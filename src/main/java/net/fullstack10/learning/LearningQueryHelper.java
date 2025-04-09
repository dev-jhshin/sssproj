package net.fullstack10.learning;

import java.util.List;

public class LearningQueryHelper {
	// 등록일
	public static void addDateFilter(StringBuilder sql, List<String> params, String startDate, String endDate) {
		if (startDate != null && !startDate.isBlank() && endDate != null && !endDate.isBlank()) {
			sql.append(" AND tl.createdAt BETWEEN ? AND ? ");
			params.add(startDate);
			params.add(endDate);
		}
	}
	
	// 검색 카테고리
	public static void addCategoryFilter(StringBuilder sql, List<String> params, String category, String value) {
		if (category != null && !category.isBlank() && value != null && !value.isBlank()) {
			switch (category) {
				case "TITLE":
					sql.append(" AND tl.learningTitle LIKE ? ");
	                params.add("%" + value + "%");
	                break;
				case "CONTENT":
	                sql.append(" AND tl.learningContent LIKE ? ");
	                params.add("%" + value + "%");
	                break;
	            case "TOPIC":
	                sql.append(" AND FIND_IN_SET(?, tl.topic) ");
	                params.add(value);
	                break;
	            case "HASHTAG":
	                sql.append(" AND FIND_IN_SET(?, tl.hashtag) ");
	                params.add(value);
	                break;
			}
		}
	}
	
	// 정렬 조건 (화이트리스트 방식)
	public static void addOrderBy(StringBuilder sql, String column, String direction) {
		List<String> allowedColumns = List.of("createdAt", "likeCnt", "viewCnt");
		List<String> allowedDirections = List.of("ASC", "DESC");
		
		if (column != null && direction != null && allowedColumns.contains(column) && allowedDirections.contains(direction)) {
			sql.append(" ORDER BY " + column + " " + direction + " , idx DESC ");
		} else {
			sql.append(" ORDER BY createdAt DESC ");
		}
	}
	
	// 페이징
	public static void addPagination(StringBuilder sql, String pageSkipCount, String pageSize) {
		if (pageSkipCount != null && !pageSkipCount.isBlank() && pageSize != null && !pageSize.isBlank()) {
			sql.append(" LIMIT " + pageSkipCount + ", " + pageSize + " ");
		}
	}
}
