package net.fullstack10.validation;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

public class LearningValidationUtil {
	/* 제목 유효성 */
	public static boolean isValidTitle(String title, HttpServletResponse response) throws IOException {
        if (title == null || title.length() < 1 || title.length() > 100) {
            JSFunction.alertBack(response, "제목을 1자 이상 100자 이하로 입력하세요.");
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
	
	/* 공개 여부가 Y일 경우 오늘의 학습 기간 필수 */
	public static boolean isValidVisibilityPeriod(String isVisible, String startedAt, String endedAt, HttpServletResponse response) throws IOException {
        if (startedAt == null || endedAt == null || startedAt.isBlank() || endedAt.isBlank()) {
            JSFunction.alertBack(response, "오늘의 학습 노출기간을 입력하세요.");
            return false;
        }
        return true;
    }
	
	/* 오늘의 학습 기간 시작일과 종료일 유효성 */
	public static boolean isValidDateOrder(String startedAt, String endedAt, HttpServletResponse response) throws IOException {
		try {
			LocalDate start = LocalDate.parse(startedAt);
            LocalDate end = LocalDate.parse(endedAt);

            if (start.isAfter(end)) {
                JSFunction.alertBack(response, "시작일은 종료일보다 이후일 수 없습니다.");
                return false;
            }
		} catch (Exception e) {
			e.printStackTrace();
			JSFunction.alertBack(response, "");
			return false;
		}
		
		return true;
	}
}
