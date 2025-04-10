package net.fullstack10.manager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;

/**
 * Servlet implementation class ManagerInquiryList
 */
@WebServlet("/manager/inquiryList.do")
public class ManagerInquiryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommonUtil cUtil = new CommonUtil();
		ManagerDAO dao = new ManagerDAO();

		int total_count = 0;
		int total_page = 1;
		String page_no = (request.getParameter("page_no") != null ?
				request.getParameter("page_no").trim() : "1");
		String page_size = (request.getParameter("page_size") != null ?
				request.getParameter("page_size").trim() : "10");
		String page_block_size =
				(request.getParameter("page_block_size") != null ?
						request.getParameter("page_block_size").trim() : "10");
		int page_skip_count = 10;

		String search_category =
				(request.getParameter("search_category") != null ?
						request.getParameter("search_category").trim() : "");
		String search_word =
				(request.getParameter("search_word") != null ?
						request.getParameter("search_word").trim() : "");


		page_no = (cUtil.isNumberic(page_no) ? page_no : "1");
		page_no = (cUtil.parseInt(page_no) > 0 ? page_no : "1");
		page_skip_count =
				(cUtil.parseInt(page_no)-1)*cUtil.parseInt(page_size);

		String queryString = "page_size="+ page_size;
		queryString += "&page_block_size="+ page_block_size;
		queryString += "&search_category="+ search_category;
		queryString += "&search_word="+ search_word;

		Map<String, Object> mMap = new HashMap<>();
		mMap.put("page_no", page_no);
		mMap.put("page_size", page_size);
		mMap.put("page_block_size", page_block_size);
		mMap.put("page_skip_count", page_skip_count);

		if (search_category != null && !search_category.isEmpty() &&
			    search_word != null && !search_word.isEmpty()) {
			    mMap.put("search_category", search_category);
			    mMap.put("search_word", search_word);
			}

		System.out.println("page_no : "+ page_no);
		System.out.println("page_size : "+ page_size);
		System.out.println("page_block_size : "+ page_block_size);
		System.out.println("page_skip_count : "+ page_skip_count);
		System.out.println("search_category : "+ search_category);
		System.out.println("search_word : "+ search_word);

		total_count = dao.getInquiryTotalCount(mMap);
		List<ManagerDTO> reportList = dao.getInquiryList(mMap);
		dao.close();
		System.out.println("total_count : "+ total_count);

		mMap.put("totalInquiry", total_count);
		mMap.put("inquiryList", reportList);
		mMap.put("linkParams", URLEncoder.encode(queryString+"&page_no="+page_no, "UTF-8"));

		request.setAttribute("map", mMap);

		mMap.put("paging",
				managerPage.managerPagingArea(total_count,
					cUtil.parseInt(page_no),
					cUtil.parseInt(page_size),
					cUtil.parseInt(page_block_size),
					"./inquiryList.do?"+queryString)
		);
		request.getRequestDispatcher("/WEB-INF/views/manager/admin_question_list_page.jsp").forward(request, response);
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
