package com.attraction.schedule.db;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.content.Context;

public class LessonDao {
	private Dao<Lesson, Integer> dao;
    private DatabaseHelper helper;
	
	public LessonDao(Context context) throws SQLException {
		helper = DatabaseHelper.getInstance(context);
		dao = helper.getDao(Lesson.class);
	}
	
	/**
	 * 查询所有课程数据
	 * @return
	 * @throws SQLException
	 */
	public List<Lesson> queryForAll() throws SQLException {
		return dao.queryForAll();
	}
	
	
	public void addLessons(List<Lesson> lessons) throws SQLException {
		for (Lesson lesson : lessons) {
			dao.create(lesson);
		}
	}
	
}
