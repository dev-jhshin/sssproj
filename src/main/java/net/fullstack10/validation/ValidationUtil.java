package net.fullstack10.validation;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

public class ValidationUtil {
	/* 로그인 여부 */
	public static boolean isLoggedIn(String memberId, HttpServletResponse response) throws IOException {
        if (memberId == null || memberId.isBlank()) {
            JSFunction.alertLocation(response, "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do");
            return false;
        }
        return true;
    }
	
	/* 권한 검사 */
	public static boolean hasPermission(String loginMemberId, String ownerMemberId, HttpServletResponse response) throws IOException {
        if (loginMemberId == null || ownerMemberId == null || !loginMemberId.equalsIgnoreCase(ownerMemberId)) {
            JSFunction.alertBack(response, "권한이 없습니다.");
            return false;
        }
        return true;
    }
	
	/* 게시글 idx 유효성 검사 */
	public static boolean isValidIdx(String idx, HttpServletResponse response) throws IOException {
        if (idx == null || idx.isBlank()) {
            JSFunction.alertBack(response, "게시글 정보가 올바르지 않습니다.");
            return false;
        }
        return true;
    }
	
	/* 게시글 작성자 검사 */
	public static boolean hasValidMemberId(String memberId, HttpServletResponse response) throws IOException {
        if (memberId == null || memberId.isBlank()) {
            JSFunction.alertBack(response, "사용자 정보가 없습니다.");
            return false;
        }
        return true;
    }
}
