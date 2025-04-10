package net.fullstack10.bbs;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class BbsLikeDeleteController
 */
@WebServlet("/bbs/like/delete.do")
public class BbsLikeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    private CommonUtil cUtil = new CommonUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsLikeDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		if(memberId ==null || memberId.equalsIgnoreCase("")) {
			JSFunction.alertLocation(response, "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do");return;
		}

		String idx = request.getParameter("idx");
		if(cUtil.parseInt(idx)<1) {
			JSFunction.alertBack(response, "게시글 정보가 없습니다.");return;
		}
		bbsDAO = new BbsDAO();
		int result = bbsDAO.setBbsLikeDelete(idx, memberId);
		bbsDAO.close();
		
		if(result>0) {
			JSFunction.alertBack(response, "");
			return;
		} else {
			JSFunction.alertBack(response, "");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
