package com.attraction.schedule.activity;

import com.attraction.schedule.R;
import com.attraction.schedule.fragment.CommunityFragment;
import com.attraction.schedule.fragment.MeFragment;
import com.attraction.schedule.fragment.ScheduleFragment;
import com.attraction.schedule.fragment.SquareFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;


public class MainActivity extends FragmentActivity {
	@Bind(R.id.tab_host_actvitiy_main)
	FragmentTabHost tabHost;
	
	// 定义数组来存放Fragment界面 
	@SuppressWarnings("rawtypes")
	private Class fragmentArray[] = {  
            CommunityFragment.class, ScheduleFragment.class,  
            SquareFragment.class, MeFragment.class  
    };
	
	private String tagArray[] = 
		{"Community", "Schedule", "Square", "Me"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initialize();
	}
	
	/**
	 * 初始化tabhost
	 */
	private void initialize() {
		tabHost.setup(this, getSupportFragmentManager(), 
				R.id.fl_actvitiy_main_real_content);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			String[] itemNames = getResources().getStringArray(R.array.tab_item_array_name);
			TabSpec tabSpec = tabHost.newTabSpec(tagArray[i]).
					setIndicator(itemNames[i]);
			tabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		tabHost.setCurrentTab(0);
	}

}
