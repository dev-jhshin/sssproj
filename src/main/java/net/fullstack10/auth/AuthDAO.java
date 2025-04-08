package net.fullstack10.auth;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.DBConnPool;

public class AuthDAO extends DBConnPool {
	private CommonDateUtil dUtil;
	public AuthDAO () {
		super();
		dUtil = new CommonDateUtil();
	}
	public AuthDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
	}
	/**
	 * 회원 가입 메서드
	 * @param  dto 회원 정보가 담긴 AuthDTO 객체 (ID, 비밀번호, 이름, 생년월일, 이메일, 성별, 보안질문 ID, 답변 포함)
	 * @return 회원가입 성공 여부 (1: 성공, 0: 실패)
	 * @example signUp(new AuthDTO("testUser", "testPwd", "홍길동", "1990-01-01", "test@naver.com", "MALE", 1, "답변"))
	 */
	public int signUp(AuthDTO dto) {
	
		StringBuilder sb = new StringBuilder();
		sb.append("CALL signUp(?,?,?,?,?,?,?,?)");
		int rs = 0;
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getMemberId());
			pstm.setString(2, dto.getMemberPwd());
			pstm.setString(3, dto.getMemberName());
			pstm.setDate(4, new java.sql.Date(dUtil.toDate(dto.getMemberBirthDate()).getTime()));
			pstm.setString(5, dto.getMemberEmail());
			pstm.setString(6, dto.getMemberGender());
			pstm.setInt(7, dto.getQuestionId());
			pstm.setString(8, dto.getAnswer());
			rs = pstm.executeUpdate();
			
			if(rs > 0) {
				System.out.println("회원가입 성공");

			}else {
				System.out.println("실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("회원가입오류" +e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 아이디 중복 체크 메서드
	 * @param memberId
	 * @return 회원가입 성공 여부 (0: 실패, 1: 성공)
	 * @example inDuplicate(memberId)
	 */
	public int idDuplicate(String memberId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT memberName FROM tbl_member");
		sb.append(" WHERE memberId = ?");
		
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				System.out.println("아이디 중복");
				return 0; 
			}else {
				System.out.println("아이디 사용 가능");
				return 1;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("아이디 중복 확인 오류" + e.getMessage());
		}
		
		return 0;
	}
	
	/**
	 * 사용자 로그인 메서드
	 * @param dto 로그인시 필요한 사용자 정보(memberId, memberPwd)
	 * @return 로그인 성공시 memberId,memberStatus가 있는 객체반환, null 객체 반환
	 * @example login(new AuthDTO("testUser", "testPwd"))
	 */
	public AuthDTO authLogin(AuthDTO dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT memberId, memberPwd, memberStatus");
		sb.append(" FROM tbl_member");
		sb.append(" WHERE memberId = ?");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getMemberId());
			
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("memberPwd").equals(dto.getMemberPwd())) {
					dto.setMemberId(rs.getString("memberId"));
					dto.setMemberStatus(rs.getInt("memberStatus"));
					System.out.println("로그인성공!");
				}else {
					dto = null;
					System.out.println("비밀번호 오류");
				}
			}else {
				dto = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("로그인 오류" + e.getMessage());
		}
		return dto;
	}
	/**
	 * 사용자 ID 검증(회원 정보와 비밀번호 찾기 질문 기반으로 확인)
	 * @param dto AuthDTO 객체 (memberId, memberName, questionId, answer)
	 * @return 일치하는 memberId (없으면 null)
	 * @example signUp(new AuthDTO("testUser", "홍길동", 1, "답변"))
	 */
	public String memberVerification(AuthDTO dto) {
		String memberId ="";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.memberId");
		sb.append(" FROM tbl_member AS m");
		sb.append(" INNER JOIN tbl_pwd_answer AS p");
		sb.append(" on m.memberId = p.memberId");
		sb.append(" WHERE m.memberId = ?");
		sb.append(" AND m.memberName = ?");
		sb.append(" AND p.questionId = ?");
		sb.append(" AND p.answer LIKE CONCAT('%', ?, '%')");
		
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getMemberId());
			pstm.setString(2, dto.getMemberName());
			pstm.setInt(3, dto.getQuestionId());
			pstm.setString(4, dto.getAnswer());
			
			rs = pstm.executeQuery();
			if(rs.next()) {
				System.out.println("확인 완료");
				memberId = rs.getString("memberId");
			}
			else {
				System.out.println("확인 실패");
				memberId = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("확인 중 오류 발생"+ e.getMessage());
		}
		return memberId;
	}
	

	/**
	 * 관리자 페이지에서 총 회원수 조회
	 * @param map 검색 조건을 담은 Map(search_category, search_word)
	 * @return MemberDTO
	 * @example int 총 회원 수 (검색 조건 없으면 전체 회원 수)  
	 */
	public int getMemberTotalCount(Map<String, Object> map) {
		int totalCount = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM tbl_member");
		
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
	 * 관리자 페이지에서 회원 목록을 조회
	 * 
	 * @param map 검색 조건을 포함한 Map (search_category, search_word, page_skip_count, page_size)
	 * @return List<BbsDTO>
	 * 
	 * @example getMemberList(map)
	 */
	public List<AuthDTO> getMemberList(Map<String, Object> map){
		
		List<AuthDTO> list = new Vector<AuthDTO>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT idx, memberId, memberName, memberBirthdate");
		sb.append(", memberEmail, memberStatus, memberGender, createdAt, lastLoginAt");
		sb.append(" FROM tbl_member");
		
		if ( map.get("search_category") != null && map.get("search_word") != null ) {
			sb.append(" WHERE "+ map.get("search_category"));
			sb.append(" LIKE '%"+ map.get("search_word") +"%'");
		}

		sb.append(" ORDER BY idx DESC");
		
		if ( map.get("page_skip_count") != null && map.get("page_size") != null ) {
			sb.append(" LIMIT "+ map.get("page_skip_count") +", "+ map.get("page_size"));
		}
		
		try {
			pstm = conn.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				AuthDTO dto = new AuthDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberBirthDate(dUtil.toLocalDate(rs.getDate("memberBirthdate")));
				dto.setMemberEmail(rs.getString("memberEmail"));
				dto.setMemberStatus(rs.getInt("memberStatus"));
				dto.setMemberGender(rs.getString("memberGender"));
				dto.setMemberCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				dto.setMemberLoginAt(dUtil.toLocalDateTime(rs.getDate("lastLoginAt")));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 리스트 조회 에러 : "+ e.getMessage());
		}
		
		return list;
	}
	/**
	 * 비밀번호  변경 메서드
	 * @param memberId, memberPwd
	 * @return 비밀번호 변경 성공 여부 
	 * @example changePwd(memberId, memberPwd)
	 */
	public int chagePwd(String memberId,String memberPwd) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tbl_member");
		sb.append(" SET memberPwd = ?");
		sb.append(" WHERE memberId = ?");
		
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberPwd);
			pstm.setString(2, memberId);
			rs = pstm.executeUpdate();
			
			if(rs>0) {
				System.out.println("비밀번호 변경성공");
			}else {
				System.out.println("비밀번호 변경 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("비밀번호 변경 에러" + e.getMessage());
		}
		
		return rs;
	}
	
	/**
	 * @desc 아이디로 회원 정보 조회
	 * @param memberId String
	 * @return MemberDTO
	 * @example getMemberInfo
	 */
	public AuthDTO getMemberInfo(String memberId) {
		// 조회한 회원 정보를 저장할 객체
		AuthDTO dto = new AuthDTO();

		//1. DB 객체 설정 --> 생성자 호출시에 반영

		//2. 쿼리 구문 작성
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		// 이름, 아이디, 이메일, 생일, 서별 
		sb.append(" memberId, memberName, memberEmail, memberStatus, memberGender, memberBirthdate ");
		sb.append(" FROM tbl_member");
		sb.append(" WHERE memberId = ?");

		try {
			//3. PreparedStatement 구문 생성 및 변수 할당
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);

			//4. 쿼리 수행
			rs = pstm.executeQuery();
			if (rs.next()) {
				//5. 수행 결과 --> MemberDTO 변수에 저장
				dto.setMemberId(memberId);
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberEmail(rs.getString("memberEmail"));
				dto.setMemberStatus(rs.getInt("memberStatus"));
				dto.setMemberGender(rs.getString("memberGender"));
				dto.setMemberBirthDate(dUtil.toLocalDate(rs.getDate("memberBirthdate")));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	/**
	 * @desc 아이디로 회원 정보 조회
	 * @param memberId String
	 * @return MemberDTO
	 * @example getMemberInfo
	 */
	public int updateLastLoginAt(String memberId) {
		int rs = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE tbl_member");
		sb.append(" SET lastLoginAt = now()");
		sb.append(" WHERE memberId = ?");
		
		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);
			
			rs = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("마지막 로그인 업데이토 오류 :" + e.getMessage());
		
		}
		return rs;
	}
}
