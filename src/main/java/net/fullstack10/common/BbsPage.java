package net.fullstack10.common;

public class BbsPage {
	public static String pagingArea(int total_count, int page_no, int page_size, int page_block_size, String linkUrl) {

		// 링크 출력할 변수
		StringBuilder sb = new StringBuilder();

		// 입력받은 URL 정보
		String tmpLinkURL = (linkUrl!=null && !linkUrl.isEmpty() ? linkUrl : "");
		String fullLink = "";
		String pageLink = "";

		int total_page = (int)Math.ceil(total_count / (double)page_size);
		total_page = (total_page > 1 ? total_page : 1);
		int page_block_start = (int)Math.floor((page_no-1) / (double)page_size) * page_size +1;
		int page_block_end = (int)Math.ceil(page_no / (double)page_size) * page_size;
		page_block_end = (page_block_end > total_page ? total_page : page_block_end);

		// sb.append((page_no > 1 ? "<a href='" + tmpLinkURL + (tmpLinkURL.equals("?")? "page_no=1" : "&page_no=1") + "'><strong><<</strong></a>&nbsp;&nbsp;" : "<<&nbsp;&nbsp;"));
		if (page_no > 1) {
			pageLink = "";
			pageLink += tmpLinkURL;
			pageLink += tmpLinkURL.isEmpty() ? "?" : "&";
			pageLink += "page_no=1";
			// <div class='page-btn'>
			fullLink += "<div class='page-nav'><a href='"+pageLink + "'><strong>&lt;&lt;</strong></a></div>&nbsp;&nbsp;";
		} else {
			fullLink = "<div class='page-nav'>&lt;&lt;</div>&nbsp;&nbsp;";
		}
		sb.append(fullLink);
		//sb.append((page_block_start>1? "<a href='"+tmpLinkURL+ (tmpLinkURL.equals("?") ? "page_no="+(page_block_start-1) : "&page_no="+(page_block_start-1)) + "'><strong><</strong></a>&nbsp;&nbsp;" : "<&nbsp;&nbsp;"));
		if (page_block_start > 1) {
			pageLink = "";
			pageLink += tmpLinkURL;
			pageLink += tmpLinkURL.isEmpty() ? "?" : "&";
			pageLink += "page_no="+(page_block_start - 1);
			pageLink = tmpLinkURL + pageLink;
			fullLink = "<div class='page-nav'><a href='" + pageLink + "'><strong>&lt;</strong></a></div>&nbsp;&nbsp;";
		} else {
			fullLink = "<div class='page-nav'>&lt;</div>&nbsp;&nbsp;";
		}
		sb.append(fullLink);

		for (int i=page_block_start; i<=page_block_end; i++) {
			if ( page_no == i ){
				sb.append("<div class='page-btn'><strong>"+i+"</strong></div>");
			} else {
				pageLink = "";
				pageLink += tmpLinkURL;
				pageLink += tmpLinkURL.isEmpty() ? "?":"&";
				pageLink += "page_no="+i;
				fullLink =
						"<div class='page-btn'>"
						+ "<a href='"
						+ pageLink
						+ "'>" + i + "</a></div>";
				sb.append("<a href='"+tmpLinkURL + (tmpLinkURL.equals("?") ? "page_no="+i: "&page_no="+i)+"'>"+i+"</a>");
				sb.append(fullLink);
			}
			if (i != page_block_end) {
				sb.append("&nbsp;|&nbsp;");
			}
		}

		if(total_page > page_block_end) {
			pageLink = "";
			pageLink += tmpLinkURL;
			pageLink += tmpLinkURL.isEmpty() ? "?": "&";
			pageLink += "page_no=" + (page_block_end + 1);
			fullLink = "&nbsp;&nbsp;<div class='page-nav'><a href='" + pageLink + "'>&nbsp;<strong>&gt;</strong></a></div>";

		} else {
			fullLink = "&nbsp;&nbsp;<div class='page-nav'>&gt;</div>";
		}
		sb.append(fullLink);

		if(page_no != total_page) {
			pageLink = "";
			pageLink += tmpLinkURL;
			pageLink += tmpLinkURL.isEmpty() ? "?":"&";
			pageLink += "page_no=" + total_page;
			fullLink = "&nbsp;&nbsp;<a href='" + pageLink + "'>&nbsp;<strong>&gt;&gt;</strong></a>";
		}else {
			fullLink = "&nbsp;&nbsp;<div class='page-nav'>&gt;&gt;</div>";
		}
		sb.append(fullLink);
		sb.append((total_page>page_block_end? "&nbsp;&nbsp;<a href='"+tmpLinkURL + (tmpLinkURL.equals("?")? "page_no="+(page_block_end+1) : "&page_no="+(page_block_end+1))+"'><strong>></strong></a>" : "&nbsp;&nbsp;>"));
		sb.append((total_page>page_block_end? "&nbsp;&nbsp;<a href='"+tmpLinkURL+ (tmpLinkURL.equals("?") ? "page_no="+(total_page) : "&page_no="+(total_page))+"'><strong>>></strong></a>" : "&nbsp;&nbsp;>>"));

		return sb.toString();
	}
}