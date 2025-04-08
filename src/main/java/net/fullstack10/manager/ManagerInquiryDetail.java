package net.fullstack10.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ManagerInquiryDetail
 */
@WebServlet("/manager/inquiryDetail.do")
public class ManagerInquiryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int inquiryIdx = Integer.parseInt(request.getParameter("idx"));
		System.out.println(inquiryIdx);
		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = new ManagerDTO();
		dto = dao.inquiryDetail(inquiryIdx);
		dao.close();
		request.setAttribute("inquiry", dto);
		request.getRequestDispatcher("/WEB-INF/views/manager/question_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
