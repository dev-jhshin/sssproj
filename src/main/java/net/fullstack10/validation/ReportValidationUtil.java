package net.fullstack10.validation;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

public class ReportValidationUtil {
	/* 내용 유효성 */
	public static boolean isValidContent(String content, HttpServletResponse response) throws IOException {
        if (content == null || content.trim().isEmpty()) {
            JSFunction.alertBack(response, "내용을 입력하세요.");
            return false;
        }
        return true;
    }
}
