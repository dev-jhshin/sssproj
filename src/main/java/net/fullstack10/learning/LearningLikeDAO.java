package net.fullstack10.learning;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;

public class LearningLikeDAO extends DBConnPool {
private CommonDateUtil dUtil;

	public LearningLikeDAO() {
		super();
		dUtil = new CommonDateUtil();
	}

	public LearningLikeDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
	}

	/**
	 * @description 좋아요 클릭 여부
	 *
	 * @param learningIdx String
	 * @param memberId String
	 * @return boolean
	 */
	public boolean isAlreadyLiked(String learningIdx, String memberId) {
		boolean result = false;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(*) ");
		sql.append(" FROM tbl_learning_like ");
		sql.append(" WHERE learningIdx = ? ");
		sql.append(" AND memberId = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			pstm.setString(2, memberId);

			rs = pstm.executeQuery();
		    if (rs.next()) {
		        result = rs.getInt(1) > 0;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @description 좋아요 등록
	 *
	 * @param learningIdx String
	 * @param memberId String
	 * @return int
	 */
	public int createLearningLikeByMemberId(String learningIdx, String memberId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO tbl_learning_like ( ");
		sql.append(" learningIdx, memberId ");
		sql.append(" ) VALUES ( ?, ? ) ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			pstm.setString(2, memberId);

			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 좋아요 취소
	 *
	 * @param learningIdx String
	 * @param memberId String
	 * @return int
	 */
	public int deleteLearningLikeByMemberId(String learningIdx, String memberId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_like ");
		sql.append(" WHERE learningIdx = ? ");
		sql.append(" AND memberId = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, learningIdx);
			pstm.setString(2, memberId);

			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 게시글의 좋아요 삭제
	 *
	 * @param learningIdx String
	 * @return int
	 */
	public int deleteLearningLikes(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning_like ");
		sql.append(" WHERE learningIdx = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);

			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}
