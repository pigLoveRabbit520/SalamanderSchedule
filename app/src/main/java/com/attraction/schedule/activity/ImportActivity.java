package com.attraction.schedule.activity;

import com.attraction.schedule.R;
import com.attraction.schedule.helper.FetchHelper;
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
import android.widget.LinearLayout;
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
    @Bind(R.id.tv_import_loginSuccess)
    TextView tvLoginSuccess;
	@Bind(R.id.ll_import_container)
    LinearLayout llContainer = null;
	private ProgressDialog proDialog;
    // 是否已经登录
    private static boolean isLogin = false;
	FetchHelper fetch = null;
	private boolean existData = false;
	public static int FETCH_SUCCESS = 1;
	public static int NO_DATA = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import);
		ButterKnife.bind(this);
		fetch = new FetchHelper(handler);
        if (isLogin) {
            hide();
        }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (proDialog != null && proDialog.isShowing()) {
			this.proDialog.dismiss();
		}
	}

    /**
     * 隐藏登录输入框和按钮
     */
    private void hide() {
        llContainer.setVisibility(View.GONE);
        tvLoginSuccess.setVisibility(View.VISIBLE);
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

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case FetchHelper.NETWORK_ERROR:
					tvLog.append("网络出了小问题" + "\n");
					proDialog.dismiss();
					break;
			case FetchHelper.GET_VIEW_STATE_FAIL:
				String info = (String) msg.obj;
				tvLog.append("viewstate获取失败：" + info + "\n");
				proDialog.dismiss();
				break;
			case FetchHelper.LOGIN_ERROR:
				Toast.makeText(ImportActivity.this, "账号或密码错误！",
						Toast.LENGTH_SHORT).show();
				proDialog.dismiss();
				break;
			case FetchHelper.LOGIN_FAIL:
				tvLog.append("登录失败!" + "\n");
				break;
			case FetchHelper.LOGIN_SUCCESS:
                hide();
                isLogin = true;
				tvLog.append("登录成功！\n");
				proDialog.dismiss();
				break;
			case FetchHelper.FETCH_LESSON_SUCCESS:
				tvLog.append("抓取成功!" + "\n");
				Intent intent = new Intent();
				intent.putExtra("html", (String)msg.obj);
				ImportActivity.this.existData = true;
				setResult(FETCH_SUCCESS, intent);
				ImportActivity.this.finish();
				break;
			case FetchHelper.FETCH_LESSON_FAIL:
				tvLog.append("抓取失败：" + "\n");
				proDialog.dismiss();
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
			if (!FetchHelper.isLogin()) {
				Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
			} else {
				fetch.getLessonInfo();
			}
			break;
		case R.id.btn_import_grade:
			if (!FetchHelper.isLogin()) {
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
		if (FetchHelper.account != null)
			FetchHelper.clearCookie();
		FetchHelper.account = account;
		FetchHelper.password = password;
		return true;
	}
	
	@Override
	public void finish() {
		if(!existData) {
			setResult(NO_DATA, null);
		}
		super.finish();
	}
}
