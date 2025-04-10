package net.fullstack10.learning;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
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
import net.fullstack10.validation.ValidationUtil;

/**
 * Servlet implementation class LearningMyListController
 */
@WebServlet("/learning/my_list.do")
public class LearningMyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LearningDAO learningDAO;
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
		
		if(!ValidationUtil.isLoggedIn(loginMemberId, response)) return;
		
		learningDAO = new LearningDAO();
		
		Map<String, String> map = new HashMap<>();
		
		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "10");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "10");
		String pageSkipCount = String.valueOf((cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize));
		
		String startDate = cUtil.setSearchParam(request.getParameter("startDate"));
	    String endDate = cUtil.setSearchParam(request.getParameter("endDate"));
	    String searchCategory = cUtil.setSearchParam(request.getParameter("searchCategory"));
	    String searchValue = URLDecoder.decode(cUtil.setSearchParam(request.getParameter("searchValue")), "UTF-8");
	    
	    String orderColumn = cUtil.setSearchParam(request.getParameter("orderColumn"));
	    orderColumn = (!orderColumn.isEmpty() ? orderColumn : "createdAt");
	    String orderDirection = cUtil.setSearchParam(request.getParameter("orderDirection"));
	    orderDirection = (!orderDirection.isEmpty() ? orderDirection : "DESC");
	    
	    String queryString = "page_size=" + pageSize + "&page_block_size=" + pageBlockSize;
	    queryString += (!startDate.isEmpty() && !endDate.isEmpty() ? "&start_date=" + startDate + "&end_date=" + endDate : "" );
	    queryString += (!searchCategory.isEmpty() && !searchValue.isEmpty() ? "&search_category=" + searchCategory + "&search_value=" + searchValue : "");
	    queryString += "&order_column=" + orderColumn + "&order_direction=" + orderDirection;
	
	    map.put("startDate", startDate);
	    map.put("endDate", endDate);
	    map.put("searchCategory", searchCategory);
	    map.put("searchValue", searchValue);
	    map.put("orderColumn", orderColumn);
	    map.put("orderDirection", orderDirection);
	    map.put("pageSkipCount", pageSkipCount);
	    map.put("pageSize", pageSize);
	    
	    request.setAttribute("map", map);
	    request.setAttribute("learningList", learningDAO.getLearningListByMemberId(loginMemberId, map));
	    request.setAttribute("paging", pUtil.pagingArea(learningDAO.getLearningListSizeByMemberId(loginMemberId, map), cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "my_list.do?" + queryString));
	   
	    learningDAO.close();
		
		request.getRequestDispatcher("/WEB-INF/views/learning/msHome.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
