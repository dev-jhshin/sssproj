package net.fullstack10.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

import java.io.IOException;

/**
 * Servlet implementation class ManagerInquiryResolution
 */
@WebServlet(name = "manager/inquiryResolution.do", urlPatterns = { "/manager/inquiryResolution.do" })
public class ManagerInquiryResolution extends HttpServlet {
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
		HttpSession session = request.getSession();
		String managerId =  (String)session.getAttribute("managerId");
		int inquiryIdx = Integer.parseInt(request.getParameter("inquiryIdx"));
		String iResolutionContent = request.getParameter("iResolutionContent");
		System.out.println(managerId + inquiryIdx +iResolutionContent );
		ManagerDAO dao = new ManagerDAO();
		int rs = dao.resolveInquiry(inquiryIdx, managerId, iResolutionContent );
		dao.close();
		if(rs>0) {
			JSFunction.alertLocation(response, "replace", "문의 처리 완료", "./inquiryDetail.do?idx="+inquiryIdx);
		}else {
		}JSFunction.alertLocation(response, "replace", "문의 처리 실패, 답변을 다시 작성해주세요", "./inquiryDetail.do?idx="+inquiryIdx);
	}

}
