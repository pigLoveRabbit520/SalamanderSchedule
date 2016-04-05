package com.attraction.schedule.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;


public class FetchHelper {
	// 第一个URL，等着为后面服务
	public static final String loginURL = "http://jwc2.usst.edu.cn";
	//第一个Post模拟登陆的URL
	public static final String redirectURL = loginURL + "/default2.aspx";
	// User-Agent
	private final static String userAgent =
			"Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko";
	// 连接超时
	private static int timeout = 5000;
	private final static String encoding = "gb2312";
	// viewState值
	private String viewState = null;
	// generator值
	private String viewStateGenerator = null;
	// validation值
	private String eventValidation = null;
	// 线程处理
	private Handler handler;
	// 网络出错
	public final static int NETWORK_ERROR = -34;
	// 登录错误，账号或者密码错误
	public final static int LOGIN_ERROR = -1;
	// 获取viewstate失败
	public final static int GET_VIEW_STATE_FAIL = 0;
	// 登录失败
	public final static int LOGIN_FAIL = 1;
	// 登录成功
	public final static int LOGIN_SUCCESS = 2;
	// 获取课程信息成功
	public final static int FETCH_LESSON_SUCCESS = 4;
	// 获取课程信息失败
	public final static int FETCH_LESSON_FAIL = 5;
	// 获取查询成绩时需要的学年和学期信息成功
	public final static int FETCH_YEARS_TERMS_SUCCESS = 6;
	// 获取查询成绩时需要的学年和学期信息失败
	public final static int FETCH_YEARS_TERMS_FAIL = 7;
	// 获取成绩信息成功
	public final static int FETCH_GRADE_SUCCESS = 8;
	// 获取成绩信息失败
	public final static int FETCH_GRADE_FAIL = 9;
	// 账号
	public static String account = null;
	// 密码
	public static String password = null;
	// 登录后cookie
	public static String cookie = null;
	// 重定向地址
	private static String location = null;
	
	// 异常
	String msg = null;
	
	public FetchHelper(Handler handler) {
		this.handler = handler;
	}

