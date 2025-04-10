package net.fullstack10.support;

public class SupportPage {
	public static String pagingArea(int total_count, int page_no, int page_size, int page_block_size, String linkUrl) {
		if (total_count < 1) { return ""; }
		StringBuilder sb = new StringBuilder();

		String tmpLinkURL = (linkUrl != null && !linkUrl.isEmpty() ? linkUrl : "");
		String pageLink = "";
		String fullLink = "";

		int total_page = (int) Math.ceil(total_count / (double) page_size);
		total_page = (total_page > 1 ? total_page : 1);
		int page_block_start = (int) Math.floor((page_no - 1) / (double) page_size) * page_size + 1;
		int page_block_end = (int) Math.ceil(page_no / (double) page_size) * page_size;
		page_block_end = (page_block_end > total_page ? total_page : page_block_end);
		System.out.println(total_page);
		System.out.println(page_block_start);
		System.out.println(page_block_end);
		System.out.println();
		System.out.println();
		if (page_no > 1) {
			pageLink = "";
			pageLink += (tmpLinkURL.isEmpty() ? "?" : "&");
			pageLink += "page_no=1";
			pageLink = tmpLinkURL + pageLink;

			fullLink = "<a class='bt first' href='" + pageLink + "'><strong><<</strong></a>&nbsp;&nbsp;";
		} else {
			fullLink = "<<&nbsp;&nbsp;";
		}
		sb.append(fullLink);

		if (page_block_start > 1) {
			pageLink = "";
			pageLink += (tmpLinkURL.isEmpty() ? "?" : "&");
			pageLink += "page_no=" + (page_block_start - 1);
			pageLink = tmpLinkURL + pageLink;

			fullLink = "<a class='bt prev' href='" + pageLink + "'><strong><</strong></a>&nbsp;&nbsp;";
		} else {
			fullLink = "<&nbsp;&nbsp;";
		}
		sb.append(fullLink);

		for (int i = page_block_start; i <= page_block_end; i++) {
			if (page_no == i) {
				sb.append("<a href='#' class='num on'><strong>" + i + "</strong></a>");
			} else {
				pageLink = "";
				pageLink += (tmpLinkURL.isEmpty() ? "?" : "&");
				pageLink += "page_no=" + i;
				pageLink = tmpLinkURL + pageLink;
				fullLink = "<a class='num' href='" + pageLink + "'>" + i + "</a>";

				sb.append(fullLink);
			}
			if (i != page_block_end) {
				sb.append("&nbsp;|&nbsp;");
			}
		}

		if (total_page > page_block_end) {
			pageLink = "";
			pageLink += (tmpLinkURL.isEmpty() ? "?" : "&");
			pageLink += "page_no=" + (page_block_end + 1);
			pageLink = tmpLinkURL + pageLink;

			fullLink = "&nbsp;&nbsp;<a class='bt next' href='" + pageLink + "'><strong>></strong></a>";
		} else {
			fullLink = "&nbsp;&nbsp;>";
		}
		sb.append(fullLink);

		if (total_page > page_block_end) {
			pageLink = "";
			pageLink += (tmpLinkURL.isEmpty() ? "?" : "&");
			pageLink += "page_no=" + total_page;
			pageLink = tmpLinkURL + pageLink;

			fullLink = "&nbsp;&nbsp;<a class='bt last' href='" + pageLink + "'><strong>>></strong></a>&nbsp;&nbsp;";
		} else {
			fullLink = "&nbsp;&nbsp;<a class='bt last' href=''><strong>>></strong></a>&nbsp;&nbsp;";
		}
		sb.append(fullLink);

		return sb.toString();
	}
}
