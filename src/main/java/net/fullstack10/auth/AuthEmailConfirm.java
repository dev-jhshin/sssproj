package net.fullstack10.auth;

import java.io.IOException;

import javax.mail.MessagingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




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
	    response.setContentType("text/plain; charset=UTF-8"); // AJAX 응답용

	    // 6자리 인증 코드 생성
	    int code1 = (int)(Math.random() * 900000) + 100000;
	    String code = String.valueOf(code1);

	    String userEmail = request.getParameter("email");
	    String subject = "숲공에서 보낸 인증 메일 입니다.";
	    String content = "인증번호 " + code + " 입니다.";

	    try {
	        MailSender mail = new MailSender();
	        mail.sendEmail(userEmail, subject, content);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        response.getWriter().write("fail");
	        return;
	    }

	    // 필요하다면 세션에 인증코드 저장 가능 (보안 강화용)
	    request.getSession().setAttribute("authCode", code);
	    request.getSession().setAttribute("email", userEmail);

	    // 응답 텍스트로 인증 성공 전달 + 인증 코드 (프론트에서 사용할 수 있게)
	    response.getWriter().write("success:" + code);
	}

}