	private static AsyncHttpClient getHttpClient() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(timeout);
		client.addHeader("User-Agent", userAgent);
		return client;
	}
	
	/**
	 * 开始登陆
	 */
	public void startLogin() {
		getViewState();
	}
	
	/**
	 * 获取VIEWSTATE值
	 */
	private void getViewState() {
		AsyncHttpClient client = getHttpClient();
		client.get(loginURL,  new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if(statusCode == HttpStatus.SC_OK) {
					String rawHtml = new String(responseBody);
					Document doc = Jsoup.parse(rawHtml);
					Element viewStateInput = doc.select("input[name=__VIEWSTATE]").first();
					Element generatorInput = doc.select("input[name=__VIEWSTATEGENERATOR]").first();
					Element validationInput = doc.select("input[name=__EVENTVALIDATION]").first();
					if(viewStateInput == null) {
						Message msg = handler.obtainMessage();
						msg.what = GET_VIEW_STATE_FAIL;
						msg.obj = "教务处禁止外网访问！";
						msg.sendToTarget();
						return;
					}
					FetchHelper.this.viewState = viewStateInput.attr("value");
					if(generatorInput != null) {
						FetchHelper.this.viewStateGenerator = generatorInput.attr("value");
					}
					if(validationInput != null) {
						FetchHelper.this.eventValidation = validationInput.attr("value");
					}
					login();
				} else {
					Message msg = handler.obtainMessage();
					msg.what = GET_VIEW_STATE_FAIL;
					msg.obj = "教务处禁止外网访问！";
					msg.sendToTarget();
					return;
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				handler.sendEmptyMessage(NETWORK_ERROR);
			}
		});
	}
	
	private void login() {
		AsyncHttpClient client = getHttpClient();
		client.setEnableRedirects(false);
		RequestParams params = new RequestParams();
		params.add("__VIEWSTATE", this.viewState);
		params.add("__VIEWSTATEGENERATOR", this.viewStateGenerator);
		params.add("__EVENTVALIDATION", this.eventValidation);
		// 账号
		params.add("TextBox1", account);
		// 密码
		params.add("TextBox2", password);
		// 学生
		params.add("RadioButtonList1", "%D1%A7%C9%FA");
		params.add("Button1", "");
		params.add("lbLanguage", "");
		params.setContentEncoding(encoding);
		client.post(redirectURL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				handler.sendEmptyMessage(LOGIN_ERROR);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// 302表示重定向状态
				if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					for (int i = 0; i < headers.length; i++) {
						Header header = headers[i];
						if (header.getName().equals("Set-Cookie")) {
							cookie = header.getValue();
						}
						if (header.getName().equals("location")) {
							location = header.getValue();
						}
					}
					handler.sendEmptyMessage(LOGIN_SUCCESS);
				} else if (statusCode == 0) {
					handler.sendEmptyMessage(NETWORK_ERROR);
				} else {
					handler.sendEmptyMessage(LOGIN_FAIL);
					login();
				}
			}
		});
	}
	
	
	public void getLessonInfo() {
		AsyncHttpClient client = getHttpClient();
		String mainURL = loginURL + "/xskbcx.aspx?xh=" + account + "&gnmkdm=N121603";
		client.addHeader("Cookie", cookie);
		client.addHeader("Referer", loginURL);
		client.post(mainURL, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode == HttpStatus.SC_OK) {
					// 获取纯净的主页HTML源码，这里大家可以将mianhtml定义在其他地方
					String html = null;
					try {
						html = new String(responseBody, encoding);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					Message msg = handler.obtainMessage();
					msg.obj = html;
					msg.what = FETCH_LESSON_SUCCESS;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(FETCH_LESSON_FAIL);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				handler.sendEmptyMessage(NETWORK_ERROR);
			}
		});
	}

	/**
	 * 清理登录cookie
	 */
	public static void clearCookie() {
		cookie = null;
	}
	
	/**
	 * 获取学年和学期
	 */
	public void getYearsTerms() {
		AsyncHttpClient client = getHttpClient();
		client.addHeader("Cookie", cookie);
		client.addHeader("Referer", loginURL);
		String mainURL = FetchHelper.loginURL + "/xscj.aspx?xh=" + FetchHelper.account + "&gnmkdm=N121614";
		client.get(mainURL, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if(statusCode == HttpStatus.SC_OK) {
                    String html = null;
                    try {
                        html = new String(responseBody, encoding);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Document doc = Jsoup.parse(html);
					Element viewStateInput = doc.select("input[name=__VIEWSTATE]").first();
					Element generatorInput = doc.select("input[name=__VIEWSTATEGENERATOR]").first();
					Element validationInput = doc.select("input[name=__EVENTVALIDATION]").first();
					viewState = viewStateInput.attr("value");
					if(generatorInput != null) viewStateGenerator = generatorInput.attr("value");
					if(validationInput != null) eventValidation = validationInput.attr("value");
					Message msg = handler.obtainMessage();
					msg.obj = html;
					msg.what = FETCH_YEARS_TERMS_SUCCESS;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(FETCH_YEARS_TERMS_FAIL);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				handler.sendEmptyMessage(NETWORK_ERROR);
			}
		});
	}
	
	/**
	 * 获取成绩信息
	 * @param year 学年
	 * @param term 学期
	 * @param type 查询方式
	 */
	public void getGradeInfo(String year, String term, int type) {
		AsyncHttpClient client = getHttpClient();
		String url = FetchHelper.loginURL  + "/xscj.aspx?xh=" + FetchHelper.account + "&gnmkdm=N121614";
		client.addHeader("Referer", url);
		client.addHeader("Cookie", cookie);
		RequestParams params = new RequestParams();
		params.add("__VIEWSTATE", this.viewState);
		params.add("__VIEWSTATEGENERATOR", this.viewStateGenerator);
		params.add("__EVENTVALIDATION", this.eventValidation);
		params.add("ddlXN", year);
		params.add("ddlXQ", term);
		params.add("txtQSCJ", "0");
		params.add("txtZZCJ", "100");
		if(type == 0) {
			params.add("Button1", "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF");
		} else {
			params.add("Button5", "%B0%B4%D1%A7%C4%EA%B2%E9%D1%AF");
		}
		params.setContentEncoding("gb2312");
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if(statusCode == HttpStatus.SC_OK) {
					String html = null;
					try {
						html = new String(responseBody, encoding);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					Message msg = handler.obtainMessage();
					msg.obj = html;
					msg.what = FETCH_GRADE_SUCCESS;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(FETCH_GRADE_FAIL);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				handler.sendEmptyMessage(NETWORK_ERROR);
			}
		});
	}
}
