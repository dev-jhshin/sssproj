package net.fullstack10.learning;

import java.io.IOException;
import java.net.URLDecoder;
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
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class LearningSharedListController
 */
@WebServlet("/learning/shared_list.do")
public class LearningSharedListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommonUtil cUtil = new CommonUtil();
	private CommonPageUtil pUtil = new CommonPageUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String requestURL = request.getRequestURL().toString();
		
		HttpSession session = request.getSession();
		session.setAttribute("redirectURL", requestURL);
		
		String loginMemberId = (String)session.getAttribute("memberId");
		if (loginMemberId == null || loginMemberId.isEmpty()) {
			JSFunction.alertBack(response, "사용자 정보가 없습니다.");
			return;
		}
		
		Map<String, String> map = new HashMap<>();
		
		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "10");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "10");
		String pageSkipCount = String.valueOf((cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize));
		
		String startDate = cUtil.setSearchParam(request.getParameter("startDate"));
	    String endDate = cUtil.setSearchParam(request.getParameter("endDate"));
	    String searchCategory = cUtil.setSearchParam(request.getParameter("searchCategory"));
	    String searchValue = URLDecoder.decode(cUtil.setSearchParam(request.getParameter("searchValue")), "UTF-8");
	    
	    String type = cUtil.setSearchParam(request.getParameter("type"));
	    
	    String queryString = "page_size=" + pageSize + "&page_block_size=" + pageBlockSize;
	    queryString += (!startDate.isEmpty() && !endDate.isEmpty() ? "&start_date=" + startDate + "&end_date=" + endDate : "" );
	    queryString += (!searchCategory.isEmpty() && !searchValue.isEmpty() ? "&search_category=" + searchCategory + "&search_value=" + searchValue : "");
	    queryString += "&type=" + type;
	    
	    map.put("startDate", startDate);
	    map.put("endDate", endDate);
	    map.put("searchCategory", searchCategory);
	    map.put("searchValue", searchValue);
	    map.put("pageSkipCount", pageSkipCount);
	    map.put("pageSize", pageSize);
	    
	    LearningDAO learningDAO = new LearningDAO();
	    LearningSharedDAO sharedDAO = new LearningSharedDAO();
	    
	    List<LearningDTO> learningList = new ArrayList<>();
	    if (type.equals("sharedFrom")) {
	    	learningList = learningDAO.getReceivedSharedList(loginMemberId, map);
	    	for (LearningDTO fromDTO : learningList) {
	    		fromDTO.setSharedList(sharedDAO.getLearningShareList(String.valueOf(fromDTO.getIdx()), 1));
	    	}
	    } else {
	    	learningList = learningDAO.getSentSharedList(loginMemberId, map);
	    	for (LearningDTO toDTO : learningList) {
	    		toDTO.setSharedList(sharedDAO.getLearningShareList(String.valueOf(toDTO.getIdx()), 3));
	    	}
	    }
	    
	    request.setAttribute("map", map);
	    request.setAttribute("learningList", learningList);
	    request.setAttribute("paging", pUtil.pagingArea(learningDAO.getReceivedSharedListSize(loginMemberId, map), cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "my_list.do?" + queryString));
	    
	    learningDAO.close();
	    sharedDAO.close();
	    
	    request.getRequestDispatcher("/WEB-INF/views/learning/studyShare.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
