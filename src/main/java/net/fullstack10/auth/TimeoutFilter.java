package net.fullstack10.auth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;

import java.io.IOException;

/**
 * Servlet Filter implementation class TimeoutFilter
 */
public class TimeoutFilter extends HttpFilter implements Filter {
	CommonUtil cUtil = new CommonUtil();  

	/**
	 * @see Filter#doFilter(HttpServletRequest, HttpServletResponse, FilterChain)
	 */
	
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
	// session.getAttribute바로 사용을 못함 사용을 위해 HttpSession객체 가져오기
	// request.getSession() 메서드는 boolean타입 반환 없으면 만들기때문에 false로 주기
	 HttpSession session = request.getSession(false);
	 
	 if (session != null) {
		 //세션이 있고, 세션의 이름이 memberId를 가져온다
	        Object memberId = session.getAttribute("memberId"); 
	        //memberId가 null이면 로그인페이지 반환
	        if (memberId == null) {
	            response.sendRedirect("/auth/login.do"); // 로그인 페이지로 리디렉션
	            return;
	        }
	    } else {
	        response.sendRedirect("login.jsp");
	        return;
	    }
	  chain.doFilter(request, response);
	}
}
