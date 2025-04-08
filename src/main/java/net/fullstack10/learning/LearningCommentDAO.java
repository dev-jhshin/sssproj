package net.fullstack10.learning;

import java.util.ArrayList;
import java.util.List;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;

public class LearningCommentDAO extends DBConnPool {
	private CommonDateUtil dUtil;
	
	public LearningCommentDAO() {
		super();
		dUtil = new CommonDateUtil();
	}
	
	public LearningCommentDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
	}
	
	/**
	 * @description 댓글 조회
	 *
	 * @param learningIdx String
	 * @return List<LearningCommentDTO>
	 */
	public List<LearningCommentDTO> getLearningCommentListByLearningIdx(String learningIdx) {
		List<LearningCommentDTO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" idx, learningIdx, memberId, commentContent, createdAt ");
		sql.append(" FROM tbl_learning_comment ");
		sql.append(" WHERE learningIdx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				LearningCommentDTO dto = new LearningCommentDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setLearningIdx(rs.getInt("learningIdx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setCommentContent(rs.getString("commentContent"));
				dto.setCreatedAt(dUtil.toLocalDateTime(rs.getTimestamp("createdAt")));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return list;
	}

	/**
	 * @description 댓글 등록
	 *
	 * @param learningIdx String
	 * @param memberId String
	 * @param comment String
	 * @return int
	 */
	public int createLearningComment(String learningIdx, String memberId, String comment) {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO tbl_learning_comment ( ");
		sql.append(" learningIdx, memberId, commentContent ");
		sql.append(" ) VALUES ( ?, ?, ? ) ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			pstm.setString(2, memberId);
			pstm.setString(3, comment);
			
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return 0;
	}
	
	/**
	 * @description 댓글 삭제
	 *
	 * @param idx String
	 * @return int
	 */
	public int deleteLearningCommentByIdx(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_comment ");
		sql.append(" WHERE idx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return 0;
	}

	/**
	 * @description 게시글의 댓글 삭제
	 *
	 * @param learningIdx String
	 * @return int
	 */
	public int deleteLearningCommentsBylearningIdx(String learningIdx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_comment ");
		sql.append(" WHERE learningIdx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return 0;
	}
}
