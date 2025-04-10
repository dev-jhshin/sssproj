package net.fullstack10.bbs;

import java.io.IOException;
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
 * Servlet implementation class BbsListController
 */
@WebServlet("/bbs/list.do")
public class BbsListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BbsDAO dao;
	private CommonUtil cUtil = new CommonUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		dao = new BbsDAO();
		Map<String, Object> pMap = new HashMap<>();
		String category = request.getParameter("category");

		// 페이징 변수
		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "10");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "10");
		int pageSkipCount = (cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize);

		// 검색 변수
		String searchOrder = cUtil.setSearchParam(request.getParameter("searchOrder"));
		String searchCategory = cUtil.setSearchParam(request.getParameter("search_category"));
		String searchWord = cUtil.setSearchParam(request.getParameter("search_word"));
		String searchStart = cUtil.setSearchParam(request.getParameter("search_start"));
		String searchEnd = cUtil.setSearchParam(request.getParameter("search_end"));
		String queryString = "page_size=" + pageSize+"&page_block_size="+pageBlockSize;
		queryString += "&search_category=" + (searchCategory != null && !searchCategory.isEmpty() ? searchCategory : "");
		queryString += "&search_word=" + (searchWord != null && !searchWord.isEmpty() ? searchWord : "");
		queryString += "&category=" + (category!=null && !category.isEmpty() ? category: "");
		
		pMap.put("pageSkipCount", pageSkipCount);
		pMap.put("pageSize", pageSize);
		pMap.put("searchOrder", searchOrder);
		pMap.put("searchCategory", searchCategory);
		pMap.put("searchWord", searchWord);
		pMap.put("category", category);
		pMap.put("searchStart", searchStart);
		pMap.put("searchEnd", searchEnd);

		int totalCount = dao.getBbsSize(pMap);
		pMap.put("paging", CommonPageUtil.pagingArea(totalCount, cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "list.do?" + queryString));
		pMap.put("user", session.getAttribute("memberId"));
		pMap.put("totalCount", totalCount);
		pMap.put("bbsList", dao.getBbsList(pMap));

		List<String> categories = dao.getBbsCategory();
		pMap.put("categories", categories);

		request.setAttribute("pMap", pMap);
		dao.close();

		request.getRequestDispatcher("/WEB-INF/views/bbs2/cmHome.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
