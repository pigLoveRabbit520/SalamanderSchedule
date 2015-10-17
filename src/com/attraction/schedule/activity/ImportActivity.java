package com.attraction.schedule.activity;

import com.attraction.schedule.R;
import com.attraction.schedule.tool.FetchUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ImportActivity extends Activity {
	@Bind(R.id.edtTxt_import_account)
	TextView tvAccount;
	@Bind(R.id.edtTxt_import_password)
	TextView tvPassword;
	@Bind(R.id.btn_import_login)
	Button btnLogin = null;
	@Bind(R.id.btn_import_lesson)
	Button btnQueryLesson = null;
	@Bind(R.id.btn_import_grade)
	Button btnQueryGrade = null;
	@Bind(R.id.tv_main_log)
	TextView tvLog;
	private ProgressDialog proDialog;
	FetchUtil fetch = null;
	private boolean existData = false;
	public static int FETCH_SUCCESS = 1;
	public static int NO_DATA = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import);
		ButterKnife.bind(this);
		fetch = new FetchUtil(handler);
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if (proDialog != null && proDialog.isShowing()) {
			this.proDialog.dismiss();
		}
	}

	/**
	 * 开启等待框
	 */
	public void showDialog() {
		proDialog = new ProgressDialog(ImportActivity.this);
		this.proDialog.setTitle(null);
		// 百分比计算
		proDialog.setProgress(100);
		proDialog.setIndeterminate(false);
		this.proDialog.setMessage("正在登录...");
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
			case FetchUtil.GET_VIEW_STATE_FAIL:
				String info = (String) msg.obj;
				tvLog.append("viewstate获取失败：" + info + "\n");
				ImportActivity.this.closeDialog();
				break;
			case FetchUtil.LOGIN_ERROR:
				Toast.makeText(ImportActivity.this, "账号或密码错误！",
						Toast.LENGTH_SHORT).show();
				ImportActivity.this.closeDialog();
				break;
			case FetchUtil.LOGIN_FAIL:
				tvLog.append("登录失败!" + "\n");
				break;
			case FetchUtil.LOGIN_SUCCESS:
				tvLog.append("登录成功！\n");
				ImportActivity.this.closeDialog();
				break;
			case FetchUtil.FETCH_LESSON_SUCCESS:
				tvLog.append("抓取成功!" + "\n");
				Intent intent = new Intent();
				intent.putExtra("html", (String)msg.obj);
				ImportActivity.this.existData = true;
				setResult(FETCH_SUCCESS, intent);
				ImportActivity.this.finish();
				break;
			case FetchUtil.FETCH_LESSON_FAIL:
				tvLog.append("抓取失败：" + (String) msg.obj);
				ImportActivity.this.closeDialog();
				break;
			default:
				break;
			}
		}

	};

	@OnClick({ R.id.btn_import_login, R.id.btn_import_lesson, R.id.btn_import_grade })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_import_login:
			if (setStuInfo()) {
				showDialog();
				fetch.startLogin();
			}
			break;
		case R.id.btn_import_lesson:
			if (FetchUtil.cookie == null) {
				Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
			} else {
				fetch.getLessonInfo();
			}
			break;
		case R.id.btn_import_grade:
			if (FetchUtil.cookie == null) {
				Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(ImportActivity.this,
						GradeActivity.class);
				startActivity(intent);
			}
		default:
			break;
		}
	}

	/**
	 * 设置学生信息
	 * 
	 * @return
	 */
	private boolean setStuInfo() {
		if (tvAccount.getText().length() == 0
				|| tvPassword.getText().length() == 0) {
			Toast.makeText(this.getApplicationContext(), "请设置你的信息！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		String account = tvAccount.getText().toString();
		String password = tvPassword.getText().toString();
		if (FetchUtil.account != null)
			FetchUtil.clearCookie();
		FetchUtil.account = account;
		FetchUtil.password = password;
		return true;
	}
	
	@Override
	public void finish() {
		// TODO 自动生成的方法存根
		if(!existData) {
			setResult(NO_DATA, null);
		}
		super.finish();
	}
}
