package net.fullstack10.auth;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet Filter implementation class TimeoutFilter
 */
public class TimeoutFilter extends HttpFilter implements Filter {
	CommonUtil cUtil = new CommonUtil();

	/**
	 * @see Filter#doFilter(HttpServletRequest, HttpServletResponse, FilterChain)
	 */

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	// session.getAttribute바로 사용을 못함 사용을 위해 HttpSession객체 가져오기
	// request.getSession() 메서드는 boolean타입 반환 없으면 만들기때문에 false로 주기
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
		 HttpSession session = request.getSession(false);
		 if (session == null || session.getAttribute("memberId") == null) {
			    JSFunction.alertLocation(response, "replace", "로그인 해야합니다..", "/sssproj/auth/login.do");
			    return;
			}
			// 로그인된 상태면 정상적으로 필터 계속 진행
			chain.doFilter(request, response);
}
}
