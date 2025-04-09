package net.fullstack10.bbs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class BbsLikeRegistController
 */
@WebServlet("/bbs/like/regist.do")
public class BbsLikeRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsLikeRegistController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String idx = request.getParameter("idx");
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		bbsDAO = new BbsDAO();
		int result = bbsDAO.setBbsLikeRegist(idx, memberId);
		bbsDAO.close();
		if (result > 0 ) { 
			JSFunction.alertLocation(response, "좋아요 등록 성공", "/sssproj/bbs/view.do?idx="+idx); return;
		} 
		
		JSFunction.alertLocation(response, "좋아요 등록 실패", "/sssproj/bbs/view.do?idx=" + idx); return;			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
