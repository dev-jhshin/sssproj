package net.fullstack10.user;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.auth.AuthDTO;

/**
 * Servlet implementation class UserListController
 */
@WebServlet("/user/list.do")
public class UserListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        
        HttpSession session = request.getSession();
        // String memberId = session.getAttribute("memberId").toString();
     	String memberId = "test1234";
        
        String keyword = URLDecoder.decode(request.getParameter("keyword"), "UTF-8");
		
		UserDAO userDAO = new UserDAO();
		List<AuthDTO> users = userDAO.getOtherUserList(memberId, keyword);
		
		// Jackson 으로 JSON 변환
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // LocalDate, LocalDateTime 처리
		String json = mapper.writeValueAsString(users);

		response.getWriter().write(json);
	}

}
