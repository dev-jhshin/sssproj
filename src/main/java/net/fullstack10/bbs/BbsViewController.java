package net.fullstack10.bbs;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class BbsViewController
 */
@WebServlet("/bbs/view.do")
public class BbsViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO dao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dao = new BbsDAO();
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		String idx = request.getParameter("idx");
		BbsDTO dto = dao.getBbs(idx, memberId);
		dto.setBbsContent(dto.getBbsContent().replace("\n", "<br>"));
		request.setAttribute("bbs", dto);
		dao.setBbsViewCnt(idx);
		dao.close();
		request.getRequestDispatcher("/WEB-INF/views/bbs2/cmInfo.jsp").forward(request, response);
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
