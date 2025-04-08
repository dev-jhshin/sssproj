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
		PrintWriter wrt = response.getWriter();
		
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		if(memberId == null || !(memberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/views/bbs2/cmRegist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wrt = response.getWriter();

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String category = request.getParameter("category");

		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		if(memberId == null || !(memberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(title == null || title.length() < 1 || title.length() > 100) {
			wrt.println("<script>");
			wrt.println("alert('제목을 1자 이상 100자 이내로 입력하세요.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(content == null || content.length() < 1) {
			wrt.println("<script>");
			wrt.println("alert('내용을 입력하세요.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(category == null || !(category.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('카테고리 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}

		BbsDTO dto = new BbsDTO();
		dto.setBbsTitle(title);
		dto.setBbsContent(content);
		dto.setBbsCategory(category);
		dto.setMemberId(memberId);
		if (request.getParts() !=null) {
			BbsFileUpload bfu = new BbsFileUpload();
			dto.setFiles(bfu.fileUpload(request));
		}
		/*
			// 파일 변수
			CommonFileUtil fUtil = new CommonFileUtil();
			String newFile = "";
			String fileExt = "";
			List<Map> files = new ArrayList<>();
			// 파일 업로드 디렉토리 설정
			String saveDir = getServletContext().getRealPath("/Uploads");
			String virtualDir = "/Uploads";

			/// saveDir 바꾸세요!!!!!!!!!!!!!!!!!!!!
			saveDir = "/Users/sinjihye/dev/java10/sssproj/sssproj/src/main/webapp/Uploads";
			/////////////////////////////////////////////////////////////////////////////

			// 파일 업로드
			List<String> orgFiles = fUtil.multiFileUpload(request, saveDir);
			System.out.println(orgFiles);
			if(orgFiles != null && !orgFiles.isEmpty()) {
				for(String orgFile : orgFiles) {
					System.out.println("orgFile:" + orgFile);
					Map<String, String> file = new HashMap<>();
					newFile = fUtil.fileRename(saveDir, orgFile);
					fileExt = fUtil.getFileInfo("FILE_EXT", orgFile);
					file.put("fileName", newFile);
					file.put("fileExt", fileExt);
					file.put("filePath", virtualDir);
					file.put("fileSize", ""+fUtil.getFileSize(saveDir, newFile));
					files.add(file);
				}
			}
			dto.setFiles(files);
		*/

		bbsDAO = new BbsDAO();
		int bbsIdx = bbsDAO.setBbsRegist(dto);
		bbsDAO.close();
		
		if (bbsIdx > 0) {
			wrt.println("<script>");
			wrt.println("alert('게시물이 등록되었습니다.');");
			wrt.println("window.location.replace('view.do?idx=" + bbsIdx + "');");
			wrt.println("</script>");
			wrt.close();
		} else {
			System.out.println("실패");
			wrt.print("<script>");
			wrt.print("alert('게시물 등록이 완료되지 않았습니다.');");
			wrt.print("history.back();");
			wrt.print("</script>");
			wrt.close();
		}
	}

}
