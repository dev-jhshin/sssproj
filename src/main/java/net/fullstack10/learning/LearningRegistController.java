package net.fullstack10.learning;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonFileUtil;
import net.fullstack10.common.JSFunction;
import net.fullstack10.file.FileDTO;

/**
 * Servlet implementation class LearningRegistController
 */
@WebServlet("/learning/regist.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 10)
public class LearningRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/learning/msRegist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// 이전 페이지 위치 - 나의 학습, 공유 학습, 모두의 학습
		HttpSession session = request.getSession();
		Object redirectURL = session.getAttribute("redirectURL");
		String url = "list.do";
		if (redirectURL != null) {
			url = redirectURL.toString();
		}

		
		String loginMemberId = (String)session.getAttribute("memberId");

		String learningTitle = request.getParameter("learningTitle");
		String learningContent = request.getParameter("learningContent");
		String isVisible = request.getParameter("isVisible");
		String learningStartedAt = request.getParameter("learningStartedAt");
		String learningEndedAt = request.getParameter("learningEndedAt");
		String isPublic = request.getParameter("isPublic");
		String topics = request.getParameter("topics");
		String hashtags = request.getParameter("hashtags");
		
		String sharedStr = request.getParameter("sharedList");
		List<String> sharedList = ( sharedStr != null && !sharedStr.isEmpty() ?
				Arrays.asList(sharedStr.split(",")) : List.of());
		List<LearningSharedDTO> sharedDTOList = new ArrayList<>();
		for (String user : sharedList) {
			LearningSharedDTO sharedDTO = new LearningSharedDTO();
			sharedDTO.setSharedTo(user);
			sharedDTOList.add(sharedDTO); 
		}
		
		// validation 체크 루틴
		if (loginMemberId == null || loginMemberId.isEmpty()) {
			JSFunction.alertBack(response, "사용자 정보가 없습니다.");
			return;
		}
		if (learningTitle == null || learningTitle.length() < 1 || learningTitle.length() > 100) {
			JSFunction.alertBack(response, "제목을 1자 이상 100자 이하로 입력하세요.");
			return;
		}
		if (learningContent == null || learningContent.length() < 1) {
			JSFunction.alertBack(response, "내용을 입력하세요.");
			return;
		}
		if (isVisible.equals("Y") && (learningStartedAt == null || learningEndedAt == null)) {
			JSFunction.alertBack(response, "오늘의 학습 노출기간을 입력하세요.");
			return;
		}

		LearningDTO learningDTO = new LearningDTO();
		learningDTO.setMemberId(loginMemberId);
		learningDTO.setLearningTitle(learningTitle);
		learningDTO.setLearningContent(learningContent);
		learningDTO.setIsPublic(isPublic.equals("Y") ? true : false);
		learningDTO.setIsVisible(isVisible.equals("Y") ? true : false);
		learningDTO.setLearningStartedAt(isVisible.equals("N") || learningStartedAt == null ? null : LocalDate.parse(learningStartedAt));
		learningDTO.setLearningEndedAt(isVisible.equals("N") || learningEndedAt == null ? null : LocalDate.parse(learningEndedAt));
		learningDTO.setSharedList(sharedDTOList);
		learningDTO.setTopic(topics);
		learningDTO.setHashtag(hashtags);

		// 파일 처리
		CommonFileUtil fUtil = new CommonFileUtil();
		List<FileDTO> files = new ArrayList<>();

		// 1. 파일 업로드 디렉토리 설정
		// String saveDir = getServletContext().getRealPath("/Uploads");
		String saveDir = getServletContext().getInitParameter("SaveDirectory");
		String virtualDir = "/Uploads";

		// 2. 파일 업로드
		List<String> orgFiles = fUtil.multiFileUpload(request, saveDir);
		if (orgFiles != null && !orgFiles.isEmpty()) {
			for (String orgFile : orgFiles) {
				String newFile = fUtil.fileRename(saveDir, orgFile);
				FileDTO file = new FileDTO();
				file.setFileName(newFile);
				file.setFileExt(fUtil.getFileInfo("FILE_EXT", orgFile));
				file.setFilePath(virtualDir);
				file.setFileSize(fUtil.getFileSize(saveDir, newFile));
				files.add(file);
			}
		}
		learningDTO.setFiles(files);

		LearningDAO learningDAO = new LearningDAO();
		int result = learningDAO.createLearning(learningDTO);
		learningDAO.close();
		if (result > 0) {
			JSFunction.alertLocation(response, "replace", "학습 게시글 등록이 완료되었습니다.", url);
		} else {
			JSFunction.alertBack(response, "학습 게시글 등록이 완료되지 않았습니다.");
		}
	}
}
