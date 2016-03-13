package com.attraction.schedule.fragment;

import com.attraction.schedule.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MeFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_me,
					container, false);
		} else {
			this.removeParent();
		}
		return rootView;
	}

}
