package com.attraction.schedule.tool;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.attraction.schedule.db.Lesson;

public class ParseUtil {
	
	/**
	 * 解析课表
	 * @param rawHtml 待处理的html
	 */
	public List<Lesson> parseLesson(String rawHtml) {
		Document doc = Jsoup.parse(rawHtml);
		Element table = doc.getElementById("Table1");
		if (table == null) {
			return null;
		}
		List<Lesson> lessons = new ArrayList<Lesson>();
		Elements trs = table.getElementsByTag("tr");
		trs.remove(0);
		trs.remove(0);
		for (int row = 0; row < trs.size(); row++) {
			Elements tds = trs.get(row).select("td");
			int colSize = tds.size();
			for (int col = 0; col < colSize; col++) {
				Element td = tds.get(col);
				String content = td.html();
				if (td.hasAttr("rowspan") && content.contains("<br>")) {
					int rowSpan = Integer.parseInt(td.attr("rowspan"));
					if (content.contains("<br><br>")
							|| content.contains("<br><br><br>")) {
						String[] strs = content.split("<br><br><br>|<br><br>");
						for (String str : strs)
							lessons.add(parseTd(row, colSize, col, rowSpan, str));
					} else {
						lessons.add(parseTd(row, colSize, col, rowSpan, content));
					}
				} else
					continue;
			}
		}
		return lessons;
	}

	private Lesson parseTd(int row, int colSize, int col, int rowSpan,
			String content) {
		String[] strs = content.split("<br>");
		String courseName = strs[0];
		String classesAndWeeks = strs[1];
		String courseCodeAndTeacher = strs[2];
		String classRoom = null;
		if (strs.length >= 4) {
			classRoom = strs[3];
		}
		String teacher = null;
		int weekStart = 0;
		int weekEnd = 0;
		String weeksRawStr = null;
		// 提取周数
		if (classesAndWeeks.contains("|")) {
			weeksRawStr = classesAndWeeks.split("\\|")[0];
		} else {
			weeksRawStr = classesAndWeeks.split("\\{")[1];
		}
		String[] weeksStrs = weeksRawStr.split("-");
		weekStart = this.extractInt(weeksStrs[0]);
		weekEnd = this.extractInt(weeksStrs[1]);
		// 提取任课老师
		if (courseCodeAndTeacher.contains("|")) {
			teacher = courseCodeAndTeacher.split("\\|")[1];
		} else {
			teacher = courseCodeAndTeacher;
		}
		int classStart = row + 1;
		int classEnd = classStart + rowSpan - 1;
		// 星期几的课，列数为9，为col -1
		int day = colSize == 9 ? col - 1 : col;
		return new Lesson(courseName, classStart, classEnd, classRoom,
				teacher, weekStart, weekEnd, day);
	}

	/**
	 * 从字符串中提取数字
	 * @param str 待提取字符串         
	 * @return 结果
	 */
	private int extractInt(String str) {
		String extractStr = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 48 && c <= 57) {
				extractStr += c;
			}
		}
		return Integer.parseInt(extractStr);
	}
}
