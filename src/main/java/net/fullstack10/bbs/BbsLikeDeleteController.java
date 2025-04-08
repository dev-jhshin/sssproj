package net.fullstack10.bbs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;

import java.io.IOException;
import java.io.PrintWriter;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wrt = response.getWriter();
		
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		if(memberId ==null || memberId.equalsIgnoreCase("")) {
			wrt.println("<script>");
			wrt.println("alert('잘못된 접근입니다.')");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
		}
		
		String idx = request.getParameter("idx");
		if(cUtil.parseInt(idx)<1) {
			wrt.println("<script>");
			wrt.println("alert('게시글 정보가 잘못되었습니다.')");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
		}
		bbsDAO = new BbsDAO();
		int result = bbsDAO.setBbsLikeDelete(idx, memberId);
		bbsDAO.close();
				
		if(result>0) {
			wrt.println("<script>");
			wrt.println("alert('좋아요 취소 성공했습니다.')");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
		} else {
			wrt.println("<script>");
			wrt.println("alert('좋아요 취소 실패했습니다.')");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
