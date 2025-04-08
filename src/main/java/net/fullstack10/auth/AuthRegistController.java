package net.fullstack10.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonDateUtil;
import net.fullstack10.common.JSFunction;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 * Servlet implementation class AuthRegistController
 */
@WebServlet("/auth/regist.do")
public class AuthRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/regist_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthDTO dto = new AuthDTO();
		AuthDAO dao = new AuthDAO();
		CommonDateUtil cUtil = new CommonDateUtil();
		
		PrintWriter pw = response.getWriter();
		
		String memberId = request.getParameter("memberId");
		memberId = (memberId != null) ? memberId.trim() : "";
		String memberPwd = request.getParameter("memberPwd");
		memberPwd = (memberPwd != null) ? memberPwd.trim() : "";
		String memberName = request.getParameter("memberName");
		memberName = (memberName != null) ? memberName.trim() : "";
		String memberBirthdateStr = request.getParameter("memberBirthdate"); // null 체크
		LocalDate memberBirthdate = (memberBirthdateStr != null) ? cUtil.toLocalDate(memberBirthdateStr) : null;
		String memberEmail1 = request.getParameter("memberEmail");
		memberEmail1 = (memberEmail1 != null) ? memberEmail1.trim() : "";
		String memberEmail2 = request.getParameter("select_email");
		memberEmail2 = (memberEmail2 != null) ? memberEmail2.trim() : "";
		String memberEmail = (memberEmail1 + "@" + memberEmail2).trim();
		String memberGender = request.getParameter("memberGender");
		memberGender = (memberGender != null) ? memberGender.trim() : "";
		String questionId = request.getParameter("security_questions[]");
		questionId = (questionId != null) ? questionId.trim() : "";
		String answer = request.getParameter("answer");
		answer = (answer != null) ? answer.trim() : "";

		// 생일과 비교하기 위해 오늘날짜 가져오기
		LocalDate today = LocalDate.now();
		//유효성검사 진행

		if(memberId == null || memberId.isEmpty() || !memberId.matches("^[a-z0-9]{5,20}$")) {
			request.setAttribute("error1", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.");
			JSFunction.alertLocation(response, "replace", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.", "./regist.do");
			return;
		}

		if(memberPwd == null || memberPwd.isEmpty() || !memberPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
			request.setAttribute("error2", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.");
			JSFunction.alertLocation(response, "replace", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.", "./regist.do");
		}

		if(memberName == null || memberName.isEmpty() || !memberName.matches("^[가-힣]{1,20}$")) {
		    request.setAttribute("error3", "이름은 한글 1~20자여야 합니다.");
		    JSFunction.alertLocation(response, "replace", "이름은 한글 1~20자여야 합니다.", "./regist.do");
			return;
		}

		if(memberBirthdate == null || memberBirthdate.isAfter(today)) {
			request.setAttribute("error4", "올바른 생년월일을 입력해주세요.");
			JSFunction.alertLocation(response, "replace", "올바른 생년월일을 입력해주세요.", "./regist.do");
			return;
		}

		if(memberEmail == null || memberEmail.isEmpty() || !memberEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			request.setAttribute("error5", "이메일 형식이 올바르지 않습니다.");
			JSFunction.alertLocation(response, "replace", "이메일 형식이 올바르지 않습니다.", "./regist.do");
			return;
		}

		if(!memberGender.equals("MALE") && !memberGender.equals("FEMALE")) {
			request.setAttribute("error6", "성별을 선택해주세요.");
			JSFunction.alertLocation(response, "replace", "성별을 선택해주세요.", "./regist.do");
			return;
		}

		if(questionId == null || questionId.isEmpty()) {
			request.setAttribute("error7", "비밀번호 찾기 질문을 선택해주세요.");
			JSFunction.alertLocation(response, "replace", "비밀번호 찾기 질문을 선택해주세요.", "./regist.do");
			return;
		}

		if(answer == null || answer.isEmpty() || !answer.matches("^[a-zA-Z가-힣0-9]+$")) {
			request.setAttribute("error8", "비밀번호 답변은 한글 또는 영문, 숫자만 입력 가능합니다.");
			JSFunction.alertLocation(response, "replace", "비밀번호 답변은 한글 또는 영문, 숫자만 입력 가능합니다.", "./regist.do");
			return;
		}
		
		// dto에 저장
		dto.setMemberId(memberId);
		dto.setMemberPwd(memberPwd);
		dto.setMemberName(memberName);
		dto.setMemberBirthDate(memberBirthdate);
		dto.setMemberEmail(memberEmail);
		dto.setMemberGender(memberGender);
		dto.setQuestionId(Integer.parseInt(questionId));
		dto.setAnswer(answer);
		//회원가입 메서드 호출
		int rs = dao.signUp(dto);
		if(rs > 0) {
			JSFunction.alertLocation(response, "replace", "회원가입 성공", "./login.do");
			// response.sendRedirect("./login.do");
		}else {
			JSFunction.alertLocation(response, "replace", "회원정보가 올바르지 않습니다.", "./regist.do");
		}
	}

}
