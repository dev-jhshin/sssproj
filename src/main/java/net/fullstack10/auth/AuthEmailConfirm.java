package net.fullstack10.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

import java.io.IOException;

import javax.mail.MessagingException;




/**
 * Servlet implementation class AuthEmailConfirm
 */
@WebServlet(name = "auth/emailConfirm.do", urlPatterns = { "/auth/emailConfirm.do" })
public class AuthEmailConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		//6자리 코드 생성
		int code1 = (int)(Math.random() * 900000) + 100000; 
		String code = String.valueOf(code1);
		String userEmail = request.getParameter("email");
		String subject = "숲공에서 보낸 인증 메일 입니다.";
		String content = "인증번호"+ code + "입니다.";
		try {
			MailSender mail = new MailSender();
	        mail.sendEmail(userEmail, subject, content); // ✅ static 방식 + 예외 처리
	    } catch (MessagingException e) {
	        e.printStackTrace(); // 에러 로그 출력
	        response.getWriter().write("메일 전송에 실패했습니다.");
	        return;
	    }
		
		 // 세션에 인증번호 저장 (선택사항)
	    request.setAttribute("authCode", code);
	    request.setAttribute("email", userEmail);

	    // 사용자에게 메시지 출력
	    JSFunction.alertLocation(response, "replace", "이메일이 전송되었습니다.", "./email.jsp");
	}

}
