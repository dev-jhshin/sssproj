package net.fullstack10.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class AuthFindPwdController
 */
@WebServlet("/auth/memberVerification.do")
public class AuthFindVerificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/found_pw_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String memberId = request.getParameter("memberId");
			memberId = (memberId != null) ? memberId.trim() : "";
			String memberName = request.getParameter("memberName");
			memberName = (memberName != null) ? memberName.trim() : "";
			String questionId = request.getParameter("security_question");
			questionId = (questionId != null) ? questionId.trim() : "";
			String answer = request.getParameter("answer");
			answer = (answer != null) ? answer.trim() : "";


			if(memberId == null || memberId.isEmpty() || !memberId.matches("^[a-z0-9]{5,20}$")) {
				JSFunction.alertLocation(response, "replace", "인증실패 다시 입력하세요.", "./memberVerification.do");
			}

			if(questionId == null || questionId.isEmpty()) {
				JSFunction.alertLocation(response, "replace", "인증실패 다시 입력하세요.", "./memberVerification.do");
			}

			if(answer == null || answer.isEmpty() || !answer.matches("^[a-zA-Z가-힣0-9]+$")) {
				JSFunction.alertLocation(response, "replace", "인증실패 다시 입력하세요.", "./memberVerification.do");
			}
		    AuthDAO dao = new AuthDAO();
		    AuthDTO dto = new AuthDTO();
			dto.setMemberId(memberId);
			dto.setMemberName(memberName);
			dto.setQuestionId(Integer.parseInt(questionId));
			dto.setAnswer(answer);
			memberId = dao.memberVerification(dto);
			if(memberId != null && !memberId.isEmpty()) {
				response.sendRedirect("./pwdChange?member_id="+memberId);
				dao.close();
				return;
			}else{
				JSFunction.alertLocation(response, "replace", "인증실패 다시 입력하세요.", "./memberVerification.do");
			}

	}

}
