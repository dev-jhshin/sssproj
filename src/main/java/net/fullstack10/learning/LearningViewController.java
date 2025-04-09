package net.fullstack10.learning;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class LearningViewController
 */
@WebServlet("/learning/view.do")
public class LearningViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String loginMemberId = (String)session.getAttribute("memberId");
		String idx = request.getParameter("idx");
		
		LearningDAO learningDAO = new LearningDAO();
		LearningDTO learningDTO = learningDAO.getLearningByIdx(idx);
		
		LearningSharedDAO sharedDAO = new LearningSharedDAO();
		learningDTO.setSharedList(sharedDAO.getLearningShareList(idx));
		sharedDAO.close();
		
		// 비공개 게시글에 대한 예외 처리 (단, 공유받은회원,작성자 접근 가능)
		if (!learningDTO.getIsPublic()) {
			boolean isSharedMember = learningDTO.getSharedList().stream()
					.anyMatch(shared -> shared.getSharedTo().equals(loginMemberId));
			if (!isSharedMember && !learningDTO.getMemberId().equals(loginMemberId)) {
				JSFunction.alertBack(response, "권한이 없습니다.");
				return;
			}
		}
		
		learningDAO.updateViewCnt(idx);
		learningDAO.close();
		
		learningDTO.setLearningContent(learningDTO.getLearningContent().replace("\n", "<br>"));
		String[] topics = (learningDTO.getTopic() != null && !learningDTO.getTopic().isEmpty() ? 
				learningDTO.getTopic().split(",") : new String[0]);
		String[] hashtags = (learningDTO.getHashtag() != null && !learningDTO.getHashtag().isEmpty() ? 
				learningDTO.getHashtag().split(",") : new String[0]);
		
		LearningFileDAO fileDAO = new LearningFileDAO();
		learningDTO.setFiles(fileDAO.getFileListByLearningIdx(idx));
		fileDAO.close();
		
		LearningCommentDAO commentDAO = new LearningCommentDAO();
		learningDTO.setComments(commentDAO.getLearningCommentListByLearningIdx(idx));
		commentDAO.close();
		
		LearningLikeDAO likeDAO = new LearningLikeDAO();
		learningDTO.setIsLiked(likeDAO.isAlreadyLiked(idx, loginMemberId));
		likeDAO.close();
		
		request.setAttribute("topics", topics);
		request.setAttribute("hashtags", hashtags);
		request.setAttribute("dto", learningDTO);
		request.getRequestDispatcher("/WEB-INF/views/learning/msInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
