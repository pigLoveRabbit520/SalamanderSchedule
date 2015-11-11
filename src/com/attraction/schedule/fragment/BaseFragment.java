package com.attraction.schedule.fragment;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	protected ViewGroup rootView = null;
	
	protected void removeParent() {
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
	}
}
