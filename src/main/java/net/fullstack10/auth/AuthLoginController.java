package net.fullstack10.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AuthLoginController
 */
@WebServlet("/auth/login.do")
public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AuthDAO dao;
	CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/login_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션사용 선언
		dao = new AuthDAO();
		HttpSession session = request.getSession();
		String memberId = request.getParameter("memberId");
		memberId = (memberId != null) ? memberId.trim() : "";
		String memberPwd = request.getParameter("memberPwd");
		memberPwd = (memberPwd != null) ? memberPwd.trim() : "";
		String saveIdFlag = request.getParameter("saveId");
		System.out.println(memberId + ""+ saveIdFlag);
		
		
		//ID, PWD 유효성검사 진행
		if(memberId == null || memberId.isEmpty() || !memberId.matches("^[a-z0-9]{5,20}$")) {
			request.setAttribute("error1", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.");
			JSFunction.alertLocation(response, "replace", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.", "./login.do");
			return;
		}

		if(memberPwd == null || memberPwd.isEmpty() || !memberPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
			request.setAttribute("error2", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.");
			JSFunction.alertLocation(response, "replace", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.", "./login.do");
			return;
		}
		
		AuthDTO dto = new AuthDTO();
		CommonUtil cUtil = new CommonUtil();
		dto.setMemberId(memberId);
		dto.setMemberPwd(memberPwd);		
		AuthDAO dao = new AuthDAO();
		dto = dao.authLogin(dto);
		
		// Login 성공 
		if(dto != null && dto.getMemberId()!=null) {
			if(saveIdFlag != null && saveIdFlag.equals("Y")) {
				//아이디저장은 7일간만 유지
				cUtil.makeCookie(response, "", "/", 60*60*24*7, "saveIdFlag", saveIdFlag);
				cUtil.makeCookie(response, "", "/", 60*60*24*7, "saveId", memberId);
			} else {
				cUtil.makeCookie(response, "", "/", 0, "saveIdFlag", "");
				cUtil.makeCookie(response, "", "/", 0, "saveId", "");
			}
			dao.updateLastLoginAt(memberId);
			dao.close();
			
			session.setAttribute("memberId", dto.getMemberId());
			session.setAttribute("memberStatus", dto.getMemberStatus());
			session.setMaxInactiveInterval(3600);
			// 로그인 성공시 쿠키 삭제 
			cUtil.makeCookie(response, "", "/", 0, "failId", "");
			response.sendRedirect("/sssproj/learning/today.do");
		} else { 
			// Login 실패
			dao.close();
			Cookie cookie = new Cookie("failId", "1");
			String failId = cUtil.getCookieInfo(request, "failId");
				// faiId 쿠키가 있는지 검사
				if (failId != null && !failId.isEmpty()) {
					int iFailId = Integer.parseInt(failId);
					// 5회 미만 틀렸다면 값 1 증가
					if(iFailId < 5) {
						iFailId++;
						failId = String.valueOf(iFailId);
						cookie.setValue(failId);
						response.addCookie(cookie);
						JSFunction.alertLocation(response, "replace", "비밀번호 ("+ failId +"/5)회 틀렸습니다. 로그인 페이지로 이동합니다.", "./login.do");
					} else {
						// 5회 넘어 갈시에 쿠키값 0 = 삭제 처리 후 비밀번호찾기 페이지 이동
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						JSFunction.alertLocation(response, "replace", "비밀번호 5회 틀렸습니다. 비밀번호 찾기 페이지로 이동합니다.", "./memberVerification.do");
					}
				} else {
					response.addCookie(cookie);
					JSFunction.alertLocation(response, "replace", "로그인정보가 올바르지 않습니다.. 비밀번호 5회 틀릴시에 로그인 페이지로 이동합니다.", "./login.do");
				}
			} 
		}
	}

