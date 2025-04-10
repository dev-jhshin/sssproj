package net.fullstack10.user;

import java.util.ArrayList;
import java.util.List;

import net.fullstack10.auth.AuthDTO;
import net.fullstack10.common.DBConnPool;

public class UserDAO extends DBConnPool {
	/**
	 * @desc 아이디로 회원 조회
	 * @param memberId String
	 * @return AuthDTO
	 * @example getUserById
	 */
	public AuthDTO getUserById(String memberId) {
		AuthDTO dto = new AuthDTO();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append(" memberId, memberName, memberBirthdate, memberEmail, memberGender ");
		sb.append(" FROM tbl_member ");
		sb.append(" WHERE memberId = ? ");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);

			rs = pstm.executeQuery();
			if(rs.next()) {
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberBirthDate(rs.getDate("memberBirthdate").toLocalDate());
				dto.setMemberEmail(rs.getString("memberEmail"));
				dto.setMemberGender(rs.getString("memberGender"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	/**
	 * @desc 검색어(이름, 아이디)로 다른 회원 목록 검색
	 * @param memberId String
	 * @param keyword String
	 * @return List<AuthDTO>
	 * @example getOtherUserList
	 */
	public List<AuthDTO> getOtherUserList(String memberId, String keyword) {
		List<AuthDTO> list = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append(" memberId, memberName, memberBirthdate, memberEmail, memberGender ");
		sb.append(" FROM tbl_member ");
		sb.append(" WHERE memberId != ? ");
		sb.append(" AND ( memberId LIKE ? OR memberName LIKE ? ) ");

		try {
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, memberId);
			pstm.setString(2, "%" + keyword + "%");
			pstm.setString(3, "%" + keyword + "%");

			rs = pstm.executeQuery();
			while(rs.next()) {
				AuthDTO dto = new AuthDTO();
				dto.setMemberId(rs.getString("memberId"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setMemberBirthDate(rs.getDate("memberBirthdate").toLocalDate());
				dto.setMemberEmail(rs.getString("memberEmail"));
				dto.setMemberGender(rs.getString("memberGender"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
