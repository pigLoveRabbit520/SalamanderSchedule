package com.attraction.schedule.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String  DATABASE_NAME = "schedule.db";
	private static final int DATABASE_VERSION = 1;
	// 双重加锁
	private volatile static DatabaseHelper dbHelper;
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * 单例模式
	 * @param context
	 * @return
	 */
    public static DatabaseHelper getHelper(Context context)
    {
        if (dbHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (dbHelper == null)
                	dbHelper = new DatabaseHelper(context);
            }
        }
        return dbHelper;
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
