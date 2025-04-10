package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class ManagerReportResolution
 */
@WebServlet("/manager/reportResolution.do")
public class ManagerReportResolution extends HttpServlet {
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
		HttpSession session = request.getSession();
		String managerId =  (String)session.getAttribute("managerId");
		int reportIdx = Integer.parseInt(request.getParameter("reportIdx"));
		String rResolutionContent = request.getParameter("rResolutionContent");
		System.out.println(managerId + reportIdx +rResolutionContent );
		ManagerDAO dao = new ManagerDAO();
		int rs = dao.resolveReport(reportIdx, managerId, rResolutionContent );
		dao.close();
		if(rs>0) {
			JSFunction.alertLocation(response, "replace", "신고 처리 완료", "./reportDetail.do?idx="+reportIdx);
		}else {
		}JSFunction.alertLocation(response, "replace", "신고 처리 실패, 답변을 다시 작성해주세요", "./reportDetail.do?idx="+reportIdx);

	}

}
