package net.fullstack10.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CommonDateUtil {
	public CommonDateUtil() {}

	// LocalDate타입 --> Date타입으로 변환
	public Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	// LocalDateTime --> DateTime 으로 변환
	public Date toDateTime(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	// Date 타입 --> LocalDate 로 변환
	public LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	// Date 타입 --> LocalDateTime 으로 변환
	public LocalDateTime toLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public String toString(LocalDateTime ldt) {
		if(ldt==null) {
			return "";
		}
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

		// 년도와 일이 같으면,
		if (ldt.getDayOfYear() == now.getDayOfYear() && ldt.getYear() == now.getYear()) {
			// 시간이 같으면, 근데 1시간전이 1시간 전이 아님.
			int difHour = LocalDateTime.now().getHour() - ldt.getHour();
			if(difHour <= 1) {
				if (now.getMinute() - ldt.getMinute() < 60) {
					int difMin = (now.getMinute() - ldt.getMinute() + 60) % 60;
					return difMin +"분 전" ;
				} else {
					return "1시간 전";
				}
			} else {
				return now.getHour() - ldt.getHour() + "시간 전 ";
			}

		}
		return ldt.format(formatter);

	}

	// Date 타입 --> LocalDateTime 문자열로 변환
	public String dateTolocalDateTimeString(Date date) {
		return this.toLocalDateTime(date).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	// Date 타입 --> LocalDateTime 문자열로 변환
	public String localDateTimeToString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	// Date 타입 --> LocalDate 문자열로 변환
		public String localDateToString(LocalDate localDate) {
			return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}

	// String 타입 --> LocalDate 타입으로 변환
	public LocalDate toLocalDate(String string) {
		return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}
