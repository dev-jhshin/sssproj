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
 * Servlet implementation class ManagerReportPageController
 */
@WebServlet(name = "manager/reportPage.do", urlPatterns = { "/manager/reportPage.do" })
public class ManagerReportPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int reportIdx = Integer.parseInt(request.getParameter("reportIdx"));
		int targetId =Integer.parseInt(request.getParameter("targetId"));
		String targetType = request.getParameter("targetType");
		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = new ManagerDTO();
		if (targetType.equals("tbl_learning")) {
			dto = dao.reportPost1(targetId);
			dao.close();
			if (dto == null) {
				JSFunction.alertLocation(response, "replace", "확인", "./reportList.do");
				return;
			}
			else {
				request.setAttribute("targetType", targetType);
			}
		} else if (targetType.equals("tbl_bbs")) {
			dto = dao.reportPost2(targetId);
			dao.close();
			if (dto == null) {
				JSFunction.alertLocation(response, "replace", "오류 발생", "./reportList.do");
				return;
			}else {
				request.setAttribute("targetType", targetType);
			}
		} else {
			JSFunction.alertLocation(response, "replace", "잘못된 요청", "./reportList.do");
			return;
		}
		request.setAttribute("reportIdx", reportIdx);
		request.setAttribute("dto", dto);
	
		request.getRequestDispatcher("/WEB-INF/views/manager/report_page_handle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
