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
	 * ��ʾ����״̬
	 * @param v
	 */
	public void stateShow(View v){
		Thread t = new Thread(){
			@Override
			public void run(){
				String path = "http://www.xbchzh.com/isOnline.html";
				//ʹ��httpClient ��� ��get�ύ
				//1.����httpclient ����
				HttpClient hc = new DefaultHttpClient();
				//2.����httpGet���󣬹��췽���Ĳ���������ַ
				HttpGet hg = new HttpGet(path);
				//3.ʹ�ÿͻ��˶��󣬰�get��������ͳ�ȥ
				String text = "";
				try {
					HttpResponse hr =  hc.execute(hg);
					//�õ���Ӧͷ�е�״̬��
					StatusLine sl = hr.getStatusLine();
					
					//�ж���Ӧ��
					if(sl.getStatusCode() == 200){
						//�õ���Ӧͷ��ʵ��
						HttpEntity he = hr.getEntity();
						//�õ�ʵ���е����ݣ���ʵ���Ƿ��������ص�������
						InputStream is = he.getContent();
						text = Utils.getTextFromStream(is);
					}else{
						text = "���ӳ�ʱ��";
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
	 * ����������
	 * @param v
	 */
	public void restartServer(View v){
		AlertDialog.Builder builder = new Builder(this);
		//����icon
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//���ñ���
		builder.setTitle("�����ز���");
		//������ʾ����
		builder.setMessage("ȷ������Tomcat���ò�����ʹϵͳֹͣ���У��ȴ�ʱ��ϳ����������ʧ�ܣ�������������");
		
		//����ȷ����ť
		builder.setPositiveButton("ȷ������",new OnClickListener(){
			Thread thread = new Thread(){
				@Override
				public void run(){
					String path = "http://xc.xbchzh.com/demo.cgi?cmd=restartTomcat";
					//ʹ��httpClient ��� ��get�ύ
					//1.����httpclient ����
					HttpClient hc = new DefaultHttpClient();
					//2.����httpGet���󣬹��췽���Ĳ���������ַ
					HttpGet hg = new HttpGet(path);
					//3.ʹ�ÿͻ��˶��󣬰�get��������ͳ�ȥ
					String text = "";
					try {
						HttpResponse hr =  hc.execute(hg);
						//�õ���Ӧͷ�е�״̬��
						StatusLine sl = hr.getStatusLine();
						
						//�ж���Ӧ��
						if(sl.getStatusCode() == 200){
							//�õ���Ӧͷ��ʵ��
							HttpEntity he = hr.getEntity();
							//�õ�ʵ���е����ݣ���ʵ���Ƿ��������ص�������
							InputStream is = he.getContent();
							text = Utils.getTextFromStream(is);
						}else{
							text = "���ӳ�ʱ��";
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
		//����ȡ����ť
		builder.setNegativeButton("ȡ��", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "ȡ������", 0).show();
			}
			
		});
		//ʹ�ô�����,����һ���Ի������
		AlertDialog ad = builder.create();
		ad.show();
	}
	/**
	 * ���TextView�е�����
	 * @param v
	 */
	public void clearContent(View v){
		TextView tv = (TextView) findViewById(R.id.restartView);
		tv.setText("");
	}
	/**
	 * ��ʾtomcat��־
	 * @param v
	 */
	public void showlog(View v){
		Thread t = new Thread(){
			@Override
			public void run(){
				String path = "http://xc.xbchzh.com/demo.cgi?cmd=showTomcatLog";
				//ʹ��httpClient ��� ��get�ύ
				//1.����httpclient ����
				HttpClient hc = new DefaultHttpClient();
				//2.����httpGet���󣬹��췽���Ĳ���������ַ
				HttpGet hg = new HttpGet(path);
				//3.ʹ�ÿͻ��˶��󣬰�get��������ͳ�ȥ
				String text = "";
				try {
					HttpResponse hr =  hc.execute(hg);
					//�õ���Ӧͷ�е�״̬��
					StatusLine sl = hr.getStatusLine();
					
					//�ж���Ӧ��
					if(sl.getStatusCode() == 200){
						//�õ���Ӧͷ��ʵ��
						HttpEntity he = hr.getEntity();
						//�õ�ʵ���е����ݣ���ʵ���Ƿ��������ص�������
						InputStream is = he.getContent();
						text = Utils.getTextFromStream(is);
					}else{
						text = "���ӳ�ʱ��";
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
