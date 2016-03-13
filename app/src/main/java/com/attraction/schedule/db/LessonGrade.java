package com.attraction.schedule.db;

public class LessonGrade {
	// 课程代码
	private String courseCode;
	// 课程名称
	private String courseName;
	// 课程性质
	private String courseAttr;
	// 成绩
	private float grade;
	// 课程归属
	private String courseBelong;
	// 课程学分
	private float credit;
	
	public LessonGrade(String code, String name, String attr, 
			float grade, String courseBelong, float credit) {
		this.courseCode = code;
		this.courseName = name;
		this.courseAttr = attr;
		this.grade = grade;
		this.courseBelong = courseBelong;
		this.credit = credit;
	}
	
	public String getCourseCode() {
		return this.courseCode;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
	public String getCourseAttr() {
		return this.courseAttr;
	}
	
	public float getCourseGrade() {
		return this.grade;
	}
	
	public String getCourseBelong() {
		return this.courseBelong;
	}
	
	public float getCredit() {
		return this.credit;
	}
}
