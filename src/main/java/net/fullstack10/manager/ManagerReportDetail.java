package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManagerReportResolution
 */
@WebServlet("/manager/reportDetail.do")
public class ManagerReportDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reportIdx = Integer.parseInt(request.getParameter("idx"));
		System.out.println(reportIdx);
		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = new ManagerDTO();
		dto = dao.reportDetail(reportIdx);
		dao.close();
		request.setAttribute("report", dto);
		request.getRequestDispatcher("/WEB-INF/views/manager/report_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
