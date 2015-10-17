package com.attraction.schedule.adapter;

import java.util.List;
import com.attraction.schedule.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LessonGradeAdapter extends BaseAdapter {

	private int resid = com.attraction.schedule.R.layout.listitem_grade;
	private Context context;
	private List<LessonGrade> grades;

	public LessonGradeAdapter(Context context, List<LessonGrade> grades) {
		this.context = context;
		this.grades = grades;
	}

	public int getCount() {
		return grades.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public Object getItem(int position) {
		return grades.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView tvCourseCode = null;
		TextView tvCourseName = null;
		TextView tvCourseAttr = null;
		TextView tvGrade = null;
		TextView tvCourseBelong = null;
		TextView tvCredit = null;
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(resid, null);
			tvCourseCode = (TextView) convertView.findViewById(com.attraction.schedule.R.id.tv_listitem_grade_courseCode);
			tvCourseName = (TextView) convertView.findViewById(R.id.tv_listitem_grade_courseName);
			tvCourseAttr = (TextView) convertView.findViewById(R.id.tv_listitem_grade_courseAttr);
			tvGrade = (TextView)convertView.findViewById(R.id.tv_listitem_grade_grade);
			tvCourseBelong = (TextView)convertView.findViewById(R.id.tv_listitem_grade_courseBelong);
			tvCredit = (TextView)convertView.findViewById(R.id.tv_listitem_grade_credit);
			holder.tvCourseCode = tvCourseCode;
			holder.tvCourseName = tvCourseName;
			holder.tvCourseAttr = tvCourseAttr;
			holder.tvGrade = tvGrade;
			holder.tvCourseBelong = tvCourseBelong;
			holder.tvCredit = tvCredit;
			convertView.setTag(holder);
		} else {
			// 緩存了，成为新出现Item的ConvertView
			holder = (Holder)convertView.getTag();
		}
		LessonGrade grade = grades.get(position);
		holder.tvCourseCode.setText("课程代码：" + grade.getCourseCode());
		holder.tvCourseName.setText("课程名称：" + grade.getCourseName());
		holder.tvCourseAttr.setText("课程性质：" + grade.getCourseAttr());
		holder.tvGrade.setText("成绩：" + grade.getCourseGrade());
		holder.tvCourseBelong.setText("课程归属：" + grade.getCourseBelong());
		holder.tvCredit.setText("学分：" + grade.getCredit());
		return convertView;
	}
	
	class Holder
	{
		public TextView tvCourseCode = null;
		public TextView tvCourseName = null;
		public TextView tvCourseAttr = null;
		public TextView tvGrade = null;
		public TextView tvCourseBelong = null;
		public TextView tvCredit = null;
	}

}
