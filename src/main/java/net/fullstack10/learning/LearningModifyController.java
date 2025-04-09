package net.fullstack10.learning;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import net.fullstack10.file.FileDTO;

/**
 * Servlet implementation class LearningModifyController
 */
@WebServlet("/learning/modify.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 10)
public class LearningModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginMemberId = (String)session.getAttribute("memberId");
		
		if (loginMemberId == null || loginMemberId.isEmpty()) {
			JSFunction.alertBack(response, "사용자 정보가 없습니다.");
			return;
		}
		
		String idx = request.getParameter("idx");
		
		LearningDAO learningDAO = new LearningDAO();
		LearningDTO learningDTO = learningDAO.getLearningByIdx(idx);
		learningDAO.close();
		
		if (!learningDTO.getMemberId().equalsIgnoreCase(loginMemberId)) {
			JSFunction.alertBack(response, "권한이 없습니다.");
			return;
		}
		
		// learningDTO.setLearningContent(learningDTO.getLearningContent().replace("\n", "<br>"));
		String[] topics = (learningDTO.getTopic() != null && !learningDTO.getTopic().isEmpty() ? 
				learningDTO.getTopic().split(",") : new String[0]);
		String[] hashtags = (learningDTO.getHashtag() != null && !learningDTO.getHashtag().isEmpty() ? 
				learningDTO.getHashtag().split(",") : new String[0]);
		
		LearningSharedDAO sharedDAO = new LearningSharedDAO();
		learningDTO.setSharedList(sharedDAO.getLearningShareList(idx));
		sharedDAO.close();
		
		LearningFileDAO fileDAO = new LearningFileDAO();
		learningDTO.setFiles(fileDAO.getFileListByLearningIdx(idx));
		fileDAO.close();
		
		request.setAttribute("topics", topics);
		request.setAttribute("hashtags", hashtags);
		request.setAttribute("dto", learningDTO);
		request.getRequestDispatcher("/WEB-INF/views/learning/msModify.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Object redirectURL = session.getAttribute("redirectURL");
		String url = "list.do";
		if (redirectURL != null) {
			url = redirectURL.toString();
			session.removeAttribute("redirectURL");
		}
		
		String loginMemberId = (String)session.getAttribute("memberId");
		String memberId = request.getParameter("memberId");
		
		String idx = request.getParameter("idx");
		
		String learningTitle = request.getParameter("learningTitle");
		String learningContent = request.getParameter("learningContent");
		String isVisible = request.getParameter("isVisible");
		String learningStartedAt = request.getParameter("learningStartedAt");
		String learningEndedAt = request.getParameter("learningEndedAt");
		String isPublic = request.getParameter("isPublic");
		String topics = request.getParameter("topics");
		String hashtags = request.getParameter("hashtags");
		String addShared = request.getParameter("addShared");
		String deleteShared = request.getParameter("deleteShared");
		
		// validation 체크 루틴
		if (cUtil.parseInt(idx) < 1) {
			JSFunction.alertBack(response, "게시글 정보가 올바르지 않습니다.");
			return;
		}
		if (!loginMemberId.equalsIgnoreCase(memberId)) {
			JSFunction.alertLocation(response, "href", "권한이 없습니다.", url);
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
		
		List<LearningSharedDTO> addList = getSharedDTOList(memberId, addShared);
		List<LearningSharedDTO> deleteList = getSharedDTOList(memberId, deleteShared);
		String[] deleteFileIdxList = request.getParameterValues("deleteFileIdx");
		
		// 학습 게시글 DTO 설정
		LearningDTO learningDTO = new LearningDTO();
		learningDTO.setLearningTitle(learningTitle);
		learningDTO.setLearningContent(learningContent);
		learningDTO.setIsPublic("Y".equals(isPublic));
		learningDTO.setIsVisible("Y".equals(isVisible));
		learningDTO.setLearningStartedAt(learningStartedAt != null ? LocalDate.parse(learningStartedAt) : null);
		learningDTO.setLearningEndedAt(learningEndedAt != null ? LocalDate.parse(learningEndedAt) : null);
		learningDTO.setSharedToAdd(addList);
		learningDTO.setSharedToRemove(deleteList);
		learningDTO.setTopic(topics);
		learningDTO.setHashtag(hashtags);
		
		// 파일 처리
		CommonFileUtil fUtil = new CommonFileUtil();
	
		// String saveDir = getServletContext().getRealPath("/Uploads");
		String saveDir = getServletContext().getInitParameter("SaveDirectory");
		String virtualDir = "/Uploads";
		
		// 1. 파일 업로드
		List<String> orgFiles = fUtil.multiFileUpload(request, saveDir);
		if (orgFiles != null && !orgFiles.isEmpty()) {
			List<FileDTO> addFiles = orgFiles.stream()
					.map(orgFile -> {
						String newFile = fUtil.fileRename(saveDir, orgFile);
						FileDTO file = new FileDTO();
						file.setFileName(newFile);
						file.setFileExt(fUtil.getFileInfo("FILE_EXT", orgFile));
						file.setFilePath(virtualDir);
						file.setFileSize(fUtil.getFileSize(saveDir, newFile));
						return file;
					})
					.collect(Collectors.toList());
			
		// 2. 학습 게시글 DTO 파일 추가
			learningDTO.setFilesToAdd(addFiles);
		}
		
		// 3. DB 삭제할 파일 설정
		if (deleteFileIdxList != null) {
			learningDTO.setFilesToRemove(getFileDTOList(request, deleteFileIdxList));
		}
 		
		// 4. 게시글 수정
		LearningService service = new LearningService();
		boolean result = service.updateLearning(idx, learningDTO);
		
		// 5. 파일 삭제
		if (deleteFileIdxList != null && result) {
			for (String deleteFileIdx : deleteFileIdxList) {
				String fileName = request.getParameter("fileName_" + deleteFileIdx);
				fUtil.fileDelete(request, saveDir, fileName);
			}
		}
		
		String msg = (result ? "게시글 수정에 성공했습니다." : "게시글 수정에 실패했습니다.");
		JSFunction.alertLocation(response, "href", msg, "/sssproj/learning/view.do?idx=" + idx);
	}
	
	private List<LearningSharedDTO> getSharedDTOList(String memberId, String param) {
		return (param != null && ! param.isEmpty()) 
			? Arrays.stream(param.split(","))
					.map(user -> {
						LearningSharedDTO dto = new LearningSharedDTO();
						dto.setSharedFrom(memberId);
						dto.setSharedTo(user);
						return dto;
					})
					.toList()
			: List.of();
	}
	
	private List<FileDTO> getFileDTOList(HttpServletRequest request, String[] param) {
		return (param != null && param.length != 0) 
			? Arrays.stream(param)
					.map(idx -> {
						String fileIdx = request.getParameter("fileIdx_" + idx);
						FileDTO dto = new FileDTO();
						dto.setIdx(cUtil.parseInt(fileIdx));
						return dto;
					})
					.collect(Collectors.toList())
			: List.of();
	}
}
