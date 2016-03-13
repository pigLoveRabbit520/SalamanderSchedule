package com.attraction.schedule.activity;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.attraction.schedule.R;
import com.attraction.schedule.adapter.LessonGradeAdapter;
import com.attraction.schedule.db.LessonGrade;
import com.attraction.schedule.tool.FetchUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class GradeActivity extends Activity {
	@Bind(R.id.btn_grade_query)
	Button btnQuery;
	@Bind(R.id.spn_grade_year)
	Spinner spnYear;
	@Bind(R.id.spn_grade_term)
	Spinner spnTerm;
	@Bind(R.id.spn_grade_type)
	Spinner spnType;
	@Bind(R.id.lv_grade_lists)
	ListView lvGrade;
	private ProgressDialog proDialog;
	// 计数
	private static int count = 0;
	FetchUtil fetch = null;
	List<LessonGrade> grades = new ArrayList<LessonGrade>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grade);
		ButterKnife.bind(this);
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		if(count == 0) {
			count = 1;
			prepare();
		}
	}
	

	/**
	 * 准备学年和学期数据
	 */
	private void prepare() {
		showDialog();
		fetch = new FetchUtil(handler);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				fetch.getYearsTerms();
			}
		}).start();
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if(proDialog != null && proDialog.isShowing()) {
			this.proDialog.dismiss();
		}
	}
	
	/**
	 * 开启等待框
	 */
	public void showDialog() {
		proDialog = new ProgressDialog(GradeActivity.this);
		this.proDialog.setTitle(null);
		// 百分比计算
		proDialog.setProgress(100);
		proDialog.setIndeterminate(false);
		this.proDialog.setMessage("正在获取学年学期信息...");
		this.proDialog.setCancelable(false);
		this.proDialog.show();
	}
	
	/**
	 * 关闭等待框
	 */
	public void closeDialog() {
		this.proDialog.dismiss();
	}
	
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			super.handleMessage(msg);
			switch (msg.what) {
			case FetchUtil.FETCH_YEARS_TERMS_SUCCESS:
				String htmlYearsTerms = (String)msg.obj;
				extraYearsTerms(htmlYearsTerms);
				GradeActivity.this.closeDialog();
			    break;
			case FetchUtil.FETCH_YEARS_TERMS_FAIL:
				Toast.makeText(GradeActivity.this, "获取学年学期信息失败！", Toast.LENGTH_SHORT).show();
				GradeActivity.this.closeDialog();
				break;
			case FetchUtil.FETCH_GRADE_SUCCESS:
				String htmlGrades = (String)msg.obj;
				GradeActivity.this.parseLessonGrades(htmlGrades);
				LessonGradeAdapter adapter = new LessonGradeAdapter(GradeActivity.this, grades);
				GradeActivity.this.lvGrade.setAdapter(adapter);
				GradeActivity.this.closeDialog();
				break;
			case FetchUtil.FETCH_GRADE_FAIL:
				Toast.makeText(GradeActivity.this, "获取成绩失败！", Toast.LENGTH_SHORT).show();
				GradeActivity.this.closeDialog();
			default:
				break;
			}
		}

	};
	
	/**
	 * 提取出学年和学期信息
	 * @param rawHtml 待提取字符串
	 */
	private void extraYearsTerms(String rawHtml) {
		Document doc = Jsoup.parse(rawHtml);
		Element selectYear = doc.getElementById("ddlXN");
		Element selectTerm = doc.getElementById("ddlXQ");
		Elements optionsYear = selectYear.getElementsByTag("option");
		Elements optionsTerm = selectTerm.getElementsByTag("option");
		List<String> years = new ArrayList<String>();
		List<String> terms = new ArrayList<String>();
		for (Element option:optionsYear) {
			String value = option.attr("value");
			if(value == null || value == "") {
				continue;
			} else {
				years.add(value);
			}
		}
		for (Element option:optionsTerm) {
			String value = option.attr("value");
			if (value == null || value == "") {
				continue;
			} else {
				terms.add(value);
			}
		}
		ArrayAdapter<String> aptYears = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
		ArrayAdapter<String> aptTerms = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, terms);
		spnYear.setAdapter(aptYears);
		spnTerm.setAdapter(aptTerms);
	}
	
	/**
	 * 解析课程成绩数据
	 * @param rawHtml
	 */
	private void parseLessonGrades(String rawHtml) {
		Document doc = Jsoup.parse(rawHtml);
		Element table = doc.getElementById("DataGrid1");
		if(table == null) {
			Toast.makeText(GradeActivity.this, "抓取内容中不存在所需信息！", Toast.LENGTH_SHORT).show();
			GradeActivity.this.closeDialog();
			return;
		}
		Elements trs = table.getElementsByTag("tr");
		trs.remove(0);
		for (Element tr:trs) {
			Elements tds = tr.getElementsByTag("td");
			LessonGrade grade = new LessonGrade(
					tds.get(0).text(), 
					tds.get(1).text(), 
					tds.get(2).text(), 
					Integer.parseInt(tds.get(3).text()), 
					tds.get(4).text(), 
					Float.parseFloat(tds.get(7).text())
			);
			grades.add(grade);
		}
	}
	
	@OnClick(R.id.btn_grade_query)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_grade_query:
			queryGrade();
			break;
		default:
			break;
		}
	}

	private void queryGrade() {
		this.showDialog();
		final String year = spnYear.getSelectedItem().toString();
		final String term = spnTerm.getSelectedItem().toString();
		final int type = spnType.getSelectedItemPosition();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				fetch.getGradeInfo(year, term, type);
			}
		}).start();
	}
	

}
