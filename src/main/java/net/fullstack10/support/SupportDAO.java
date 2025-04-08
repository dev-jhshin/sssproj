package net.fullstack10.support;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.DBConnPool;

public class SupportDAO extends DBConnPool{
	private CommonDateUtil dUtil;
	private CommonUtil cUtil;
	public SupportDAO() {
		super();
		dUtil = new CommonDateUtil();
		cUtil = new CommonUtil();
	}
	
	public SupportDAO(String ct, String ds) {
		super(ct, ds);
		dUtil = new CommonDateUtil();
		cUtil = new CommonUtil();
	}
	
	/**
	 * @description 문의 글 개수 조회 
	 * @param map
	 * @return
	 */
	public int getInquirySize(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		sql.append("select count(*) as count ");
		sql.append(" from tbl_customer_inquiry ");
		sql.append(" WHERE 1 = 1 ");
		if (map.get("searchCategory") != null && !map.get("searchCategory").equals("") && map.get("searchWord") != null && !map.get("searchWord").equals("")) {
			sql.append(" AND " + map.get("searchCategory"));
			sql.append(" LIKE ? ");
		}
		if (map.get("memberId") != null && !map.get("memberId").equals("") ) {
			sql.append(" AND memberId = ?");
		}
		try {
			pstm = conn.prepareStatement(sql.toString());
			int index = 1;
			if(map.get("searchCategory") != null  && !map.get("searchCategory").equals("") && map.get("searchWord") != null && !map.get("searchWord").equals("")) {
				pstm.setString(index++, map.get("searchWord").toString());
			}
			if (map.get("memberId") != null && !map.get("memberId").equals("") ) {
				pstm.setString(index++, map.get("memberId").toString());
			}
			rs = pstm.executeQuery();
			rs.next();
			System.out.println(rs.getInt("count"));
			return rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * @description 문의 게시글 작성 
	 * @param memberId
	 * @param inquiryTitle
	 * @param inquiryContent
	 * @return
	 */
	public int setInquiryRegist(String memberId, String inquiryTitle, String inquiryContent) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_customer_inquiry ( ");
		sql.append(" memberId, inquiryTitle, inquiryContent  ");
		sql.append(" ) VALUES ( ");
		sql.append(" ?, ?, ?) ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, memberId);
			pstm.setString(2, inquiryTitle);
			pstm.setString(3, inquiryContent);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @description 회원의 문의 내역 조회 
	 * @param memberId
	 * @return
	 */
	public List<InquiryDTO> getInquiryList(Map<String, Object> map) {
		if(map.get("memberId")==null || map.get("memberId").equals("")) return null;
		List<InquiryDTO> inquirys = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		int index = 1;
		sql.append("SELECT ");
		// tbl_customer_inquiry 정보 
		sql.append(" tci.idx as 'idx', tci.inquiryTitle as 'inquiryTitle', tci.inquiryContent as 'inquiryContent', tci.memberId as 'memberId', tci.createdAt as 'createdAt', tci.updatedAt as 'updatedAt'");
		// tbl_inquiry_resolution 정보
		sql.append(", CASE WHEN tir.inquiryIdx IS NOT NULL THEN 1 ELSE 0 END AS 'inquiryStatus'");
		
		sql.append(" FROM tbl_customer_inquiry tci ");
		sql.append(" LEFT OUTER JOIN tbl_inquiry_resolution tir ");
		sql.append(" ON tci.idx = tir.inquiryIdx ");
		
		sql.append(" WHERE tci.memberId = ? ");
		
		if(map.get("searchWord")!=null && !map.get("searchWord").equals("") && map.get("searchCategory")!=null && !map.get("searchCategory").equals("")) {
			sql.append(" AND tci.");
			sql.append(map.get("searchCategory"));
			sql.append(" LIKE ?");
		}
		sql.append(" ORDER BY tci.idx desc ");
		// 페이징 부분 
		if (map.get("pageSkipCount") != null && map.get("pageSize") != null) {
			sql.append(" LIMIT ? , ?");
		}
				
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(index++, map.get("memberId").toString());
			if(map.get("searchWord")!=null && !map.get("searchWord").equals("") && map.get("searchCategory")!=null && !map.get("searchCategory").equals("")) {
				
				pstm.setString(index++, "%" + map.get("searchWord").toString()+ "%");
			}
			if(map.get("pageSkipCount")!=null && map.get("pageSize")!=null) {
				pstm.setInt(index++, cUtil.parseInt(map.get("pageSkipCount").toString()));
				pstm.setInt(index++, cUtil.parseInt(map.get("pageSize").toString()));
			}
			rs = pstm.executeQuery();
			while(rs.next()) {
				InquiryDTO dto = new InquiryDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setInquiryTitle(rs.getString("inquiryTitle"));
				dto.setInquiryContent(rs.getString("inquiryContent"));
				dto.setInquiryStatus(rs.getInt("inquiryStatus"));
				dto.setMemberId(rs.getString("memberId"));
				if (rs.getDate("createdAt")!=null) {
					dto.setCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				} 
				if (rs.getDate("updatedAt")!=null) {
					dto.setUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));
				}
				inquirys.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inquirys;
	}

	/**
	 * @description 문의 게시글 한 건 조회 
	 * @param idx
	 * @return
	 */
	public InquiryDTO getInquiry(String idx) {
		StringBuilder sql = new StringBuilder();
		InquiryDTO dto = new InquiryDTO();
		
		sql.append("SELECT ");
		sql.append(" tci.idx as 'idx', tci.inquiryTitle as 'inquiryTitle', tci.inquiryContent as 'inquiryContent', tci.memberId as 'memberId', tci.createdAt as 'createdAt' , tci.updatedAt as 'updatedAt' ");
		sql.append(", iResolutionContent as 'iResolutionContent', tir.managerId as 'managerId',  CASE WHEN tir.inquiryIdx IS NOT NULL THEN 1 ELSE 0 END AS 'inquiryStatus' ");
		sql.append(" FROM tbl_customer_inquiry tci ");
		sql.append(" LEFT OUTER JOIN tbl_inquiry_resolution tir ");
		sql.append(" ON tci.idx = tir.inquiryIdx ");
		sql.append(" WHERE tci.idx = ? ");
		sql.append(" GROUP BY tci.idx ");
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			rs = pstm.executeQuery();
			if (rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setInquiryTitle(rs.getString("inquiryTitle"));
				dto.setInquiryContent(rs.getString("inquiryContent"));
				dto.setInquiryStatus(rs.getInt("inquiryStatus"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setCreatedAt(dUtil.toLocalDateTime(rs.getDate("createdAt")));
				dto.setUpdatedAt(dUtil.toLocalDateTime(rs.getDate("updatedAt")));
				dto.setiResolutionContent(rs.getString("iResolutionContent"));
				dto.setManagerId(rs.getString("managerId"));
			}
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * @description 문의 게시글 삭제 
	 * @param idx
	 * @return
	 */
	public int setInquiryDelete(String idx) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_customer_inquiry");
		sql.append(" WHERE idx = ? ");
		
		try {
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, idx);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
}
