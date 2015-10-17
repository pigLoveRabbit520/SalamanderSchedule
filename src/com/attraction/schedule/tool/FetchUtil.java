package com.attraction.schedule.tool;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.Handler;
import android.os.Message;


public class FetchUtil {
	//第一个URL，等着为后面服务
	public static final String loginURL = "http://jwc1.usst.edu.cn";
	//第一个Post模拟登陆的URL
	public static final String redirectURL = "http://jwc1.usst.edu.cn/default2.aspx";
	// viewState值
	private String viewState = null;
	// generator值
	private String viewStateGenerator = null;
	// validation值
	private String eventValidation = null;
	// 线程处理
	private Handler handler;
	// 获取viewstate失败
	public final static int GET_VIEW_STATE_FAIL = 0;
	// 登录失败
	public final static int LOGIN_FAIL = 1;
	// 登录错误，账号或者密码错误
	public final static int LOGIN_ERROR = -1;
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
	
	public FetchUtil(Handler handler) {
		this.handler = handler;
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				HttpGet get = new HttpGet(FetchUtil.loginURL);
				HttpClient httpClient = new DefaultHttpClient();
				// 通过网络与服务器建立连接的超时时间
				HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
				// 从服务器获取响应数据的超时时间
				HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5000);
				// 响应请求
		        HttpResponse response;
		        try {
					response = httpClient.execute(get);
					// 获取响应状态码
			        int status = response.getStatusLine().getStatusCode();
			        if(status == HttpStatus.SC_OK) {
			        	HttpEntity entity = response.getEntity();
			        	String rawHtml = EntityUtils.toString(entity);;
			        	Document doc = Jsoup.parse(rawHtml);
						Element viewStateInput = doc.select("input[name=__VIEWSTATE]").first();
						Element generatorInput = doc.select("input[name=__VIEWSTATEGENERATOR]").first();
						Element validationInput = doc.select("input[name=__EVENTVALIDATION]").first();
						if(viewStateInput == null) {
							// 网速较慢时发生这种情况
							Message msg = handler.obtainMessage();
							msg.what = GET_VIEW_STATE_FAIL;
							msg.obj = "你的网速较慢！";
							handler.sendMessage(msg);
							return;
						}
						FetchUtil.this.viewState = viewStateInput.attr("value");
						if(generatorInput != null) {
							FetchUtil.this.viewStateGenerator = generatorInput.attr("value");
						}
						if(validationInput != null) {
							FetchUtil.this.eventValidation = validationInput.attr("value");
						}
						new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO 自动生成的方法存根
								FetchUtil.this.login();
							}
						}).start();
			        } else {
			        	Message msg = handler.obtainMessage();
			        	msg.what = FetchUtil.GET_VIEW_STATE_FAIL;
			        	msg.obj = "抓取失败";
			        	handler.sendMessage(msg);
			        }
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Message msg = handler.obtainMessage();
		        	msg.what = FetchUtil.GET_VIEW_STATE_FAIL;
		        	msg.obj = "发生异常";
		        	handler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	private void login() {
		// 建立一个Post请求，第一步的方法是Post方法嘛
		HttpPost httpPost = new HttpPost(FetchUtil.redirectURL);
		// 禁止重定向，由于刚刚Post的状态值是重定向，所以我们要去禁止它，不然网页会乱飞
		httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false); 
		// 设置头部信息（头部信息在刚刚的Httpwatch下面Headers标签会有，不过我感觉写多跟写少没多大区别，只是多写没有坏处吧。）
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko"); 
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
		
		// 第一种模拟登陆传值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 将刚刚获取到的值添加到List的中
		params.add(new BasicNameValuePair("__VIEWSTATE", this.viewState));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", this.viewStateGenerator));  
		params.add(new BasicNameValuePair("__EVENTVALIDATION", this.eventValidation));
		// 账号
		params.add(new BasicNameValuePair("TextBox1", account));
		// 密码
		params.add(new BasicNameValuePair("TextBox2", password));
		// 学生
		params.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));
		params.add(new BasicNameValuePair("Button1", ""));
		params.add(new BasicNameValuePair("lbLanguage", ""));
        // 响应请求
        HttpResponse response;
		try {
			// 传递参数的时候注意编码使用,否则乱码        
	        httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
			response = new DefaultHttpClient().execute(httpPost);
			 // 获取响应状态码
	        int status = response.getStatusLine().getStatusCode();
	        // 302表示重定向状态
	        if(status == 302) {
	        	// 获取响应的cookie值
	            cookie = response.getFirstHeader("Set-Cookie").getValue();
	            location = response.getFirstHeader("location").getValue();
	            handler.sendEmptyMessage(LOGIN_SUCCESS);
	        } else if(status == HttpStatus.SC_OK) {
	        	handler.sendEmptyMessage(LOGIN_ERROR);
	        } else {
	        	handler.sendEmptyMessage(LOGIN_FAIL);
	        	FetchUtil.this.getViewState();
	        }
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			handler.sendEmptyMessage(LOGIN_FAIL);
			FetchUtil.this.getViewState();
		}
       
	}
	
	
	public void getLessonInfo() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				String mainURL = FetchUtil.loginURL + "/xskbcx.aspx?xh=" + account + "&gnmkdm=N121603";
				HttpGet get = new HttpGet(mainURL);
				get.setHeader("Referer", FetchUtil.loginURL);
				get.addHeader("Cookie", cookie);
				// 获取Get响应，如果状态码是200的话表示连接成功
				HttpResponse httpResponse;
				try {
					httpResponse = new DefaultHttpClient().execute(get);
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					     HttpEntity entity = httpResponse.getEntity();
				         // 获取纯净的主页HTML源码，这里大家可以将mianhtml定义在其他地方
					     String html = EntityUtils.toString(entity);
					     Message msg = handler.obtainMessage();
					     msg.obj = html;
					     msg.what = FETCH_LESSON_SUCCESS;
					     handler.sendMessage(msg);
					} else {
						handler.sendEmptyMessage(FETCH_LESSON_FAIL);
					}
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					handler.sendEmptyMessage(FETCH_LESSON_FAIL);
					e.printStackTrace();
				}
			}
		}).start();
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
		String mainURL = FetchUtil.loginURL + "/xscj.aspx?xh=" + FetchUtil.account + "&gnmkdm=N121614";
		HttpGet get = new HttpGet(mainURL);
		get.setHeader("Referer", FetchUtil.loginURL);
		get.addHeader("Cookie", cookie);
		// 获取Get响应，如果状态码是200的话表示连接成功
		HttpResponse response;
		try {
			response = new DefaultHttpClient().execute(get);
			if(response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity);
				Document doc = Jsoup.parse(html);
			    Element viewStateInput = doc.select("input[name=__VIEWSTATE]").first();
			    Element generatorInput = doc.select("input[name=__VIEWSTATEGENERATOR]").first();
			    Element validationInput = doc.select("input[name=__EVENTVALIDATION]").first();
			    this.viewState = viewStateInput.attr("value");
			    if(generatorInput != null) this.viewStateGenerator = generatorInput.attr("value");
			    if(validationInput != null) this.eventValidation = validationInput.attr("value");
			    Message msg = handler.obtainMessage();
			    msg.obj = html;
			    msg.what = FETCH_YEARS_TERMS_SUCCESS;
			    handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(FETCH_YEARS_TERMS_FAIL);
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			handler.sendEmptyMessage(FETCH_YEARS_TERMS_FAIL);
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取成绩信息
	 * @param year 学年
	 * @param term 学期
	 * @param type 查询方式
	 */
	public void getGradeInfo(String year, String term, int type) {
		String url = FetchUtil.loginURL  + "/xscj.aspx?xh=" + FetchUtil.account + "&gnmkdm=N121614";
		HttpPost httpPost = new HttpPost(url);
		// 设置头部信息
		httpPost.setHeader("Referer", url); 
		httpPost.addHeader("Cookie", FetchUtil.cookie);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", this.viewState));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", this.viewStateGenerator));  
		params.add(new BasicNameValuePair("__EVENTVALIDATION", this.eventValidation));
		params.add(new BasicNameValuePair("ddlXN", year));
		params.add(new BasicNameValuePair("ddlXQ", term));
		params.add(new BasicNameValuePair("txtQSCJ", "0"));
		params.add(new BasicNameValuePair("txtZZCJ", "100"));
		if(type == 0) {
			params.add(new BasicNameValuePair("Button1", "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF"));
		} else {
			params.add(new BasicNameValuePair("Button5", "%B0%B4%D1%A7%C4%EA%B2%E9%D1%AF"));
		}
		try {
			// 传递参数的时候注意编码使用,否则乱码        
	        httpPost.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
	        HttpResponse response = new DefaultHttpClient().execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200) {
			     HttpEntity entity = response.getEntity();
			     String html = EntityUtils.toString(entity);
			     Message msg = handler.obtainMessage();
			     msg.obj = html;
			     msg.what = FETCH_GRADE_SUCCESS;
			     handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(FETCH_GRADE_FAIL);
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			handler.sendEmptyMessage(FETCH_GRADE_FAIL);
			e.printStackTrace();
		}
	}
}
