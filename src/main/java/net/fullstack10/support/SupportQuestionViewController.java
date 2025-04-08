package net.fullstack10.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class SupportQuestionViewController
 */
@WebServlet("/support/question/view.do")
public class SupportQuestionViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupportDAO supportDAO;
	private CommonUtil cUtil = new CommonUtil();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportQuestionViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idx = request.getParameter("idx");
		
		// idx validation
		
		supportDAO = new SupportDAO();
		InquiryDTO inquiry = supportDAO.getInquiry(idx);
		supportDAO.close();
		request.setAttribute("inquiry", inquiry);
		request.getRequestDispatcher("/WEB-INF/views/support/supMyQuestionView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
