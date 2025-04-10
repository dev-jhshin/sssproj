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
 * Servlet implementation class AuthIdCheck
 */
@WebServlet(name = "auth/idCheck.do", urlPatterns = { "/auth/idCheck.do" })
public class AuthIdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("member_id");
		memberId = (memberId != null) ? memberId.trim() : "";
		String memberEmail = request.getParameter("member_email");
		memberEmail = (memberEmail != null) ? memberEmail.trim() : "";
		
		if(memberId == null || memberId.isEmpty() || !memberId.matches("^[a-z0-9]{5,20}$")) {
			request.setAttribute("error1", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.");
			JSFunction.alertLocation(response, "replace", "아이디를 다시 입력하세요", "./findPwdEmail.do");
			return;
		}
		if(memberEmail == null || memberEmail.isEmpty() || !memberEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			request.setAttribute("error5", "이메일 형식이 올바르지 않습니다.");
			JSFunction.alertLocation(response, "replace", "이메일 형식이 올바르지 않습니다.", "./findPwdEmail.do");
			return;
		}
		AuthDAO dao = new AuthDAO();
		int rs = dao.isVaildMenberEmail(memberId, memberEmail);
		dao.close();
		if(rs>0) {
			// 6자리 인증 코드 생성
		    int code1 = (int)(Math.random() * 900000) + 100000;
		    String code = String.valueOf(code1);

		    String subject = "숲공에서 보낸 인증 메일 입니다.";
		    String content = "인증번호 " + code + " 입니다.";

		    try {
		        MailSender mail = new MailSender();
		        mail.sendEmail(memberEmail, subject, content);
		    } catch (MessagingException e) {
		        e.printStackTrace();
		        response.getWriter().write("fail");
		        return;
		    }
		    request.setAttribute("memberId", memberId);
		    request.setAttribute("memberEmail", memberEmail);
		    request.setAttribute("code", code);
		    request.setAttribute("accord", "1");
		    request.getRequestDispatcher("/WEB-INF/views/auth/emailChoice.jsp").forward(request, response);
		}else {
			JSFunction.alertLocation(response, "replace", "아이디와 이메일이 일치하지 않습니다.", "./findPwdEmail.do");
		}

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
