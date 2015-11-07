package com.attraction.schedule.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName="tb_lesson")
public class Lesson implements Parcelable {
	// 课程名称
	@DatabaseField(columnName = "name", useGetSet=true)
	private String name;
	// 课程开始节课
	@DatabaseField(columnName = "class_start", useGetSet=true)
	private int classStart;
	// 课程结束节课
	@DatabaseField(columnName = "class_end", useGetSet=true)
	private int classEnd;
	// 上课教室
	@DatabaseField(columnName = "class_room", useGetSet=true)
	private String classRoom;
	// 课程老师
	@DatabaseField(columnName = "teacher", useGetSet=true)
	private String teacher;
	// 开始周
	@DatabaseField(columnName = "week_start", useGetSet=true)
	private int weekStart;
	// 结束周
	@DatabaseField(columnName = "week_end", useGetSet=true)
	private int weekEnd;
	// 星期几的课
	@DatabaseField(columnName = "day", useGetSet=true)
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
	
	public void SetName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取开始节课
	 * @return
	 */
	public int getClassStart() {
		return classStart;
	}
	
	public void setClassStart(int classStart) {
		this.classStart = classStart;
	}
	
	/**
	 * 获取结束节课
	 * @return
	 */
	public int getClassEnd() {
		return classEnd;
	}
	
	public void setClassEnd(int classEnd) {
		this.classEnd = classEnd;
	}
	
	/**
	 * 获取开始周
	 * @return
	 */
	public int getWeekStart() {
		return weekStart;
	}
	
	public void setWeekStart(int weekStart) {
		this.weekStart = weekStart;
	}
	
	/**
	 * 获取结束周
	 * @return
	 */
	public int getWeekEnd() {
		return weekEnd;
	}
	
	public void setWeekEnd(int weekEnd) {
		this.weekEnd = weekEnd;
	}
	
	/**
	 * 获取教室
	 * @return
	 */
	public String getClassRoom() {
		return classRoom;
	}
	
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	
	/**
	 * 获取老师
	 * @return
	 */
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	/**
	 * 获取是星期几的课
	 * @return
	 */
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}
}
