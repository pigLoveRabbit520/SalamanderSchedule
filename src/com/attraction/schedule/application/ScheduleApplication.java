package com.attraction.schedule.application;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;

public class ScheduleApplication extends Application {
	public static final String CACHE_DIR = "Schedule/cache";
	
	@Override
	public void onCreate() {
		super.onCreate();
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), CACHE_DIR);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))  
	    .memoryCacheSize(2 * 1024 * 1024)
		.diskCache(new UnlimitedDiskCache(cacheDir))
		.build();
		// 全局初始化此配置  
		ImageLoader.getInstance().init(config);
	}

}
