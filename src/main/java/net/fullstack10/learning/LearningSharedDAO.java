package net.fullstack10.learning;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;

public class LearningSharedDAO extends DBConnPool {
	private CommonDateUtil dUtil;
	
	public LearningSharedDAO() {
		super();
		dUtil = new CommonDateUtil();
	}
	
	public LearningSharedDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
	}
	
	/**
	 * @desc 학습 게시물 공유 목록 등록
	 * 
	 * @param idx String
	 * @param sharedList List<LearningSharedDTO>
	 * @return int
	 */
	public int createLearningShareList(String idx, List<LearningSharedDTO> sharedList) {
		int result = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO tbl_learning_share ( ");
		sql.append(" learningIdx, sharedTo, sharedFrom ");
		sql.append(" ) VALUES ( ?, ?, ? )");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			
			for (LearningSharedDTO dto : sharedList) {
				pstm.setString(1, idx);
				pstm.setString(2, dto.getSharedTo());
				pstm.setString(3, dto.getSharedFrom());
				
				pstm.executeUpdate();
				
				result++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * @desc 학습 게시물 공유 목록 조회
	 * 
	 * @param idx String
	 * @return List<LearningSharedDTO>
	 */
	public List<LearningSharedDTO> getLearningShareList(String idx) {
		return getLearningShareList(idx, 0);
	}
	
	/**
	 * @desc 제한된 학습 게시물 공유 목록 조회 (상위 N명)
	 * 
	 * @param idx String
	 * @param limit int (제한 개수, 0이면 전체 조회)
	 * @return List<LearningSharedDTO>
	 */
	public List<LearningSharedDTO> getLearningShareList(String idx, int limit) {
		List<LearningSharedDTO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT ");
		sql.append(" tls.learningIdx AS learningIdx, tls.sharedTo AS sharedTo, A.memberName AS sharedToName, tls.sharedFrom AS sharedFrom, B.memberName AS sharedFromName, tls.createdAt AS createdAt ");
		sql.append(" FROM tbl_learning_share AS tls ");
		sql.append(" INNER JOIN tbl_member AS A ON tls.sharedTo = A.memberId ");
		sql.append(" INNER JOIN tbl_member AS B ON tls.sharedFrom = B.memberId ");
		sql.append(" WHERE tls.learningIdx = ? ");
		sql.append(" ORDER BY tls.createdAt DESC ");
		
		if (limit > 0) {
			sql.append(" LIMIT " + limit);
		}
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				LearningSharedDTO dto = new LearningSharedDTO();
				dto.setLearningIdx(rs.getInt("learningIdx"));
				dto.setSharedTo(rs.getString("sharedTo"));
				dto.setSharedToName(rs.getString("sharedToName"));
				dto.setSharedFrom(rs.getString("sharedFrom"));
				dto.setSharedFromName(rs.getString("sharedToName"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * @desc 학습 게시물 특정 공유 삭제
	 * 
	 * @param idx String
	 * @param sharedTo List<LearningSharedDTO> 
	 * @return int
	 */
	public int deleteLearningShareBySharedTo(String idx, List<LearningSharedDTO> sharedTo) {
		int result = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_share ");
		sql.append(" WHERE learningIdx = ? ");
		sql.append(" AND sharedTo = ? ");
		
		try {
			for (LearningSharedDTO dto : sharedTo) {
				pstm = conn.prepareStatement(sql.toString());
				pstm.setString(1, idx);
				pstm.setString(2, dto.getSharedTo());
				
				if (pstm.executeUpdate() < 0)
					throw new SQLException("[ERROR] 공유 삭제에 실패했습니다. learningIdx: " + idx + ", sharedTo: " + dto.getSharedTo());
				
				result++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @desc 학습 게시물 전체 공유 목록 삭제
	 * 
	 * @param idx String
	 * @return int
	 */
	public int deleteLearningShareList(String idx) {
		int result = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_share ");
		sql.append(" WHERE learningIdx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			
			result = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
