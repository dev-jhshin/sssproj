package net.fullstack10.manager;

public class managerPage {
	public static String managerPagingArea(
		int total_count, int page_no
		, int page_size, int page_block_size
		, String linkUrl) {
	// 링크 출력할 변수
	StringBuilder sb = new StringBuilder();
	
	// 입력 받은 URL 정보
	String tmpLinkURL = (linkUrl!=null&&!linkUrl.isEmpty()?linkUrl:"");
	String pageLink = "";
	String fullLink = "";
	
	int total_page = (int)Math.ceil(total_count/(double)page_size);
	total_page = (total_page > 1 ? total_page : 1);
	int page_block_start = (int)Math.floor((page_no-1)/(double)page_size)
								*page_size +1;
	int page_block_end = (int)Math.ceil(page_no/(double)page_size)*page_size;
	page_block_end = (page_block_end>total_page?total_page:page_block_end);
	
	// 첫 페이지 링크 출력 부분
	if ( page_no>1 ) {
		// page_no 변수 앞에 & 처리 설정
		pageLink = "";
		pageLink += (tmpLinkURL.isEmpty() ?  "?" : "&");
		pageLink += "page_no=1";
		pageLink = tmpLinkURL + pageLink;
		
		fullLink = "<a href='"+ pageLink +"'><strong><<</strong></a>&nbsp;&nbsp;";
	} else {
		fullLink = "<<&nbsp;&nbsp;";
	}
sb.append(fullLink);

// 이전 10페이지 링크 출력 부분
if ( page_block_start>1 ) {
	// page_no 변수 앞에 & 처리 설정
	pageLink = "";
	pageLink += (tmpLinkURL.isEmpty() ?  "?" : "&");
	pageLink += "page_no="+(page_block_start-1);
	pageLink = tmpLinkURL + pageLink;
	
	fullLink = "<a href='"+ pageLink +"'><strong><</strong></a>&nbsp;&nbsp;";
} else {
	fullLink = "<&nbsp;&nbsp;";
}
sb.append(fullLink);

// 페이징 링크 출력 부분
for (int i=page_block_start; i<=page_block_end; i++) {
	if ( page_no == i ){
		sb.append("<strong>"+i+"</strong>");
	} else {
		pageLink = "";
		pageLink += (tmpLinkURL.isEmpty() ?  "?" : "&");
		pageLink += "page_no="+i;
		pageLink = tmpLinkURL + pageLink;
		fullLink = "<a href='"+ pageLink +"'>"+ i +"</a>";
		
		sb.append(fullLink);
	}
	if (i!=page_block_end) {
		sb.append("&nbsp;|&nbsp;");
	}
}

// 다음 10개 링크 출력 부분
if ( total_page > page_block_end ) {
	// page_no 변수 앞에 & 처리 설정
	pageLink = "";
	pageLink += (tmpLinkURL.isEmpty() ?  "?" : "&");
	pageLink += "page_no="+(page_block_end+1);
	pageLink = tmpLinkURL + pageLink;
	
	fullLink = "&nbsp;&nbsp;<a href='"+ pageLink +"'><strong>></strong></a>";
} else {
	fullLink = "&nbsp;&nbsp;>";
}
sb.append(fullLink);

// 마지막 페이지 링크 출력 부분
if ( total_page > page_block_end ) {
	// page_no 변수 앞에 & 처리 설정
	pageLink = "";
	pageLink += (tmpLinkURL.isEmpty() ?  "?" : "&");
	pageLink += "page_no="+ total_page;
	pageLink = tmpLinkURL + pageLink;
	
	fullLink = "&nbsp;&nbsp;<a href='"+ pageLink +"'><strong>>></strong></a>&nbsp;&nbsp;";
} else {
	fullLink = "&nbsp;&nbsp;>>";
}
sb.append(fullLink);

return sb.toString();
}
}
