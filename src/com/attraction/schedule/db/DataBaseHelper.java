package com.attraction.schedule.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String  DATABASE_NAME = "schedule.db";
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database, 
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Lesson.class);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,  
            ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根
		
	}

}
