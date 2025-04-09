package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonFileUtil;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class BbsModifyController
 */
@WebServlet("/bbs/modify.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 10)
public class BbsModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    private CommonUtil cUtil = new CommonUtil();
    private CommonFileUtil fUtil = new CommonFileUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsModifyController() {
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
		PrintWriter wrt = response.getWriter();

		HttpSession session = request.getSession();
		String sMemberId = (String)session.getAttribute("memberId");
		if (sMemberId == null || sMemberId.length() < 1) { JSFunction.alertLocation(response, "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do"); }

		bbsDAO = new BbsDAO();
		BbsDTO dto = bbsDAO.getBbs(request.getParameter("idx"), sMemberId);
		bbsDAO.close();

		if(!dto.getMemberId().equalsIgnoreCase(sMemberId)) { JSFunction.alertBack(response, "권한이 없습니다."); }

		Map<String, Object> pMap = new HashMap<>();
		pMap.put("bbs", dto);
		request.setAttribute("pMap", pMap);

		request.getRequestDispatcher("/WEB-INF/views/bbs2/cmModify.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String saveDir = getServletContext().getInitParameter("SaveDirectory");

		int idx = cUtil.parseInt(request.getParameter("idx"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String customCategory = request.getParameter("customCategory");
		String memberId = request.getParameter("memberId");
		String sMemberId = (String) request.getSession().getAttribute("memberId");
		String[] deleteFileIdxes = request.getParameterValues("deleteFileIdx");

		if (idx < 1) { JSFunction.alertBack(response, "게시글 정보가 올바르지 않습니다.");}

		if (!sMemberId.equalsIgnoreCase(memberId)) { JSFunction.alertLocation(response, "권한이 없습니다.", "/sssproj/bbs/view.do?idx=" + idx); return; }

		if (title == null || title.length() < 1 || content ==null || content.length() < 1) { JSFunction.alertBack(response, "제목을 1~100자 이내로 입력해주세요."); return; }

		if(content == null || content.length() < 1) { JSFunction.alertBack(response, "내용을 입력해주세요."); return; }

		if(category == null || !(category.length() > 0)) { JSFunction.alertBack(response, "카테고리 정보가 없습니다."); return; }

		if(category.equalsIgnoreCase("직접입력")) {
			if(customCategory == null || !(customCategory.length() > 0)) { JSFunction.alertBack(response, "카테고리를 입력해주세요."); return; }
		}


		if(deleteFileIdxes != null) {
			for(String deleteFileIdx : deleteFileIdxes) {
				String fileName = request.getParameter("fileName_" + deleteFileIdx);
				String fileIdxStr = request.getParameter("fileIdx_" + deleteFileIdx);
				fUtil.fileDelete(request, saveDir, fileName);
				bbsDAO = new BbsDAO();
				bbsDAO.setFileDelete(fileIdxStr);
			}
		}

		bbsDAO = new BbsDAO();
		BbsDTO dto = new BbsDTO();
		dto.setIdx(idx);
		dto.setBbsTitle(title);
		dto.setBbsContent(content);
		dto.setBbsCategory(category);
		if(request.getParts() != null) {
			BbsFileUpload bfu = new BbsFileUpload();
			dto.setFiles(bfu.fileUpload(request, saveDir));
		}
		int result = bbsDAO.setBbsModify(dto);
		bbsDAO.close();

		if (result > 0) {
			JSFunction.alertLocation(response, "게시글 수정에 성공했습니다.", "/sssproj/bbs/view.do?idx=" + idx); return;
		}
		JSFunction.alertLocation(response, "게시글 수정에 성공했습니다.", "/sssproj/bbs/list.do");

	}

}
