package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonFileUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class BbsRegistController
 */
@WebServlet("/bbs/regist.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 10)
public class BbsRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsRegistController() {
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
		bbsDAO = new BbsDAO();
		List categories = bbsDAO.getBbsCategory();
		request.setAttribute("categories", categories);
		if(memberId == null || !(memberId.length() > 0)) { JSFunction.alertLocation(response, "로그인 후 이용해주세요.", "/sssproj/auth/login.do"); }
		
		request.getRequestDispatcher("/WEB-INF/views/bbs2/cmRegist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String customCategory = request.getParameter("customCategory");

		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		if(memberId == null || !(memberId.length() > 0)) { JSFunction.alertLocation(response, "로그인 후 이용해주세요.", "/sssproj/auth/login.do"); }

		if(title == null || title.length() < 1 || title.length() > 100) { JSFunction.alertBack(response, "제목을 1~100자로 입력하세요."); }
		
		if(content == null || content.length() < 1) { JSFunction.alertBack(response, "내용을 입력해주세요."); }

		if(category == null || !(category.length() > 0)) { JSFunction.alertBack(response, "카테고리 정보가 없습니다."); }
		
		if(category.equalsIgnoreCase("직접입력")) {
			if(customCategory == null || !(customCategory.length() > 0)) { JSFunction.alertBack(response, "카테고리를 입력해주세요."); }
		}
		
		BbsDTO dto = new BbsDTO();
		dto.setBbsTitle(title);
		dto.setBbsContent(content);
		if (category.equalsIgnoreCase("직접입력")) {
			dto.setBbsCategory(customCategory);
		} else {
			dto.setBbsCategory(category);
		}
		
		dto.setMemberId(memberId);
		
		if (request.getParts() !=null) {
			BbsFileUpload bfu = new BbsFileUpload();
			String saveDir = getServletContext().getInitParameter("SaveDirectory");
			dto.setFiles(bfu.fileUpload(request, saveDir));
		}

		bbsDAO = new BbsDAO();
		int bbsIdx = bbsDAO.setBbsRegist(dto);
		bbsDAO.close();
		
		if (bbsIdx > 0) { 
			JSFunction.alertLocation(response, "replace", "게시글 등록에 성공했습니다.", "view.do?idx=" + bbsIdx);
		} else {
			JSFunction.alertBack(response, "게시글 등록에 실패했습니다.");
		}
	}

}
