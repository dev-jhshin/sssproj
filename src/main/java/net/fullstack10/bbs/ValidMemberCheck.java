package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;

public class ValidMemberCheck {
	private CommonUtil cUtil = new CommonUtil();
	public boolean isValidMember (HttpServletRequest request, HttpServletResponse response, String memberId, String memberStatus) {
		int status = cUtil.parseInt(memberStatus);
		if (status == 1) {
			// 사용 가능 상태
			return true;
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter wrt = response.getWriter();
			if (status == 2) {
				// 사용 정지 상태 
				wrt.println("<script>");
				wrt.println("alert('활동 정지 상태입니다. \n관리자에게 문의하세요.');");
				wrt.println("window.location.href = '/sssproj/'");
				wrt.println("</script>");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
}
