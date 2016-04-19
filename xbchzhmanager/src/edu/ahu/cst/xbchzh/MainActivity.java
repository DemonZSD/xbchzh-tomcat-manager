package edu.ahu.cst.xbchzh;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import edu.ahu.cst.utils.Utils;

public class MainActivity extends ActionBarActivity {
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			TextView tv = (TextView) findViewById(R.id.restartView);
			tv.setText(Html.fromHtml(msg.obj.toString()));
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	/**
	 * 显示运行状态
	 * @param v
	 */
	public void stateShow(View v){
		Thread t = new Thread(){
			@Override
			public void run(){
				String path = "http://www.xbchzh.com/isOnline.html";
				//使用httpClient 框架 做get提交
				//1.创建httpclient 对象
				HttpClient hc = new DefaultHttpClient();
				//2.创建httpGet对象，构造方法的参数就是网址
				HttpGet hg = new HttpGet(path);
				//3.使用客户端对象，把get请求对象发送出去
				String text = "";
				try {
					HttpResponse hr =  hc.execute(hg);
					//拿到响应头中的状态行
					StatusLine sl = hr.getStatusLine();
					
					//判断响应码
					if(sl.getStatusCode() == 200){
						//拿到相应头的实体
						HttpEntity he = hr.getEntity();
						//拿到实体中的内容，其实就是服务器返回的输入流
						InputStream is = he.getContent();
						text = Utils.getTextFromStream(is);
					}else{
						text = "链接超时！";
					}
					Message msg = handler.obtainMessage();
					msg.obj = text;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		};
		t.start();
	}
	/**
	 * 重启服务器
	 * @param v
	 */
	public void restartServer(View v){
		AlertDialog.Builder builder = new Builder(this);
		//设置icon
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//设置标题
		builder.setTitle("请慎重操作");
		//设置提示内容
		builder.setMessage("确定重启Tomcat？该操作会使系统停止运行，等待时间较长，如果重启失败，请重新重启！");
		
		//设置确定按钮
		builder.setPositiveButton("确定重启",new OnClickListener(){
			Thread thread = new Thread(){
				@Override
				public void run(){
					String path = "http://xc.xbchzh.com/demo.cgi?cmd=restartTomcat";
					//使用httpClient 框架 做get提交
					//1.创建httpclient 对象
					HttpClient hc = new DefaultHttpClient();
					//2.创建httpGet对象，构造方法的参数就是网址
					HttpGet hg = new HttpGet(path);
					//3.使用客户端对象，把get请求对象发送出去
					String text = "";
					try {
						HttpResponse hr =  hc.execute(hg);
						//拿到响应头中的状态行
						StatusLine sl = hr.getStatusLine();
						
						//判断响应码
						if(sl.getStatusCode() == 200){
							//拿到相应头的实体
							HttpEntity he = hr.getEntity();
							//拿到实体中的内容，其实就是服务器返回的输入流
							InputStream is = he.getContent();
							text = Utils.getTextFromStream(is);
						}else{
							text = "链接超时！";
						}
						Message msg = handler.obtainMessage();
						msg.obj = text;
						handler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			};
			@Override
			public void onClick(DialogInterface dialog, int which) {
				thread.start();
			}
			
		});
		//设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "取消重启", 0).show();
			}
			
		});
		//使用创建器,生成一个对话框对象
		AlertDialog ad = builder.create();
		ad.show();
	}
	/**
	 * 清除TextView中的内容
	 * @param v
	 */
	public void clearContent(View v){
		TextView tv = (TextView) findViewById(R.id.restartView);
		tv.setText("");
	}
	/**
	 * 显示tomcat日志
	 * @param v
	 */
	public void showlog(View v){
		Thread t = new Thread(){
			@Override
			public void run(){
				String path = "http://xc.xbchzh.com/demo.cgi?cmd=showTomcatLog";
				//使用httpClient 框架 做get提交
				//1.创建httpclient 对象
				HttpClient hc = new DefaultHttpClient();
				//2.创建httpGet对象，构造方法的参数就是网址
				HttpGet hg = new HttpGet(path);
				//3.使用客户端对象，把get请求对象发送出去
				String text = "";
				try {
					HttpResponse hr =  hc.execute(hg);
					//拿到响应头中的状态行
					StatusLine sl = hr.getStatusLine();
					
					//判断响应码
					if(sl.getStatusCode() == 200){
						//拿到相应头的实体
						HttpEntity he = hr.getEntity();
						//拿到实体中的内容，其实就是服务器返回的输入流
						InputStream is = he.getContent();
						text = Utils.getTextFromStream(is);
					}else{
						text = "链接超时！";
					}
					Message msg = handler.obtainMessage();
					msg.obj = text;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		};
		t.start();
	}
}
