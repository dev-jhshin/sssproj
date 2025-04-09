package net.fullstack10.learning;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonFileUtil;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;
import net.fullstack10.file.FileDTO;

/**
 * Servlet implementation class LearningDeleteController
 */
@WebServlet("/learning/delete.do")
public class LearningDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		Object redirectURL = session.getAttribute("redirectURL");
		String url = "list.do";
		if (redirectURL != null) {
			url = redirectURL.toString();
			session.removeAttribute("redirectURL");
		}

		String loginMemberId = (String)session.getAttribute("memberId");
		String memberId = request.getParameter("memberId");

		String idx = request.getParameter("idx");

		if (cUtil.parseInt(idx) < 1) {
			JSFunction.alertBack(response, "게시글 정보가 올바르지 않습니다.");
		}
		if (loginMemberId.equalsIgnoreCase(memberId)) {
			JSFunction.alertLocation(response, "href", "권한이 없습니다.", url);
		}

		LearningFileDAO fileDAO = new LearningFileDAO();
		List<FileDTO> files = fileDAO.getFileListByLearningIdx(idx);

		LearningService service = new LearningService();
		boolean result = service.deleteLearning(idx);

		// 파일 삭제 처리
		if (result) {
			CommonFileUtil fUtil = new CommonFileUtil();

			// String saveDir = getServletContext().getRealPath("/Uploads");
			String saveDir = getServletContext().getInitParameter("SaveDirectory");
			String virtualDir = "/Uploads";

			for (FileDTO file : files) {
				fUtil.fileDelete(request, saveDir, file.getFileName());
			}
		}

		String msg = (result ? "게시글 삭제에 성공했습니다." : "게시글 삭제에 실패했습니다.");
		JSFunction.alertLocation(response, "href", msg, url);
	}

}
