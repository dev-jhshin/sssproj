package net.fullstack10.bbs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonFileUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class BbsViewController
 */
@WebServlet("/bbs/delete.do")
public class BbsDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    private CommonFileUtil fUtil = new CommonFileUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String idx = request.getParameter("bbs_idx");
		bbsDAO = new BbsDAO();
		List<Map> files = bbsDAO.getBbsFilesByIdx(idx);

		if(files !=null) {
			for(Map<String, String> file: files) {
				String fileName = file.get("fileName");
				String filePath = file.get("filePath");
				String saveDir = getServletContext().getInitParameter("SaveDirectory");
				fUtil.fileDelete(request, saveDir, fileName);
			}
		}

		int result = bbsDAO.setBbsDelete(idx);
		bbsDAO.close();
		if (result > 0) { JSFunction.alertLocation(response, "게시글이 삭제되었습니다.", "list.do"); } else { JSFunction.alertBack(response, "게시글 삭제에 실패했습니다."); return; }
	}

}
