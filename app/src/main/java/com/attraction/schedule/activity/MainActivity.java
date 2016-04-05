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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
	
	// 定义数组来存放按钮图片
	private int mImageViewArray[] = {
			R.drawable.community_tab_item_bg,
			R.drawable.schedule_tab_item_bg,
			R.drawable.square_tab_item_bg, 
			R.drawable.me_tab_item_bg
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
					setIndicator(getTabItemView(i, itemNames[i]));
			tabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		tabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.white));
		// TabWidget可以理解为底部选项卡栏
		tabHost.setCurrentTab(0);
	}
	
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index, String name) {
		View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_item_icon);
		imageView.setImageResource(mImageViewArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.tv_tab_item_text);		
		textView.setText(name);
		return view;
	}

}
