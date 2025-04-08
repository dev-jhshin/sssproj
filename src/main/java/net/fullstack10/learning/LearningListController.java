package net.fullstack10.learning;

import java.io.IOException;
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

/**
 * Servlet implementation class LearningListController
 */
@WebServlet("/learning/list.do")
public class LearningListController extends HttpServlet {
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
		
		learningDAO = new LearningDAO();
		
		Map<String, String> map = new HashMap<>();
		
		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "10");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "10");
		String pageSkipCount = String.valueOf((cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize));
		
		String startDate = cUtil.setSearchParam(request.getParameter("startDate"));
	    String endDate = cUtil.setSearchParam(request.getParameter("endDate"));
	    String searchCategory = cUtil.setSearchParam(request.getParameter("searchCategory"));
	    String searchValue = cUtil.setSearchParam(request.getParameter("searchValue"));
	    String orderColumn = cUtil.setSearchParam(request.getParameter("orderColumn"));
	    String orderDirection = cUtil.setSearchParam(request.getParameter("orderDirection"));
	      
	    String queryString = "page_size=" + pageSize + "&page_block_size=" + pageBlockSize;
	    queryString += (!startDate.isEmpty() && !endDate.isEmpty() ? "&start_date=" + startDate + "&end_date=" + endDate : "" );
	    queryString += (!searchCategory.isEmpty() && !searchValue.isEmpty() ? "&search_category=" + searchCategory + "&search_value=" + searchValue : "");
	    queryString += (!orderColumn.isEmpty() && !orderDirection.isEmpty() ? "&order_column=" + orderColumn + "&order_direction=" + orderDirection : "&order_column=createdAt&order_direction=DESC" );
	
	    map.put("startDate", startDate);
	    map.put("endDate", endDate);
	    map.put("searchCategory", searchCategory);
	    map.put("searchValue", searchValue);
	    map.put("orderColumn", orderColumn);
	    map.put("orderDirection", orderDirection);
	    map.put("pageSkipCount", pageSkipCount);
	    map.put("pageSize", pageSize);
	    
	    request.setAttribute("map", map);
	    request.setAttribute("learningList", learningDAO.getLearningList(map));
	    request.setAttribute("paging", pUtil.pagingArea(learningDAO.getLearningListSize(map), cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "list.do?" + queryString));
	   
	    learningDAO.close();
	    
	    request.getRequestDispatcher("/WEB-INF/views/learning/everyStudy.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
