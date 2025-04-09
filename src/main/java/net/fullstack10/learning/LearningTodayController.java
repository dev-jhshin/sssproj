package net.fullstack10.learning;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonPageUtil;
import net.fullstack10.common.CommonUtil;

/**
 * Servlet implementation class LearningToadyController
 */
@WebServlet("/learning/today.do")
public class LearningTodayController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommonUtil cUtil = new CommonUtil();
	private CommonPageUtil pUtil = new CommonPageUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL = request.getRequestURL().toString();

		HttpSession session = request.getSession();
		session.setAttribute("redirectURL", requestURL);

		Map<String, String> map = new HashMap<>();

		String loginMemberId = (String)session.getAttribute("memberId");

		String dateStr = request.getParameter("date");
		LocalDate date;
		if (dateStr != null && !dateStr.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
			date = LocalDate.parse(dateStr, formatter);
		} else {
			date = LocalDate.now();
		}

		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "1");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "5");
		String pageSkipCount = String.valueOf((cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize));

		String queryString = "date=" + date.toString();

		map.put("pageSkipCount", pageSkipCount);
	    map.put("pageSize", pageSize);

	    LearningDAO learningDAO = new LearningDAO();
		LearningFileDAO fileDAO = new LearningFileDAO();
		LearningSharedDAO sharedDAO = new LearningSharedDAO();
		LearningLikeDAO likeDAO = new LearningLikeDAO();
		
		// 나의 학습
		List<LearningDTO> learningList = learningDAO.getTodayLearningList(loginMemberId, date, map);
		for (LearningDTO learningDTO : learningList) {
			String myIdx = String.valueOf(learningDTO.getIdx());
			learningDTO.setSharedList(sharedDAO.getLearningShareList(myIdx, 3));
			learningDTO.setFiles(fileDAO.getFileListByLearningIdx(myIdx));
		}

		// 공유 학습
		List<LearningDTO> sharedList = learningDAO.getTodaySharedList(loginMemberId, date);
		for (LearningDTO learningDTO : sharedList) {
			String sharedIdx = String.valueOf(learningDTO.getIdx());
			learningDTO.setFiles(fileDAO.getFileListByLearningIdx(sharedIdx));
			learningDTO.setIsLiked(likeDAO.isAlreadyLiked(sharedIdx, loginMemberId));
		}

	    request.setAttribute("learningList", learningList);
	    request.setAttribute("sharedList", sharedList);
	    request.setAttribute("paging", CommonPageUtil.pagingArea(learningDAO.getTodayLearningListSize(loginMemberId, date), cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "today.do?" + queryString));

	    learningDAO.close();
		sharedDAO.close();
		fileDAO.close();
		likeDAO.close();
	    
		request.getRequestDispatcher("/WEB-INF/views/learning/todayStudy.jsp").forward(request, response);
	}

}
