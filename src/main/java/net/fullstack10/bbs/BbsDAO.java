package net.fullstack10.bbs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.DBConnPool;

public class BbsDAO extends DBConnPool {
	private CommonDateUtil dUtil;
	private CommonUtil cUtil;
	public BbsDAO() {
		super();
		dUtil = new CommonDateUtil();
		cUtil = new CommonUtil();
	}

	public BbsDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
		cUtil = new CommonUtil();
	}
	/**
	 * @description 게시글 개수
	 * @param map
	 * @return int
	 */
	public int getBbsSize(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		sql.append("select count(*) as count ");
		sql.append(" from tbl_bbs ");
		sql.append(" WHERE 1 = 1 ");
		if(map.get("category")!=null && !map.get("category").equals("")) {
			sql.append(" AND bbsCategory = ? ");
		}
		if (map.get("searchCategory") != null && !map.get("searchCategory").equals("") && map.get("searchWord") != null && !map.get("searchWord").equals("")) {
			sql.append(" AND " + map.get("searchCategory"));
			sql.append(" LIKE ? ");
		}
		// 검색 조건 3) 작성일자
		if (map.get("searchStart") != null && !map.get("searchStart").toString().equals("")) {
			sql.append(" AND DATE(createdAt) >= ? " );
		}

		// 검색 조건 3) 작성일자
		if (map.get("searchEnd") != null && !map.get("searchEnd").toString().equals("")) {
			sql.append(" AND DATE(createdAt) <= ? " );
		}
		try {
			pstm = conn.prepareStatement(sql.toString());
			int index = 1;
			if (map.get("category") != null && !map.get("category").equals("")) {
				pstm.setString(index++, map.get("category").toString());
			}
			if(map.get("searchCategory") != null  && !map.get("searchCategory").equals("") && map.get("searchWord") != null && !map.get("searchWord").equals("")) {
				pstm.setString(index++, map.get("searchWord").toString());
			}
			if (map.get("searchStart") != null && !map.get("searchStart").toString().equals("")) {
				pstm.setDate(index++, java.sql.Date.valueOf(map.get("searchStart").toString()));
			}
			if (map.get("searchEnd") != null && !map.get("searchEnd").toString().equals("")) {
				pstm.setString(index++, map.get("searchEnd").toString() + " 23:59:59");
			}
			rs = pstm.executeQuery();
			rs.next();
			return rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @description 전체 게시글 조회
	 *
	 * @author shin
	 * @param map Map<String, Object>
	 * @return List<BbsDTO>
	 */
	public List<BbsDTO> getBbsList(Map<String, Object> map) {

		List<BbsDTO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT tb.idx as idx, tb.bbsCategory as bbsCategory, tb.bbsTitle as bbsTitle, tb.bbsContent as bbsContent");
		sql.append(", tb.memberId as memberId, tb.viewCnt as viewCnt, count(tbl.idx) as likeCnt, tb.createdAt as createdAt");
		sql.append(" FROM tbl_bbs tb");
		sql.append(" LEFT OUTER JOIN tbl_bbs_like tbl ON tb.idx = tbl.bbsIdx");
		sql.append(" WHERE 1 = 1 ");

		// 검색 조건 1) 검색어
		if (map.get("searchCategory") != null && !map.get("searchCategory").equals("") && map.get("searchWord") != null && !map.get("searchWord").equals("")) {
			sql.append(" AND tb." + map.get("searchCategory").toString() + " LIKE ?");
		}

		// 검색 조건 2) 게시판 카테고리별
		if (map.get("category") != null && !map.get("category").toString().equals("")) {
			sql.append(" AND tb.bbsCategory = ? " );
		}

		// 검색 조건 3) 작성일자
		if (map.get("searchStart") != null && !map.get("searchStart").toString().equals("")) {
			sql.append(" AND DATE(tb.createdAt) >= ? " );
		}

		// 검색 조건 3) 작성일자
		if (map.get("searchEnd") != null && !map.get("searchEnd").toString().equals("")) {
			sql.append(" AND DATE(tb.createdAt) <= ? " );
		}

		sql.append(" GROUP BY tb.idx"); // 좋아요 수 group by
		String searchOrder = (String)map.get("searchOrder");
		if (searchOrder!=null && !searchOrder.equals("") && searchOrder.length()>0) {
			if(searchOrder.trim().equalsIgnoreCase("orderByViewCnt")) {
				sql.append(" ORDER BY viewCnt desc");
			}
			if(searchOrder.trim().equalsIgnoreCase("orderByLikeCnt")) {
				sql.append(" ORDER BY likeCnt desc");
			}
		} else {
			sql.append(" ORDER BY tb.idx desc"); // idx 내림차순
		}

		// 페이징 부분
		if (map.get("pageSkipCount") != null && map.get("pageSize") != null) {
			sql.append(" LIMIT ? , ?");
		}

		try {
			pstm = conn.prepareStatement(sql.toString());
			int index = 1;
			if(map.get("searchCategory") != null && !map.get("searchCategory").toString().equals("") && map.get("searchWord") != null && !map.get("searchWord").toString().equals("") ) {
				pstm.setString(index++, "%"+map.get("searchWord").toString()+"%");
			}
			if(map.get("category") != null && !map.get("category").toString().equals("") ) {
				pstm.setString(index++, map.get("category").toString());
			}
			if (map.get("searchStart") != null && !map.get("searchStart").toString().equals("")) {
				pstm.setDate(index++, java.sql.Date.valueOf(map.get("searchStart").toString()));
			}
			if (map.get("searchEnd") != null && !map.get("searchEnd").toString().equals("")) {
				pstm.setString(index++, map.get("searchEnd").toString() + " 23:59:59");
			}
			if(map.get("pageSkipCount")!=null && map.get("pageSize")!=null) {
				pstm.setInt(index++, cUtil.parseInt(map.get("pageSkipCount").toString()));
				pstm.setInt(index++, cUtil.parseInt(map.get("pageSize").toString()));
			}
			rs = pstm.executeQuery();

			while (rs.next()) {
				BbsDTO dto = new BbsDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setBbsTitle(rs.getString("bbsTitle"));
				dto.setBbsContent(rs.getString("bbsContent"));
				dto.setBbsCategory(rs.getString("bbsCategory"));
				dto.setViewCnt(rs.getInt("viewCnt"));
				dto.setLikeCnt(rs.getInt("likeCnt"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @description 인덱스로 게시글 상세 조회
	 *
	 * @author shin
	 * @param idx
	 * @return BbsDTO
	 */
	public BbsDTO getBbs(String idx, String memberId) {

		BbsDTO dto = new BbsDTO();
		List<Map> commentList = new ArrayList<>();
		List<Map> fileList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();

		// 게시글 불러오기 쿼리
		sql.append("SELECT tb.idx as idx, tb.bbsCategory as bbsCategory, tb.bbsTitle as bbsTitle, tb.bbsContent as bbsContent");
		sql.append(", tb.memberId as memberId, tb.viewCnt as viewCnt, count(tbl.idx) as likeCnt, tb.createdAt as createdAt, tb.updatedAt as updatedAt");
		if(memberId!=null && !memberId.equalsIgnoreCase("")) {
			sql.append(", MAX(CASE WHEN tbl.memberId = ? THEN 1 ELSE 0 END) as isLike ");
		}

		sql.append(" FROM tbl_bbs tb");
		sql.append(" LEFT OUTER JOIN tbl_bbs_like tbl ON tb.idx = tbl.bbsIdx");
		sql.append(" WHERE tb.idx = ?");

		// 파일 불러오기 쿼리
		sql2.append("SELECT tf.* ");
		sql2.append(" FROM tbl_bbs_file tbf ");
		sql2.append(" JOIN tbl_file tf ON tbf.fileIdx = tf.idx ");
		sql2.append(" WHERE tbf.bbsIdx = ?");

		// 댓글 불러오기 쿼리
		sql3.append("SELECT idx, bbsIdx, memberId, commentContent, createdAt, updatedAt ");
		sql3.append(" FROM tbl_bbs_comment ");
		sql3.append(" WHERE bbsIdx = ? ");
		sql3.append(" ORDER BY idx desc ");

		try {
			int index = 1;
			// get items from tbl_bbs
			pstm = conn.prepareStatement(sql.toString());
			if(memberId!=null && !memberId.equalsIgnoreCase("")) {
				pstm.setString(index++, memberId);
			}
			pstm.setString(index++, idx);
			rs = pstm.executeQuery();
			if (rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setBbsTitle(rs.getString("bbsTitle"));
				dto.setBbsContent(rs.getString("bbsContent"));
				dto.setBbsCategory(rs.getString("bbsCategory"));
				dto.setViewCnt(rs.getInt("viewCnt"));
				dto.setLikeCnt(rs.getInt("likeCnt"));
				dto.setMemberId(rs.getString("memberId"));
				if(memberId!=null && !memberId.equalsIgnoreCase("")) {
					dto.setLike(rs.getBoolean("isLike"));
				}
				if (rs.getDate("createdAt") != null) {
					dto.setCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				}
				if (rs.getDate("updatedAt") != null) {
					dto.setUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));
				}

			}

			// get items from tbl_file & tbl_bbs_file
			rs.close();
			pstm.close();
			pstm = conn.prepareStatement(sql2.toString());
			pstm.setString(1, idx);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Map<String, String> file = new HashMap<>();
				file.put("fileIdx", rs.getString("idx"));
				file.put("fileName", rs.getString("fileName"));
				file.put("filePath", rs.getString("filePath"));
				file.put("fileExt", rs.getString("fileExt"));
				file.put("fileSize", rs.getString("fileSize"));
				fileList.add(file);
			}
			dto.setFiles(fileList);

			// get items from tbl_bbs_comment
			rs.close();
			pstm.close();
			pstm = conn.prepareStatement(sql3.toString());
			pstm.setString(1, idx);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Map<String, String> comment = new HashMap<>();
				comment.put("idx", rs.getString("idx"));
				comment.put("memberId", rs.getString("memberId"));
				comment.put("commentContent", rs.getString("commentContent"));
				comment.put("createdAt", rs.getString("createdAt"));
				comment.put("updatedAt", rs.getString("updatedAt"));
				commentList.add(comment);
			}
			dto.setComment(commentList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * @description 글 작성
	 *
	 * @author shin
	 * @param BbsDTO
	 * @return int
	 */

	public int setBbsRegist(BbsDTO dto) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();

		int bbsIdx = 0;
		int[] arrFileIdx;
		int fileCnt = 0;
		int result = 0;

		sql.append("INSERT INTO tbl_bbs (");
		sql.append(" bbsTitle, bbsContent, bbsCategory, memberId ");
		sql.append(" ) values ( ");
		sql.append(" ?, ?, ?, ?");
		sql.append(" ) ");

		sql2.append("INSERT INTO tbl_file ( ");
		sql2.append(" fileName, fileExt, filePath, fileSize ");
		sql2.append(") VALUES ( ");
		sql2.append("?, ?, ?, ?) ");

		sql3.append("INSERT INTO tbl_bbs_file ( ");
		sql3.append(" bbsIdx, fileIdx ");
		sql3.append(") VALUES ( ");
		sql3.append(" ?, ? ) ");

		try {
			conn.setAutoCommit(false);

			pstm = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, dto.getBbsTitle());
			pstm.setString(2, dto.getBbsContent());
			pstm.setString(3, dto.getBbsCategory());
			pstm.setString(4, dto.getMemberId());
			result = pstm.executeUpdate();
			try (ResultSet rs = pstm.getGeneratedKeys()) {
				if(rs.next()) {
					bbsIdx = rs.getInt(1);
				} else {
					throw new SQLException("게시글 등록 실패");
				}
			}

			// 게시글이 잘 등록되었고 파일이 없는 경우 바로 커밋하고 종료
			if (result > 0 && dto.getFiles().size() < 1) {
				if(conn!=null) {
					conn.commit();
				}
				return bbsIdx;
			}

			arrFileIdx = new int[dto.getFiles().size()];
			// 파일 등록
            try (PreparedStatement pstm2 = conn.prepareStatement(sql2.toString(), Statement.RETURN_GENERATED_KEYS)) {
                for (Map<String, String> file : dto.getFiles()) {
                    pstm2.setString(1, file.get("fileName"));
                    pstm2.setString(2, file.get("fileExt"));
                    pstm2.setString(3, file.get("filePath"));
                    pstm2.setString(4, file.get("fileSize"));
                    pstm2.executeUpdate();
                    try (ResultSet fileRs = pstm2.getGeneratedKeys()) {
                        if (fileRs.next()) {
                            arrFileIdx[fileCnt++] = fileRs.getInt(1);
                        }
                    }
                }
            }

            // 게시글과 파일 연결
            try (PreparedStatement pstm3 = conn.prepareStatement(sql3.toString())) {
                for (int i = 0; i < fileCnt; i++) {
                    pstm3.setInt(1, bbsIdx);
                    pstm3.setInt(2, arrFileIdx[i]);
                    pstm3.executeUpdate();
                }
            }
            /*
			rs = pstm.getGeneratedKeys();
			if (!rs.next()) {
				return 0;
			}

			bbsIdx = rs.getInt(1);
			if (rs != null) rs.close();

			arrFileIdx = new int[dto.getFiles().size()];
			for (Map<String, String> file : dto.getFiles()) {
				if (pstm != null) pstm.close();
				pstm = conn.prepareStatement(sql2.toString(), Statement.RETURN_GENERATED_KEYS);
				pstm.setString(1, file.get("fileName"));
				pstm.setString(2, file.get("fileExt"));
				pstm.setString(3, file.get("filePath"));
				pstm.setString(4, file.get("fileSize"));
				pstm.executeUpdate();
				arrFileIdx[fileCnt++] = pstm.getGeneratedKeys().getInt(1);
			}
			for (int i = 0; i < fileCnt; i++ ) {
				if (pstm != null) pstm.close();
				pstm = conn.prepareStatement(sql3.toString());
				pstm.setString(1, bbsIdx+"");
				pstm.setString(2, arrFileIdx[i]+"");
				pstm.executeUpdate();
			}
			if (pstm != null) pstm.close();
			*/
            if(conn!=null) {
            	conn.commit();
            }
		} catch (SQLException e) {
			try {
	            conn.rollback();  // 예외 발생 시 롤백
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
			e.printStackTrace();
		} finally {
			try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();  // 커넥션 종료
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		return bbsIdx;
	}

	/**
	 * @description 댓글 작성
	 *
	 * @author shin
	 * @param bbsIdx
	 * @param memberId
	 * @param comment
	 * @return int
	 */
	public int setBbsCommentRegist(String bbsIdx, String memberId, String comment) {
		
		int commentIdx = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("insert into tbl_bbs_comment ( ");
		sql.append(" bbsIdx, memberId, commentContent ");
		sql.append(") values (");
		sql.append("?, ?, ? )");
		
		try {
			pstm = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, bbsIdx);
			pstm.setString(2, memberId);
			pstm.setString(3, comment);
			pstm.executeUpdate();
			try (ResultSet rs = pstm.getGeneratedKeys()) {
				if(rs.next()) {
					commentIdx = rs.getInt(1);
				} else {
					throw new SQLException("게시글 등록 실패");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentIdx;
	}
	public int setBbsCommentModify(String commentIdx, String comment) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bbs_comment set ");
		sql.append(" commentContent = ?, ");
		sql.append(" updatedAt = ? ");
		sql.append(" WHERE idx = ?");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, comment);
			pstm.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			pstm.setString(3, commentIdx);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	public int setBbsCommentDelete(String commentIdx) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_bbs_comment WHERE");
		sql.append(" idx = ?");
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, commentIdx);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * @description 게시글 인덱스로 파일 조회
	 * @param idx
	 * @return
	 */
	public List<Map> getBbsFilesByIdx(String idx) {
		List<Map> files = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tf.fileName, tf.filePath ");
		sql.append(" FROM tbl_bbs_file tbf ");
		sql.append(" JOIN tbl_file tf ON tbf.fileIdx = tf.idx ");
		sql.append(" WHERE tbf.bbsIdx = ?");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Map<String, String> file = new HashMap<>();
				file.put("fileName", rs.getString("fileName"));
				file.put("filePath", rs.getString("filePath"));
				files.add(file);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}
	/**
	 * @description 글 삭제
	 *
	 * @author shin
	 * @param idx: String
	 * @return int (변경된 row 값 합계)
	 */
	public int setBbsDelete(String idx) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();

		sql.append("DELETE FROM tbl_bbs ");
		sql.append(" where idx = ? ");

		sql2.append("DELETE FROM tbl_bbs_file ");
		sql2.append(" where bbsIdx = ? ");

		sql3.append("DELETE FROM tbl_bbs_comment ");
		sql3.append(" where bbsIdx = ? ");

		try {

			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			result += pstm.executeUpdate();
			if (pstm!=null) {
				pstm.close();
			}

			pstm = conn.prepareStatement(sql2.toString());
			pstm.setString(1, idx);
			result += pstm.executeUpdate();
			if (pstm!=null) {
				pstm.close();
			}

			pstm = conn.prepareStatement(sql3.toString());
			pstm.setString(1, idx);
			result += pstm.executeUpdate();
			if (pstm!=null) {
				pstm.close();
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @description 좋아요 추가
	 *
	 * @author shin
	 * @param idx: String, memberId: String
	 * @return int
	 */
	public int setBbsLikeRegist(String idx, String memberId) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into tbl_bbs_like (bbsIdx, memberId) ");
		sql.append(" values (?, ?) ");
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			pstm.setString(2, memberId);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 좋아요 취소
	 *
	 * @author shin
	 * @param idx: String, memberId: String
	 * @return int
	 */
	public int setBbsLikeDelete(String idx, String memberId) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_bbs_like ");
		sql.append(" WHERE bbsIdx = ? ");
		sql.append(" AND memberId = ? ");
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			pstm.setString(2, memberId);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 게시글 수정
	 * @param dto
	 * @return
	 */
	public int setBbsModify(BbsDTO dto) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();

		int bbsIdx = dto.getIdx();
		int[] arrFileIdx;
		int fileCnt = 0;
		int result = 0;

		sql.append("UPDATE tbl_bbs SET");
		sql.append(" bbsTitle = ?, ");
		sql.append(" bbsContent = ?, ");
		sql.append(" bbsCategory = ?, ");
		sql.append(" updatedAt = ? ");
		sql.append(" WHERE idx = ?");

		sql2.append("INSERT INTO tbl_file ( ");
		sql2.append(" fileName, fileExt, filePath, fileSize ");
		sql2.append(") VALUES ( ");
		sql2.append("?, ?, ?, ?) ");

		sql3.append("INSERT INTO tbl_bbs_file ( ");
		sql3.append(" bbsIdx, fileIdx ");
		sql3.append(") VALUES ( ");
		sql3.append(" ?, ? ) ");

		try {
			conn.setAutoCommit(false);

			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, dto.getBbsTitle());
			pstm.setString(2, dto.getBbsContent());
			pstm.setString(3, dto.getBbsCategory());
			pstm.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			pstm.setInt(5, dto.getIdx());
			result = pstm.executeUpdate();
			// 게시글이 잘 수정되었고 파일이 없는 경우 바로 커밋하고 종료
			if (result > 0 && dto.getFiles() == null) {
				conn.commit();
				return result;
			}

			arrFileIdx = new int[dto.getFiles().size()];
			// 파일 등록
            try (PreparedStatement pstm2 = conn.prepareStatement(sql2.toString(), Statement.RETURN_GENERATED_KEYS)) {
                for (Map<String, String> file : dto.getFiles()) {
                    pstm2.setString(1, file.get("fileName"));
                    pstm2.setString(2, file.get("fileExt"));
                    pstm2.setString(3, file.get("filePath"));
                    pstm2.setString(4, file.get("fileSize"));
                    pstm2.setString(5, file.get("fileSize"));
                    pstm2.executeUpdate();
                    try (ResultSet fileRs = pstm2.getGeneratedKeys()) {
                        if (fileRs.next()) {
                            arrFileIdx[fileCnt++] = fileRs.getInt(1);
                        }
                    }
                }
            }

            // 게시글과 파일 연결
            try (PreparedStatement pstm3 = conn.prepareStatement(sql3.toString())) {
                for (int i = 0; i < fileCnt; i++) {
                    pstm3.setInt(1, bbsIdx);
                    pstm3.setInt(2, arrFileIdx[i]);
                    pstm3.executeUpdate();
                }
            }
			conn.commit();
		} catch (SQLException e) {
			try {
	            conn.rollback();  // 예외 발생 시 롤백
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
			e.printStackTrace();
		} finally {
			try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();  // 커넥션 종료
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		return result;
	}

	/**
	 * @description 조회수 증가
	 *
	 * @author shin
	 * @param idx: String
	 * @return int
	 */
	public int setBbsViewCnt(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bbs SET");
		sql.append(" viewCnt = viewCnt + 1 ");
		sql.append(" where idx = ?");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 파일 삭제
	 * @param fileIdx
	 * @return
	 */
	public int setFileDelete(String fileIdx) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();

		sql.append("DELETE FROM tbl_file ");
		sql.append(" WHERE idx = ? ");
		sql2.append("DELETE FROM tbl_bbs_file ");
		sql2.append(" WHERE fileIdx = ? ");

		try {
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, fileIdx);
			int result = pstm.executeUpdate();

			if(pstm!=null) {
				pstm.close();
			}

			pstm = conn.prepareStatement(sql2.toString());
			pstm.setString(1, fileIdx);
			result += pstm.executeUpdate();
			conn.commit();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * @description 게시글 신고 처리
	 * @param memberId
	 * @param idx
	 * @param content
	 * @return
	 */
	public int setBbsReportRegist(String memberId, String idx, String content) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_customer_report ( ");
		sql.append(" memberId, targetId, targetType, description) ");
		sql.append(" VALUES ");
		sql.append(" (?, ?, ?, ?) ");

		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, memberId);
			pstm.setString(2, idx);
			pstm.setString(3, "tbl_bbs");
			// pstm.setString(3, "tbl_learning");
			pstm.setString(4, content);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @description 커뮤니티 카테고리 조회
	 * @return
	 */
	public List<String> getBbsCategory() {
		List<String> categories = new ArrayList<>();
		String sql = "SELECT distinct bbsCategory FROM tbl_bbs";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				categories.add(rs.getString("bbsCategory"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categories;
	}
}
