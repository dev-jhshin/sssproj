package net.fullstack10.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonPageUtil;
import net.fullstack10.common.CommonUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class SupportQuestionListController
 */
@WebServlet("/support/question/list.do")
public class SupportQuestionListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SupportDAO supportDAO; 
    private CommonUtil cUtil = new CommonUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportQuestionListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wrt = response.getWriter();
		
		String memberId = (String) request.getSession().getAttribute("memberId");
		if(memberId ==null || memberId.length()<1) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		
		supportDAO = new SupportDAO();
		Map<String, Object> pMap = new HashMap<>();
		// 페이징 변수 
		String pageNo = cUtil.setPageParam(request.getParameter("page_no"), "1");
		String pageSize = cUtil.setPageParam(request.getParameter("page_size"), "10");
		String pageBlockSize = cUtil.setPageParam(request.getParameter("page_block_size"), "10");
		int pageSkipCount = (cUtil.parseInt(pageNo) - 1) * cUtil.parseInt(pageSize);
		// 검색 변수
		String searchCategory = cUtil.setSearchParam(request.getParameter("search_category"));
		String searchWord = cUtil.setSearchParam(request.getParameter("search_word"));
//		String searchStart = cUtil.setSearchParam(request.getParameter("search_start"));
//		String searchEnd = cUtil.setSearchParam(request.getParameter("search_end"));
		String queryString = "page_size=" + pageSize+"&page_block_size="+pageBlockSize;
		queryString += "&search_category=" + (searchCategory != null && !searchCategory.isEmpty() ? searchCategory : "");
		queryString += "&search_word=" + (searchWord != null && !searchWord.isEmpty() ? searchWord : "");
		
		pMap.put("pageSkipCount", pageSkipCount);
		pMap.put("pageSize", pageSize);
		
		pMap.put("searchCategory", searchCategory);
		pMap.put("searchWord", searchWord);
//		pMap.put("searchStart", searchStart);
//		pMap.put("searchEnd", searchEnd);
		pMap.put("memberId", memberId);
		int totalCount = supportDAO.getInquirySize(pMap);
		pMap.put("totalCount", totalCount);
		pMap.put("paging", SupportPage.pagingArea(totalCount, cUtil.parseInt(pageNo), cUtil.parseInt(pageSize), cUtil.parseInt(pageBlockSize), "list.do?" + queryString));
		List<InquiryDTO> inquiries = supportDAO.getInquiryList(pMap);
		supportDAO.close();
		pMap.put("inquiries", inquiries);
		request.setAttribute("pMap", pMap);
		request.getRequestDispatcher("/WEB-INF/views/support/supMyQuestionList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
