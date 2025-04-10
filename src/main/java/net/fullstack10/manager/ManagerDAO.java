package net.fullstack10.manager;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;

public class ManagerDAO extends DBConnPool {
	private CommonDateUtil dUtil;

	public ManagerDAO() {
		super();
		dUtil = new CommonDateUtil();
	}
	public ManagerDAO(String ct, String ds) {
		super(ct,ds);
		dUtil = new CommonDateUtil();
	}

	/**
	 * 관리자 로그인 메서드
	 * @param dto 로그인시 필요한 사용자 정보(managerId, managerPwd)
	 * @return 로그인 성공시 managerId,managerStatus가 있는 객체반환, null 객체 반환
	 * @example managerLogin(new ManagerDTO("testManager", "testPwd"))
	 */
	public ManagerDTO managerLogin(ManagerDTO dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT managerId, managerPwd, managerStatus");
		sb.append(" FROM tbl_manager");
		sb.append(" WHERE managerId = ?");
		sb.append(" AND managerPwd = SHA2(?, 256)");

		 try {
		        pstm = conn.prepareStatement(sb.toString());
		        pstm.setString(1, dto.getManagerId());
		        pstm.setString(2, dto.getManagerPwd());

		        rs = pstm.executeQuery();

		        if (rs.next()) {
		            dto.setManagerId(rs.getString("managerId"));
		            dto.setManagerStatus(rs.getInt("managerStatus"));
		            System.out.println("로그인 성공!");
		        } else {
		            dto = null;
		            System.out.println("로그인 실패 (아이디 또는 비밀번호 불일치)");
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("로그인 오류: " + e.getMessage());
		        dto = null;
		    }

		    return dto;
		}
	/**
	 * 관리자에 의한 회원삭제 메서드
	 * @param emberId String - 삭제할 대상 회원의 ID
	 * @return 삭제 성공 여부 int - 변경된 행 수
	 * @example getMemberInfo
	 */
	public int memberDelete(String memberId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tbl_member");
		sb.append(" WHERE memberId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);

			rs = pstm.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("관리자에 의한 회원삭제 오류" + e.getMessage());
		}
		return rs;
	}
	/**
	 * 관리자에 의한 회원 상태 변경 (활성 -> 정지)
	 * 회원의 상태를 정지(memberStatus = 2)로 변경
	 * @param memberId String - 상태를 변경할 대상 회원의 ID
	 * @return int - 변경된 행 수
	 * @example memberChange1(memberId)
	 */
	public int memberChange1(String memberId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tbl_member");
		sb.append(" SET memberStatus = 2");
		sb.append(" WHERE memberId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("관리자 회원 상태 변경 오루" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 관리자에 의한 회원 상태 변경 (정지 -> 활성)
	 * 회원의 상태를 정지(memberStatus = 1)로 변경
	 * @param memberId String - 상태를 변경할 대상 회원의 ID
	 * @return int - 변경된 행 수
	 * @example memberChange2(memberId)
	 */
	public int memberChange2(String memberId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tbl_member");
		sb.append(" SET memberStatus = 1");
		sb.append(" WHERE memberId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("관리자 회원 상태 변경 오류" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 관리자 페이지에서 총 관리자수 조회
	 * @param map 검색 조건을 담은 Map(search_category, search_word)
	 * @return ManagerDTO
	 * @example int 총 회원 수 (검색 조건 없으면 전체 회원 수)
	 */
	public int getManagerTotalCount(Map<String, Object> map) {
		int totalCount = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM tbl_manager");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}
		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 전체 조회 에러 : "+ e.getMessage());
		}
		return totalCount;
	}

	/**
	 * 관리자 페이지에서 관리자 목록을 조회
	 *
	 * @param map 검색 조건을 포함한 Map (search_category, search_word, page_skip_count, page_size)
	 * @return List<BbsDTO>
	 *
	 * @example getMemberList(map)
	 */
	public List<ManagerDTO> getManagerList(Map<String, Object> map){

		List<ManagerDTO> list = new Vector<>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT managerId, managerName, managerEmail,managerStatus");
		sb.append(" FROM tbl_manager");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}

		sb.append(" ORDER BY managerId");

		if ( map.get("page_skip_count") != null && map.get("page_size") != null ) {
			sb.append(" LIMIT "+ map.get("page_skip_count") +", "+ map.get("page_size"));
		}

		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();

			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
				dto.setManagerId(rs.getString("managerId"));
				dto.setManagerName(rs.getString("managerName"));
				dto.setManagerEmail(rs.getString("managerEmail"));
				dto.setManagerStatus(rs.getInt("managerStatus"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("관리자 리스트 조회 에러 : "+ e.getMessage());
		}

		return list;
	}

	/**
	 * 슈퍼관리자에 의한 관리자 상태 변경 (1 -> 2)
	 * 관리자의 승진(memberStatus = 2)로 변경
	 * @param managerId String - 상태를 변경할 대상 회원의 ID
	 * @return int - 변경된 행 수
	 * @example managerChange1(managerId)
	 */
	public int managerChange1(String managerId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tbl_manager");
		sb.append(" SET managerStatus = 2");
		sb.append(" WHERE managerId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, managerId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("슈퍼관리자 관리자 상태 변경 오루" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 슈퍼관리자에 의한 관리자 상태 변경 (2 -> 1)
	 * 관리자의 상태를 강등(memberStatus = 1)로 변경
	 * @param managerId String - 상태를 변경할 대상 회원의 ID
	 * @return int - 변경된 행 수
	 * @example memberChange2(managerId)
	 */
	public int managerChange2(String managerId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tbl_manager");
		sb.append(" SET managerStatus = 1");
		sb.append(" WHERE managerId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, managerId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("관리자 회원 상태 변경 오류" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 슈퍼관리자에 의한 관리자삭제 메서드
	 * @param emberId String - 삭제할 대상 회원의 ID
	 * @return 삭제 성공 여부 int - 변경된 행 수
	 * @example getMemberInfo
	 */
	public int managerDelete(String managerId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tbl_manager");
		sb.append(" WHERE managerId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, managerId);

			rs = pstm.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("슈퍼관리자에 의한 관리자 삭제 오류" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 관리자 페이지에서 총 신고 조회
	 * @param map 검색 조건을 담은 Map(search_category, search_word)
	 * @return ManagerDTO
	 * @example int 총 회원 수 (검색 조건 없으면 전체 회원 수)
	 */
	public int getReportTotalCount(Map<String, Object> map) {
		int totalCount = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM tbl_customer_report");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}
		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 전체 조회 에러 : "+ e.getMessage());
		}
		return totalCount;
	}

	/**
	 * 관리자 페이지에서 신고 목록을 조회
	 *
	 * @param map 검색 조건을 포함한 Map (search_category, search_word, page_skip_count, page_size)
	 * @return List<BbsDTO>
	 *
	 * @example getReportList(map)
	 */
	public List<ManagerDTO> getReportList(Map<String, Object> map){

		List<ManagerDTO> list = new Vector<>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT idx, memberId, targetId, targetType, description, reportStatus, createdAt");
		sb.append(" FROM tbl_customer_report");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}

		sb.append(" ORDER BY idx");

		if ( map.get("page_skip_count") != null && map.get("page_size") != null ) {
			sb.append(" LIMIT "+ map.get("page_skip_count") +", "+ map.get("page_size"));
		}

		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();

			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
				dto.setReportIdx(rs.getInt("idx"));
				dto.setSmemberId(rs.getString("memberId"));//신고한 멤버ID
				dto.setTargetId(rs.getInt("targetId")); //신고 당한 인덱스 번호
				dto.setTargetType(rs.getString("targetType")); //신고당한 카테고리
				dto.setReportStatus(rs.getBoolean("reportStatus"));//처리상태
				dto.setReportCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("관리자 리스트 조회 에러 : "+ e.getMessage());
		}

		return list;
	}
	/**
	 * 신고 페이지 상세
	 *
	 * @param 신고Id(reportIdx)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public ManagerDTO reportDetail(int reportIdx ) {
		ManagerDTO dto = new ManagerDTO();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.idx, a.memberId, a.targetId, a.targetType, a.reportStatus, a.createdAt, a.description, b.idx as resolutionIdx,  b.managerId,  b.rResolutionContent, b.createdAt AS resolutionCreatedAt");
		sb.append(" FROM tbl_customer_report AS a");
		sb.append(" LEFT JOIN tbl_report_resolution AS b ON a.idx = b.reportIdx");
		sb.append(" WHERE a.idx = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, reportIdx);

			rs = pstm.executeQuery();
			if (rs.next()) {
				dto.setReportIdx(rs.getInt("idx"));
				dto.setSmemberId(rs.getString("memberId"));
				dto.setTargetId(rs.getInt("targetId"));
				dto.setTargetType(rs.getString("targetType"));
				dto.setReportStatus(rs.getBoolean("reportStatus"));
				dto.setDescription(rs.getString("description"));
				dto.setReportCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));

				int resolutionIdx = rs.getInt("resolutionIdx");
				if (!rs.wasNull()) {
					dto.setrReportIdx(resolutionIdx);
					dto.setrManagerId(rs.getString("managerId"));
					dto.setrResolutionContent(rs.getString("rResolutionContent"));
					dto.setResolutionCreatedAt(dUtil.toLocalDateTime(rs.getDate("resolutionCreatedAt")));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고 상세 페이지 오류" + e.getMessage());
		}

		return dto;
	}
	/**
	 * 신고 페이지 답변
	 *
	 * @param 신고Id(reportIdx), 답변(rResolutionContent), 답변한 관리자 Id(managerId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */

	public int resolveReport(int reportIdx, String managerId, String rResolutionContent) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("CALL insert_resolution_and_update_status(?, ?, ?)");
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, reportIdx);
			pstm.setString(2, managerId);
			pstm.setString(3, rResolutionContent);

			rs = pstm.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고 답변 오류" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 신고 받은 학습 게시글 확인
	 *
	 * @param 신고받은Id(targetId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public ManagerDTO reportPost1(int targetId) {
		ManagerDTO dto = new ManagerDTO();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT idx, memberId, learningTitle, learningContent, updatedAt");
		sb.append(" FROM tbl_learning");
		sb.append(" WHERE idx = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, targetId);

			rs = pstm.executeQuery();
			if(rs.next()) {
					dto.setTargetId(rs.getInt("idx"));
					dto.setRmemberId(rs.getString("memberId"));
					dto.setReportTitle(rs.getString("learningTitle"));
					dto.setReportContent(rs.getString("learningContent"));
					dto.setReportUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));

			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고 받은 학습 게시판 확인 오류." + e.getMessage());
		}
		return dto;
	}

	/**
	 * 신고 받은 카테고리 게시글 확인
	 *
	 * @param 신고받은Id(targetId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public ManagerDTO reportPost2(int targetId) {
		ManagerDTO dto = new ManagerDTO();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT idx, memberId, bbsTitle, bbsContent, updatedAt");
		sb.append(" FROM tbl_bbs");
		sb.append(" WHERE idx = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, targetId);

			rs = pstm.executeQuery();
			if(rs.next()) {
					dto.setTargetId(rs.getInt("idx"));
					dto.setRmemberId(rs.getString("memberId"));
					dto.setReportTitle(rs.getString("bbsTitle"));
					dto.setReportContent(rs.getString("bbsContent"));
					dto.setReportUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));

			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고 받은 학습 게시판 확인 오류." + e.getMessage());
		}
		return dto;
	}
	/**
	 * 신고 받은 학습 게시글 삭제
	 *
	 * @param 신고받은Id(targetId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public int reportDelete1(int targetId) {
		StringBuilder sb = new StringBuilder();
		int rs = 0;
		sb.append("DELETE FROM tbl_learning");
		sb.append(" WHERE idx=?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, targetId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고받은 학습게시글 삭제 오류" + e.getMessage());
		}

		return rs;
	}
	/**
	 * 신고 받은 카티고리 게시글 삭제
	 *
	 * @param 신고받은Id(targetId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public int reportDelete2(int targetId) {
		StringBuilder sb = new StringBuilder();
		int rs = 0;
		sb.append("DELETE FROM tbl_bbs");
		sb.append(" WHERE idx=?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, targetId);

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고받은 학습게시글 삭제 오류" + e.getMessage());
		}

		return rs;
	}


	/**
	 * 관리자 페이지에서 총 문의 조회
	 * @param map 검색 조건을 담은 Map(search_category, search_word)
	 * @return ManagerDTO
	 * @example int 총 회원 수 (검색 조건 없으면 전체 회원 수)
	 */
	public int getInquiryTotalCount(Map<String, Object> map) {
		int totalCount = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM tbl_customer_inquiry");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}
		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 전체 조회 에러 : "+ e.getMessage());
		}
		return totalCount;
	}

	/**
	 * 관리자 페이지에서 문의 목록을 조회
	 *
	 * @param map 검색 조건을 포함한 Map (search_category, search_word, page_skip_count, page_size)
	 * @return List<BbsDTO>
	 *
	 * @example getReportList(map)
	 */
	public List<ManagerDTO> getInquiryList(Map<String, Object> map){

		List<ManagerDTO> list = new Vector<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT idx, memberId, inquiryTitle, inquiryStatus, updatedAt");
		sb.append(" FROM tbl_customer_inquiry");

		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}

		sb.append(" ORDER BY idx");

		if ( map.get("page_skip_count") != null && map.get("page_size") != null ) {
			sb.append(" LIMIT "+ map.get("page_skip_count") +", "+ map.get("page_size"));
		}

		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();

			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
				dto.setInquiryIdx(rs.getInt("idx"));
				dto.setSmemberId(rs.getString("memberId"));//문의한 멤버ID
				dto.setInquiryTitle(rs.getString("inquiryTitle")); //문의 제목
				dto.setInquiryStatus(rs.getBoolean("inquiryStatus"));//처리상태
				dto.setInquiryUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("관리자 리스트 조회 에러 : "+ e.getMessage());
		}

		return list;
	}
	/**
	 * 문의 페이지 상세
	 *
	 * @param 문의Id(inquiryIdx)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */
	public ManagerDTO inquiryDetail(int inquiryIdx ) {
		ManagerDTO dto = new ManagerDTO();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.idx, a.memberId, a.inquiryTitle, a.inquiryContent, a.inquiryStatus,a.updatedAt, a.createdAt, b.idx as resolutionIdx,  b.managerId,  b.iResolutionContent, b.createdAt AS resolutionCreatedAt");
		sb.append(" FROM tbl_customer_inquiry AS a");
		sb.append(" LEFT JOIN tbl_inquiry_resolution AS b ON a.idx = b.inquiryIdx");
		sb.append(" WHERE a.idx = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, inquiryIdx);

			rs = pstm.executeQuery();
			if (rs.next()) {
				dto.setInquiryIdx(rs.getInt("idx"));
				dto.setSmemberId(rs.getString("memberId"));
				dto.setInquiryTitle(rs.getString("inquiryTitle"));
				dto.setInquiryContent(rs.getString("inquiryContent"));
				dto.setInquiryStatus(rs.getBoolean("inquiryStatus"));
				dto.setInquiryUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));

				int resolutionIdx = rs.getInt("resolutionIdx");
				if (!rs.wasNull()) {
					dto.setInquiryIdx(resolutionIdx);
					dto.setrManagerId(rs.getString("managerId"));
					dto.setIResolutionContent(rs.getString("iResolutionContent"));
					dto.setResolutionCreatedAt(dUtil.toLocalDateTime(rs.getDate("resolutionCreatedAt")));
					System.out.println(dto.getIResolutionContent());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("신고 상세 페이지 오류" + e.getMessage());
		}

		return dto;
	}
	/**
	 * 신고 페이지 답변
	 *
	 * @param 신고Id(reportIdx), 답변(rResolutionContent), 답변한 관리자 Id(managerId)
	 * @return ManagerDTO
	 *
	 * @example getReportList(map)
	 */

	public int resolveInquiry(int inquiryIdx, String managerId, String iResolutionContent) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("CALL insert_inquiry_and_update_status(?, ?, ?)");
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setInt(1, inquiryIdx);
			pstm.setString(2, managerId);
			pstm.setString(3, iResolutionContent);

			rs = pstm.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("문의 답변 오류" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 관리자 추가
	 *
	 * @param dto(managerId, managerPwd, managerName, managerEmail, managerStatus)
	 * @return int
	 *
	 * @example managerAdd(ManagerDTO dto)
	 */
	public int managerAdd(ManagerDTO dto) {
		int rs =0;
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tbl_manager(managerId,managerPwd,managerName,managerEmail,managerStatus)");
		sb.append(" VALUES(?,SHA2(?,256),?,?,?)");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getManagerId());
			pstm.setString(2, dto.getManagerPwd());
			pstm.setString(3, dto.getManagerName());
			pstm.setString(4, dto.getManagerEmail());
			pstm.setInt(5, dto.getManagerStatus());

			rs = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("관리자 추가 오류" + e.getMessage());
		}

		return rs;
	}
}
