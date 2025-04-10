package net.fullstack10.learning;

import java.sql.Connection;
import java.sql.SQLException;

import net.fullstack10.common.DBConnPool;

public class LearningService {
	public boolean updateLearning(String idx, LearningDTO dto) {
		Connection conn = null;
		boolean success = false;

		try {
			 DBConnPool db = new DBConnPool();
	         conn = db.getConnection();
	         conn.setAutoCommit(false);

	         // 1. 게시글 수정
	         LearningDAO learningDAO = new LearningDAO();
	         learningDAO.setConnection(conn);

	         int updated = learningDAO.updateLearningByIdx(idx, dto);
	         if (updated < 0) {
				throw new SQLException("[ERROR] 게시글 수정에 실패했습니다.");
			}

	         // 2. 파일 추가, 삭제
	         LearningFileDAO fileDAO = new LearningFileDAO();
	         fileDAO.setConnection(conn);

	         if (dto.getFilesToAdd() != null && dto.getFilesToAdd().size() != 0) {
	        	 int createFiles = fileDAO.createLearningFiles(idx, dto.getFilesToAdd());
		         if (createFiles < 0) {
					throw new SQLException("[ERROR] 파일 등록에 실패했습니다.");
				}
	         }

	         if (dto.getFilesToRemove() != null && dto.getFilesToRemove().size() != 0) {
	        	 int deleteFiles = fileDAO.deleteLearningFiles(idx, dto.getFilesToRemove());
		         if (deleteFiles < 0) {
					throw new SQLException("[ERROR] 파일 삭제에 실패했습니다.");
				}
	         }

	         // 4. 공유 추가, 삭제
	         LearningSharedDAO sharedDAO = new LearningSharedDAO();
	         sharedDAO.setConnection(conn);

	         if (dto.getSharedToAdd() != null && dto.getSharedToAdd().size() != 0) {
	        	 int addShared = sharedDAO.createLearningShareList(idx, dto.getSharedToAdd());
		         if (addShared < 0) {
					throw new SQLException("[ERROR] 공유 등록에 실패했습니다.");
				}
	         }

	         if (dto.getSharedToRemove() != null && dto.getSharedToRemove().size() != 0) {
	        	 int deleteShared = sharedDAO.deleteLearningShareBySharedTo(idx, dto.getSharedToRemove());
		         if (deleteShared < 0) {
					throw new SQLException("[ERROR] 공유 삭제에 실패했습니다.");
				}
	         }

	         conn.commit();
	         success = true;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		} finally {
			try {
				conn.setAutoCommit(true);
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return success;
	}

	public boolean deleteLearning(String idx) {
		Connection conn = null;
		boolean success = false;

		try {
			 DBConnPool db = new DBConnPool();
	         conn = db.getConnection();
	         conn.setAutoCommit(false);

	         // 1. 파일 삭제 (DB)
	         LearningFileDAO fileDAO = new LearningFileDAO();
	         fileDAO.setConnection(conn);

	         boolean deleteFiles = fileDAO.deleteLearningFiles(idx);
	         if (!deleteFiles) {
				throw new SQLException("[ERROR] 게시글 삭제에 실패했습니다.");
			}

	         // 2. 공유 목록 삭제
	         LearningSharedDAO sharedDAO = new LearningSharedDAO();
	         sharedDAO.setConnection(conn);

	         int deleteShared = sharedDAO.deleteLearningShareList(idx);
	         if (deleteShared < 0) {
				throw new SQLException("[ERROR] 공유 목록 삭제에 실패했습니다.");
			}

	         // 3. 댓글 삭제
	         LearningCommentDAO commentDAO = new LearningCommentDAO();
	         commentDAO.setConnection(conn);

	         int deleteComments = commentDAO.deleteLearningCommentsBylearningIdx(idx);
	         if (deleteComments < 0) {
				throw new SQLException("[ERROR] 댓글 삭제에 실패했습니다.");
			}

	         // 4. 좋아요 삭제
	         LearningLikeDAO likeDAO = new LearningLikeDAO();
	         likeDAO.setConnection(conn);

	         int deleteLikes = likeDAO.deleteLearningLikes(idx);
	         if (deleteLikes < 0) {
				throw new SQLException("[ERROR] 좋아요 삭제에 실패했습니다.");
			}

	         // 5. 게시글 삭제
	         LearningDAO learningDAO = new LearningDAO();
	         learningDAO.setConnection(conn);

	         int deleted = learningDAO.deleteLearningByIdx(idx);
	         if (deleted < 0) {
				throw new SQLException("[ERROR] 게시글 삭제에 실패했습니다.");
			}


	         conn.commit();
	         success = true;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		} finally {
			try {
				conn.setAutoCommit(true);
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return success;
	}
}
