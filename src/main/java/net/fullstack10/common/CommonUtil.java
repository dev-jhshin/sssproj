package net.fullstack10.common;

//import org.apache.tomcat.jakartaee.commons.lang3.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CommonUtil {
	public boolean isNumberic(String str) {
		return str != null && !str.isEmpty() && !str.isBlank() && str.matches("[0-9]+");
 	}
	
	public int parseInt(String no) {
		return (isNumberic(no) ? Integer.parseInt(no) : 0);
	}
	
	public int parseInt(String no, String def) {
		return (isNumberic(no) ? Integer.parseInt(no) : (def != null && !def.isEmpty() && !def.isBlank() ? Integer.parseInt(def) : 0));
	}
	
//	public int parseStringToInt(String param, String def) {
//		return (StringUtils.isNumeric(param) ? Integer.parseInt(param) : (def != null && !def.isEmpty() && !def.isBlank() ? Integer.parseInt(def) : 0));
//	}

	/**
	 * @description 페이지 파라미터 값 처리 
	 * @param src
	 * @param def
	 * @return
	 */
	public String setPageParam(String src, String def) {
		// src 값이 null이거나 숫자가 아니면 def 반환 
		if (src == null || src.equals("") || !isNumberic(src)) {
			return def;
		}
		return src.trim();
	}
	/**
	 * @description 검색 카테고리, 검색어 처리 
	 * @param src
	 * @return
	 */
	public String setSearchParam(String src) {
		return src != null && !src.isEmpty() ? src.trim() : "";
	}
	
	// 쿠키 등록
	public void makeCookie(HttpServletResponse res
			, String domain, String path, int exp
			, String cName, String cValue) {

		// 쿠키 생성
		Cookie cookie = new Cookie(cName, cValue);
		if ( domain != null ){
			cookie.setDomain(domain);
		}
		if ( path != null ){
			cookie.setPath(path);
		}
		cookie.setMaxAge(exp);
		res.addCookie(cookie);
	}
	
	// 쿠키정보 조회
	public String getCookieInfo(HttpServletRequest req, String cName) {
		String rtnValue = "";
		Cookie[] cookies = req.getCookies();
		if ( cookies != null ) {
			for (Cookie c : cookies){
				if ( cName.equals(c.getName()) ) {
					rtnValue = c.getValue();
					break;
				}
			}
		}
		return rtnValue;
	}

}