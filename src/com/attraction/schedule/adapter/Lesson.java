package com.attraction.schedule.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Lesson implements Parcelable {
	// 课程名称
	private String name;
	// 课程开始节课
	private int classStart;
	// 课程结束节课
	private int classEnd;
	// 上课教室
	private String classRoom;
	// 课程老师
	private String teacher;
	// 开始周
	private int weekStart;
	// 结束周
	private int weekEnd;
	// 星期几的课
	private int day;
	
	public Lesson(String name, int classStart, int classEnd, String classRoom,
			String teacher, int weekStart, int weekEnd, int day) {
		this.name = name;
		this.classStart = classStart;
		this.classEnd = classEnd;
		this.classRoom = classRoom;
		this.teacher = teacher;
		this.weekStart = weekStart;
		this.weekEnd = weekEnd;
		this.day = day;
	}
	
	public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
		@Override
		public Lesson[] newArray(int size) {
			return new Lesson[size];
		}

		@Override
		public Lesson createFromParcel(Parcel in) {
			return new Lesson(in);
		}
	};
	
	public Lesson(Parcel in) {
		this.name 				= in.readString();
		this.classStart 		= in.readInt();
		this.classEnd 			= in.readInt();
		this.classRoom 			= in.readString();
		this.teacher 			= in.readString();
		this.weekStart 			= in.readInt();
		this.weekEnd 			= in.readInt();
    }
	
	@Override
	public void writeToParcel(Parcel out, int flag) {
		// TODO 自动生成的方法存根
		out.writeString(this.name);
		out.writeInt(this.classStart);
		out.writeInt(this.classEnd);
		out.writeString(this.classRoom);
		out.writeString(this.teacher);
		out.writeInt(this.weekStart);
		out.writeInt(this.weekEnd);
		out.writeInt(this.day);
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 获取开始节课
	 * @return
	 */
	public int getClassStart() {
		return classStart;
	}
	
	/**
	 * 获取结束节课
	 * @return
	 */
	public int getClassEnd() {
		return classEnd;
	}
	
	
	/**
	 * 获取开始周
	 * @return
	 */
	public int getWeekStart() {
		return weekStart;
	}
	
	/**
	 * 获取结束周
	 * @return
	 */
	public int getWeekEnd() {
		return weekEnd;
	}
	
	
	public String getClassRoom() {
		return classRoom;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public int getDay() {
		return day;
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}
}
