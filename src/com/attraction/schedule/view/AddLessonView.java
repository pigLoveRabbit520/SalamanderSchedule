package com.attraction.schedule.view;

import com.attraction.schedule.R;
import android.content.Context;
import android.widget.ImageButton;

public class AddLessonView extends ImageButton {

	public AddLessonView(Context context) {
		super(context);
		this.setScaleType(ScaleType.CENTER);
		int color = getResources().getColor(R.color.add_lesson_back_color);
		this.setBackgroundColor(color);
		this.setImageResource(R.drawable.add_lesson);
	}
}
