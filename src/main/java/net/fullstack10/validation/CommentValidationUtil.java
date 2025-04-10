package net.fullstack10.validation;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

public class CommentValidationUtil {
	/* 게시글 idx 유효성 검사 */
	public static boolean isValidIdx(String idx, HttpServletResponse response) throws IOException {
        if (idx == null || idx.isBlank()) {
            JSFunction.alertBack(response, "댓글 정보가 올바르지 않습니다.");
            return false;
        }
        return true;
    }
	
	/* 내용 유효성 */
	public static boolean isValidContent(String content, HttpServletResponse response) throws IOException {
        if (content == null || content.trim().isEmpty()) {
            JSFunction.alertBack(response, "내용을 입력하세요.");
            return false;
        }
        return true;
    }
}
