package net.fullstack10.learning;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;
import net.fullstack10.file.FileDTO;

public class LearningDAO extends DBConnPool {
	private CommonDateUtil dUtil;

	public LearningDAO() {
		super();
		dUtil = new CommonDateUtil();
	}

	public LearningDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
	}

	/**
	 * @desc 학습 게시글 등록
	 *
	 * @param dto LearningDTO
	 * @return int
	 */
	public int createLearning(LearningDTO dto) {
		int result = 0;
		int learningIdx = -1;
		int fileIdx = -1;

		StringBuilder learningSQL = new StringBuilder();
		learningSQL.append(" INSERT INTO tbl_learning ( ");
		learningSQL.append(" memberId, learningTitle, learningContent, learningStartedAt, learningEndedAt, topic, hashtag, isVisible, isPublic ");
		learningSQL.append(" ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

		StringBuilder fileSQL = new StringBuilder();
		fileSQL.append("INSERT INTO tbl_file ( ");
		fileSQL.append(" fileName, fileExt, filePath, fileSize ");
		fileSQL.append(") VALUES ( ?, ?, ?, ? ) ");

		StringBuilder learningFileSQL = new StringBuilder();
		learningFileSQL.append("INSERT INTO tbl_learning_file ( ");
		learningFileSQL.append(" learningIdx, fileIdx ");
		learningFileSQL.append(") VALUES ( ?, ? ) ");

		StringBuilder shareSQL = new StringBuilder();
		shareSQL.append(" INSERT INTO tbl_learning_share ( ");
		shareSQL.append(" learningIdx, sharedTo, sharedFrom ");
		shareSQL.append(") VALUES ( ?, ?, ? ) ");

		try {
			conn.setAutoCommit(false);

			// 1. 게시글 등록
			pstm = conn.prepareStatement(learningSQL.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, dto.getMemberId());
			pstm.setString(2, dto.getLearningTitle());
			pstm.setString(3, dto.getLearningContent());
			pstm.setDate(4, dto.getLearningStartedAt() != null ? Date.valueOf(dto.getLearningStartedAt()) : null);
			pstm.setDate(5, dto.getLearningEndedAt() != null ? Date.valueOf(dto.getLearningEndedAt()) : null);
			pstm.setString(6, dto.getTopic());
			pstm.setString(7, dto.getHashtag());
			pstm.setBoolean(8, dto.getIsVisible());
			pstm.setBoolean(9, dto.getIsPublic());

			result = pstm.executeUpdate();

			rs = pstm.getGeneratedKeys();
			if (rs.next()) { // key 없음
				learningIdx = rs.getInt(1);
			} else {
				conn.rollback();
				return -1;
			}

			// 2. 파일 등록 및 매핑
			for (FileDTO file : dto.getFiles()) {
				pstm = conn.prepareStatement(fileSQL.toString(), Statement.RETURN_GENERATED_KEYS);
				pstm.setString(1, file.getFileName());
                pstm.setString(2, file.getFileExt());
                pstm.setString(3, file.getFilePath());
                pstm.setLong(4, file.getFileSize());

                pstm.executeUpdate();

                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                	fileIdx = rs.getInt(1);
                } else {
                	conn.rollback();
                	return -1;
                }

                pstm = conn.prepareStatement(learningFileSQL.toString());
                pstm.setInt(1, learningIdx);
                pstm.setInt(2, fileIdx);

                if (pstm.executeUpdate() < 0) {
                	conn.rollback();
                	return -1;
                }
			}

			// 3. 공유 목록 등록
			for (LearningSharedDTO sharedDTO : dto.getSharedList()) {
				pstm = conn.prepareStatement(shareSQL.toString());
				pstm.setInt(1, learningIdx);
	            pstm.setString(2, sharedDTO.getSharedTo());
	            pstm.setString(3, dto.getMemberId());

	            if (pstm.executeUpdate() < 0) {
                	conn.rollback();
                	return -1;
	            }
			}

			conn.commit();
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
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * @desc 모든 학습 게시글 개수 조회
	 *
	 * @param map Map<String, String>
	 * @return int
	 */
	public int getLearningListSize(Map<String, String> map) {
		int result = 0;

		List<String> params = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(tl.idx) ");
		sql.append(" FROM tbl_learning AS tl");
		sql.append(" WHERE tl.isPublic = true ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
	        for (String param : params) {
	            pstm.setString(index++, param);
	        }

	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	result = rs.getInt(1);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return result;
	}

	/**
	 * @desc 나의 학습 게시글 개수 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return int
	 */
	public int getLearningListSizeByMemberId(String memberId, Map<String, String> map) {
		int result = 0;

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(tl.idx) ");
		sql.append(" FROM tbl_learning AS tl");
		sql.append(" WHERE tl.memberId = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
	        for (String param : params) {
	            pstm.setString(index++, param);
	        }

	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	result = rs.getInt(1);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return result;
	}

	/**
	 * @desc 공유받은 학습 게시글 개수 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return int
	 */
	public int getReceivedSharedListSize(String memberId, Map<String, String> map) {
		int result = 0;

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(DISTINCT tl.idx) ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" INNER JOIN tbl_learning_share AS tls ON tl.idx = tls.learningIdx ");
		sql.append(" WHERE tls.sharedTo = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
	        for (String param : params) {
	            pstm.setString(index++, param);
	        }

	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	result = rs.getInt(1);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return result;
	}

	/**
	 * @desc 공유한 학습 게시글 개수 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return int
	 */
	public int getSentSharedListSize(String memberId, Map<String, String> map) {
		int result = 0;

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(DISTINCT tl.idx) ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" INNER JOIN tbl_learning_share AS tls ON tl.idx = tls.learningIdx ");
		sql.append(" WHERE tls.sharedFrom = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
	        for (String param : params) {
	            pstm.setString(index++, param);
	        }

	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	result = rs.getInt(1);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return result;
	}

	/**
	 * @desc 오늘의 학습 게시글 개수 조회 (나의학습)
	 *
	 * @param memberId String
	 * @param date LocalDate
	 * @return int
	 */
	public int getTodayLearningListSize(String memberId, LocalDate date) {
		int result = 0;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" COUNT(DISTINCT idx) ");
		sql.append(" FROM tbl_learning ");
		sql.append(" WHERE memberId = ? ");
		sql.append(" AND isVisible = true ");
		sql.append(" AND ? BETWEEN learningStartedAt AND learningEndedAt ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, memberId);
			pstm.setDate(2, Date.valueOf(date));

	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	result = rs.getInt(1);
	        }
		} catch (Exception e) {
	        e.printStackTrace();
	    }

		return result;
	}

	/**
	 * @desc 모든 학습 게시글 목록 조회
	 *
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getLearningList(Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.learningTitle AS learningTitle, tl.memberId AS memberId, tl.createdAt AS createdAt, tl.viewCnt AS viewCnt, COUNT(tll.idx) AS likeCnt ");
		sql.append(" FROM tbl_learning AS tl");
		sql.append(" LEFT OUTER JOIN tbl_learning_like AS tll ON tl.idx = tll.learningIdx ");
		sql.append(" WHERE tl.isPublic = true ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		sql.append(" GROUP BY tl.idx ");
		
		LearningQueryHelper.addOrderBy(sql, map.get("orderColumn"), map.get("orderDirection"));
		LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
				dto.setViewCnt(rs.getInt("viewCnt"));
				dto.setLikeCnt(rs.getInt("likeCnt"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 나의 학습 게시글 목록 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getLearningListByMemberId(String memberId, Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.learningTitle AS learningTitle, tl.createdAt AS createdAt, COUNT(tll.idx) AS likeCnt, tl.isVisible AS isVisible, tl.learningStartedAt AS learningStartedAt, tl.learningEndedAt AS learningEndedAt ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" LEFT OUTER JOIN tbl_learning_like AS tll ON tl.idx = tll.learningIdx ");
		sql.append(" WHERE tl.memberId = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));

		sql.append(" GROUP BY tl.idx ");

		LearningQueryHelper.addOrderBy(sql, map.get("orderColumn"), map.get("orderDirection"));
		LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
				dto.setLikeCnt(rs.getInt("likeCnt"));
				dto.setIsVisible(rs.getBoolean("isVisible"));
				Date startedAt = rs.getDate("learningStartedAt");
				dto.setLearningStartedAt(startedAt != null ? startedAt.toLocalDate() : null);

				Date endedAt = rs.getDate("learningEndedAt");
				dto.setLearningEndedAt(endedAt != null ? endedAt.toLocalDate() : null);

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 공유받은 학습 게시글 목록 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getReceivedSharedList(String memberId, Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.memberId AS memberId, tl.learningTitle AS learningTitle, tl.createdAt AS createdAt ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" INNER JOIN tbl_learning_share AS tls ON tl.idx = tls.learningIdx ");
		sql.append(" WHERE tls.sharedTo = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));
		// LearningQueryHelper.addOrderBy(sql, map.get("orderColumn"), map.get("orderDirection"));
		LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));


		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 공유한 학습 게시글 목록 조회
	 *
	 * @param memberId String
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getSentSharedList(String memberId, Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();
		params.add(memberId);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.memberId AS memberId, tl.learningTitle AS learningTitle, tl.createdAt AS createdAt ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" INNER JOIN tbl_learning_share AS tls ON tl.idx = tls.learningIdx ");
		sql.append(" WHERE tls.sharedFrom = ? ");

		LearningQueryHelper.addDateFilter(sql, params, map.get("startDate"), map.get("endDate"));
		LearningQueryHelper.addCategoryFilter(sql, params, map.get("searchCategory"), map.get("searchValue"));
		
		sql.append(" GROUP BY tl.idx ");
		
		LearningQueryHelper.addOrderBy(sql, map.get("orderColumn"), map.get("orderDirection"));
		LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));


		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 오늘의 학습 게시글 목록 조회
	 *
	 * @param memberId String
	 * @param date LocalDate
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getTodayLearningList(String memberId, LocalDate date, Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();
		params.add(memberId);
		params.add(date.toString());

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.learningTitle AS learningTitle, tl.learningContent AS learningContent, tl.topic AS topic, tl.hashtag AS hashtag ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" WHERE tl.memberId = ? ");
		sql.append(" AND tl.isVisible = true ");
		sql.append(" AND ? BETWEEN tl.learningStartedAt AND tl.learningEndedAt ");

		LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();

				dto.setIdx(rs.getInt("idx"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setLearningContent(rs.getString("learningContent"));
				dto.setTopic(rs.getString("topic"));
				dto.setHashtag(rs.getString("hashtag"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 오늘의 공유 게시글 목록 조회 (페이지네이션 O)
	 *
	 * @param memberId String
	 * @param date LocalDate
	 * @param map Map<String, String>
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getTodaySharedList(String memberId, LocalDate date, Map<String, String> map) {
		List<LearningDTO> list = new ArrayList<>();

		List<String> params = new ArrayList<>();
		params.add(memberId);
		params.add(date.toString());

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tls.sharedFrom AS memberId, COUNT(tll.idx) AS likeCnt ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" INNER JOIN tbl_learning_share AS TLS ON  tl.idx = tls.learningIdx ");
		sql.append(" LEFT OUTER JOIN tbl_learning_like AS tll ON tl.idx = tll.learningIdx ");
		sql.append(" WHERE tls.sharedTo = ? ");
		sql.append(" AND isVisible = true ");
		sql.append(" AND DATE(?) BETWEEN DATE(tl.learningStartedAt) AND DATE(tl.learningEndedAt) ");
		sql.append(" GROUP BY tl.idx ");

		if (map != null && !map.isEmpty()) {
			LearningQueryHelper.addPagination(sql, map.get("pageSkipCount"), map.get("pageSize"));
		}

		try {
			pstm = conn.prepareStatement(sql.toString());

			int index = 1;
			for (String param : params) {
				pstm.setString(index++, param);
			}

			rs = pstm.executeQuery();

			while(rs.next()) {
				LearningDTO dto = new LearningDTO();

				dto.setIdx(rs.getInt("idx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setLikeCnt(rs.getInt("likeCnt"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @desc 오늘의 공유 게시글 목록 조회 (페이지네이션 X)
	 *
	 * @param memberId String
	 * @param date LocalDate
	 * @return List<LearningDTO>
	 */
	public List<LearningDTO> getTodaySharedList(String memberId, LocalDate date) {
		return getTodaySharedList(memberId, date, null);
	}

	/**
	 * @desc 학습 게시글 상세 조회
	 *
	 * @param idx String
	 * @return LearningDTO
	 */
	public LearningDTO getLearningByIdx(String idx) {
		LearningDTO dto = new LearningDTO();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" tl.idx AS idx, tl.memberId AS memberId, tl.learningTitle AS learningTitle, tl.learningContent AS learningContent, tl.topic AS topic, tl.hashtag AS hashtag, tl.learningStartedAt AS learningStartedAt, tl.learningEndedAt AS learningEndedAt, tl.isPublic AS isPublic, tl.isVisible AS isVisible, tl.createdAt AS createdAt, tl.viewCnt AS viewCnt, COUNT(tll.idx) AS likeCnt ");
		sql.append(" FROM tbl_learning AS tl ");
		sql.append(" LEFT OUTER JOIN tbl_learning_like AS tll ON tl.idx = tll.learningIdx ");
		sql.append(" WHERE tl.idx = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);

			rs = pstm.executeQuery();

			if (rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setLearningTitle(rs.getString("learningTitle"));
				dto.setLearningContent(rs.getString("learningContent"));
				dto.setTopic(rs.getString("topic"));
				dto.setHashtag(rs.getString("hashtag"));
				LocalDate startedAt = (rs.getDate("learningStartedAt") != null ?
						dUtil.toLocalDate(rs.getDate("learningStartedAt")) : null );
				dto.setLearningStartedAt(startedAt);
				LocalDate endedAt = (rs.getDate("learningEndedAt") != null ?
						dUtil.toLocalDate(rs.getDate("learningEndedAt")) : null );
				dto.setLearningEndedAt(endedAt);
				dto.setIsPublic(rs.getBoolean("isPublic"));
				dto.setIsVisible(rs.getBoolean("isVisible"));
				dto.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
				dto.setViewCnt(rs.getInt("viewCnt"));
				dto.setLikeCnt(rs.getInt("likeCnt"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	/**
	 * @desc 학습 게시글 수정
	 *
	 * @param idx String
	 * @param dto LearningDTO
	 * @return int
	 */
	public int updateLearningByIdx(String idx, LearningDTO dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE tbl_learning SET ");
		sql.append(" learningTitle = ?, learningContent = ?, learningStartedAt = ?, learningEndedAt = ?, topic = ?, hashtag = ?, isVisible = ?, isPublic = ?, updatedAt = ? ");
		sql.append(" WHERE idx = ? ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, dto.getLearningTitle());
			pstm.setString(2, dto.getLearningContent());
			pstm.setDate(3, dto.getLearningStartedAt() != null ? Date.valueOf(dto.getLearningStartedAt()) : null);
			pstm.setDate(4, dto.getLearningEndedAt() != null ? Date.valueOf(dto.getLearningEndedAt()) : null);
			pstm.setString(5, dto.getTopic());
			pstm.setString(6, dto.getHashtag());
			pstm.setBoolean(7, dto.getIsVisible());
			pstm.setBoolean(8, dto.getIsPublic());
			pstm.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
			pstm.setString(10, idx);

			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @desc 학습 게시글 삭제
	 *
	 * @param idx String
	 * @return int
	 */
	public int deleteLearningByIdx(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM tbl_learning ");
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
	 * @desc 학습 게시글 조회수 증가
	 *
	 * @param idx String
	 * @return int
	 */
	public int updateViewCnt(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE tbl_learning SET ");
		sql.append(" viewCnt = viewCnt + 1 ");
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
	 * @desc 학습 게시글 조회수 조회
	 * 
	 * @param idx String
	 * @return int
	 */
	public int getViewCntByIdx(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append(" Select viewCnt ");
		sql.append(" From tbl_learning ");
		sql.append(" WHERE idx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			
			rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getInt("viewCnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * @desc 학습 게시글 신고 처리
	 *
	 * @param memberId String
	 * @param idx String
	 * @param content String
	 * @return int
	 */
	public int createLearningReport(String memberId, String idx, String content) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_customer_report ( ");
		sql.append(" memberId, targetId, targetType, description) ");
		sql.append(" VALUES ");
		sql.append(" (?, ?, ?, ?) ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, memberId);
			pstm.setString(2, idx);
			// pstm.setString(3, "tbl_bbs");
			pstm.setString(3, "tbl_learning");
			pstm.setString(4, content);
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}
