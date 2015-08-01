package com.zihao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class ListUrltask extends AsyncTask<Integer, Integer, String> {
	// ����������ڷֱ��ǲ��������������߳���Ϣʱ�䣩������(publishProgress�õ�)������ֵ ����
	private static String songUri = null;
	private static String stringExtra=null;
	private static TextView tv;
	private static Date app;
	
	
	
	
	public ListUrltask(Date app,String songid) {
		// TODO Auto-generated constructor stub
		stringExtra = songid;
ListUrltask.app=app;
	tv = app.getMusicService1().getLab_taste();
	tv.setText("���ڻ���[ "+stringExtra+" ]");
	}

	

	@Override
	protected void onPreExecute() {
		// ��һ��ִ�з���
		super.onPreExecute();
		

	}

	@Override
	protected String doInBackground(Integer... params) {
		// �ڶ���ִ�з���,onPreExecute()ִ�����ִ��
		try {
			getSongUri(stringExtra);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return songUri;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		// ���������doInBackground����publishProgressʱ��������Ȼ����ʱֻ��һ������
		// ��������ȡ������һ������,����Ҫ��progesss[0]��ȡֵ
		// ��n����������progress[n]��ȡֵ

		super.onProgressUpdate(progress[0]);
	}

	@Override
	public void onPostExecute(String result) {
		// doInBackground����ʱ���������仰˵������doInBackgroundִ����󴥷�
		// �����result��������doInBackgroundִ�к�ķ���ֵ������������"ִ�����"
		super.onPostExecute(result);
		try {
//			app.getMusicService1().backplay(result);
			
		
			app.getMusicService1().play(result);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		app.setMp3info(songUri);
		
		
	}
	

	public static String getSongUri(String songid) throws Exception {
URL url1 = new URL("http://www.songtaste.com/song/" + songid + "/");
//	URL url1 = new URL("http://www.songtaste.com/playmusic.php?song_id=" + songid + "/");
	

		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");
			Pattern p = Pattern.compile("playmedia1((.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?));",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(html);
			while (m.find()) {
				System.out.println("1+"+m.group());
				songUri = m.group(4);
				System.out.println(songUri);
				String regEx = "\"+"; // ��ʾһ������@
				Pattern pat = Pattern.compile(regEx);
				Matcher mat1 = pat.matcher(songUri);
				songUri= mat1.replaceAll("");
			    songUri=songUri.substring(2, songUri.length()-1);
			    Log.i("��һ������ȡ��������", songUri);
		        URI url = new URI("http://www.songtaste.com/time.php");
				HttpPost httpRequest = new HttpPost(url);
				/*
				 * Post�������ͱ���������NameValuePair[]���鴢��
				 */
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("str", songUri));
				params.add(new BasicNameValuePair("sid", songid));
				params.add(new BasicNameValuePair("t", "0"));
				try {
					/* ����HTTP request */
					httpRequest.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					/* ȡ��HTTP response */
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					/* ��״̬��Ϊ200 ok */
					
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* ȡ����Ӧ�ַ��� */
						songUri = EntityUtils
								.toString(httpResponse.getEntity());
						// mTextView1.setText(strResult);
					} else {
						// mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());
					}
				} catch (ClientProtocolException e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				} catch (IOException e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				} catch (Exception e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				}
				

			}
			System.out.println(songUri);
			if (songUri.equals("http://songtaste.com/404.html?3=")) {
				
				
				
					String html2 = html ; 
					System.out.println("�ڶ���ȡ����");
					Pattern p2 = Pattern.compile("x' src=\'http(.*)mp3\'>",
							Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher m2 = p2.matcher(html2);
					while (m2.find()) {
						songUri = m2.group(1);
						songUri = "http"+songUri+"mp3";
						System.out.println(songUri+"�ڶ�������");
						Log.d("tureurl222", songUri);
				
			}}}
				
			Log.d("doinback", "�������û������Ļ�����ֱ��������"+songUri+songid);
			if (songUri.equals("http://songtaste.com/404.html?3=")) {
				Message msg = app.getHandler().obtainMessage();  
				msg.arg1 = R.string.msg_not_network;  
				app.getHandler().sendMessage(msg);  
			}
		return songUri;

		// URI url = new
		// URI("http://huodong.duomi.com/songtaste/?songid="+songid);

		// //
	}

	public static byte[] readStream(InputStream inputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		while ((len = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, len);
		}

		inputStream.close();
		byteArrayOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
	

}
