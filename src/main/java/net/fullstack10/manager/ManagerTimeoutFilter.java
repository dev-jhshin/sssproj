package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

/**
 * Servlet Filter implementation class ManagerTimeoutFilter
 */
public class ManagerTimeoutFilter implements Filter {
       Filter config;

	/**
	 * @see Filter#doFilter(HttpServletRequest, HttpServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
			// session.getAttribute바로 사용을 못함 사용을 위해 HttpSession객체 가져오기
			// request.getSession() 메서드는 boolean타입 반환 없으면 만들기때문에 false로 주기
			 HttpSession session = request.getSession(false);
			 if (session == null || session.getAttribute("managerId") == null) {
				    JSFunction.alertLocation(response, "replace", "로그인 해야합니다..", "./login.do");
				    return;
				}
				// 로그인된 상태면 정상적으로 필터 계속 진행
				chain.doFilter(request, response);
	}
}
