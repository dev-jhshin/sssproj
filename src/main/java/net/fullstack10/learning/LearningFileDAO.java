package net.fullstack10.learning;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.fullstack10.common.DBConnPool;
import net.fullstack10.file.FileDTO;

public class LearningFileDAO extends DBConnPool {
	/**
	 * @desc 게시글의 파일 목록 조회
	 *
	 * @param idx String
	 * @return List<FileDTO>
	 */
	public List<FileDTO> getFileListByLearningIdx(String idx) {
		List<FileDTO> list = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tf.idx AS idx, tf.filePath AS filePath, tf.fileName AS fileName, tf.fileSize AS fileSize, tf.fileExt AS fileExt ");
		sql.append(" FROM tbl_learning_file AS tlf ");
		sql.append(" INNER JOIN tbl_file AS tf ON tlf.fileIdx = tf.idx ");
		sql.append(" WHERE tlf.learningIdx = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);

	        rs = pstm.executeQuery();

	        while (rs.next()) {
	        	FileDTO dto = new FileDTO();
	        	dto.setIdx(rs.getInt("idx"));
	        	dto.setFilePath(rs.getString("filePath"));
	        	dto.setFileName(rs.getString("fileName"));
	        	dto.setFileSize(rs.getLong("fileSize"));
	        	dto.setFileExt(rs.getString("fileExt"));

	        	list.add(dto);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return list;
	}

	/**
	 * @desc 게시글 파일 등록 (여러개)
	 *
	 * @param idx String
	 * @param dto List<FileDTO>
	 * @return int
	 */
	public int createLearningFiles(String idx, List<FileDTO> dto) {
		int result = 0;

		StringBuilder fileSQL = new StringBuilder();
		fileSQL.append("INSERT INTO tbl_file ( ");
		fileSQL.append(" fileName, fileExt, filePath, fileSize ");
		fileSQL.append(") VALUES ( ?, ?, ?, ? ) ");

		StringBuilder learningFileSQL = new StringBuilder();
		learningFileSQL.append("INSERT INTO tbl_learning_file ( ");
		learningFileSQL.append(" learningIdx, fileIdx ");
		learningFileSQL.append(") VALUES ( ?, ? ) ");

		try {
			for (FileDTO file : dto) {
				int fileIdx = -1;

				// 1. 파일 등록
				pstm = conn.prepareStatement(fileSQL.toString(), Statement.RETURN_GENERATED_KEYS);
				pstm.setString(1, file.getFileName());
                pstm.setString(2, file.getFileExt());
                pstm.setString(3, file.getFilePath());
                pstm.setLong(4, file.getFileSize());

                if(pstm.executeUpdate() < 0) {
					throw new SQLException("[ERROR] 파일 등록에 실패했습니다.");
				}

                rs = pstm.getGeneratedKeys();
                if (!rs.next()) {
					throw new SQLException("[ERROR] 파일 등록 후 생성된 파일 ID를 가져오는데 실패했습니다.");
				}
                fileIdx = rs.getInt(1);

                // 2. 파일 매핑
                pstm = conn.prepareStatement(learningFileSQL.toString());
                pstm.setString(1, idx);
                pstm.setInt(2, fileIdx);

                if(pstm.executeUpdate() == 0) {
					throw new SQLException("[ERROR] tbl_file 과 tbl_learning_file 매핑에 실패했습니다. fileIdx: " + fileIdx);
				}

                result++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @desc 게시글 파일 삭제 (여러개)
	 *
	 * @param idx String
	 * @param dto List<FileDTO>
	 * @return int
	 */
	public int deleteLearningFiles(String idx, List<FileDTO> dto) {
		int result = 0;

		StringBuilder fileSQL = new StringBuilder();
		fileSQL.append(" DELETE FROM tbl_file ");
		fileSQL.append(" WHERE idx = ? ");

		StringBuilder learningFileSQL = new StringBuilder();
		learningFileSQL.append(" DELETE FROM tbl_learning_file ");
		learningFileSQL.append(" WHERE learningIdx = ? ");
		learningFileSQL.append(" AND fileIdx = ? ");

		try {
			for (FileDTO file : dto) {
				int fileIdx = file.getIdx();

				// 1. 파일 매핑 데이터 삭제
				pstm = conn.prepareStatement(learningFileSQL.toString());
                pstm.setString(1, idx);
                pstm.setInt(2, fileIdx);

                if(pstm.executeUpdate() < 0) {
					throw new SQLException("[ERROR] tbl_file 과 tbl_learning_file 매핑 데이터 삭제에 실패했습니다. fileIdx: " + fileIdx);
				}

				// 2. 파일 삭제
				pstm = conn.prepareStatement(fileSQL.toString());
				pstm.setInt(1, fileIdx);

                if(pstm.executeUpdate() < 0) {
					throw new SQLException("[ERROR] 파일 삭제에 실패했습니다. fileIdx: " + fileIdx);
				}

                result++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @desc 게시글 파일 전체 삭제
	 *
	 * @param idx String
	 * @return boolean
	 */
	public boolean deleteLearningFiles(String idx) {
		boolean result = false;

		StringBuilder fileSQL = new StringBuilder();
		fileSQL.append(" DELETE FROM tbl_file ");
		fileSQL.append(" WHERE idx IN ( SELECT fileIdx FROM tbl_learning_file WHERE learningIdx = ? ) ");

		StringBuilder learningFileSQL = new StringBuilder();
		learningFileSQL.append(" DELETE FROM tbl_learning_file ");
		learningFileSQL.append(" WHERE learningIdx = ? ");

		try {
			// 1. 파일 삭제
			pstm = conn.prepareStatement(fileSQL.toString());
            pstm.setString(1, idx);

            if(pstm.executeUpdate() < 0) {
				throw new SQLException("[ERROR] 파일 삭제에 실패했습니다.");
			}

			// 2. 파일 매핑 데이터 삭제
			pstm = conn.prepareStatement(learningFileSQL.toString());
			pstm.setString(1, idx);

            if(pstm.executeUpdate() < 0) {
				throw new SQLException("[ERROR] 파일 매핑 데이터 삭제에 실패했습니다.");
			}

            result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
