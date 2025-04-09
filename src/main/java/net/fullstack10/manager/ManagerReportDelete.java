package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class ManagerReportDelete
 */
@WebServlet("/manager/reportDelete.do")
public class ManagerReportDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int targetId = Integer.parseInt(request.getParameter("targetId"));
		int reportId = Integer.parseInt(request.getParameter("reportId"));
		String targetType = request.getParameter("targetType");


		 ManagerDAO dao = new ManagerDAO();
		    int rs = 0;

		    if ("tbl_learning".equals(targetType)) {
		        rs = dao.reportDelete1(targetId);
		    } else if ("tbl_bbs".equals(targetType)) {
		        rs = dao.reportDelete2(targetId);
		    } else {
		    	JSFunction.alertLocation(response, "replace", "삭제가 완료되었습니다.", "./reportPage.do?targetId=" + targetId + "&targetType=" + targetType + "&reportIdx=" + reportId);
		        dao.close();
		        return;
		    }

		    dao.close();

		    if (rs > 0) {
		    	JSFunction.alertLocation(response, "replace", "삭제가 완료되었습니다.", "./reportPage.do?targetId=" + targetId + "&targetType=" + targetType + "&reportIdx=" + reportId);
		    } else {
		    	JSFunction.alertLocation(response, "replace", "삭제가 완료되었습니다.", "./reportPage.do?targetId=" + targetId + "&targetType=" + targetType + "&reportIdx=" + reportId);
		    }
		}

}
