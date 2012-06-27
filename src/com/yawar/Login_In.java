package com.yawar;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_In  extends Activity{

	Button login;
	EditText username;
	EditText password;
	private String imei;
	private ProgressDialog progressbar;
	private boolean auth;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_in);
		login = (Button) findViewById(R.id.login_button);
		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tm.getDeviceId().toString();
	//	Log.d("Drive Id : ", imei);
		prefs = getSharedPreferences("auth", 0);
		auth = prefs.getBoolean("auth", false);
		Toast.makeText(this, "" + auth, Toast.LENGTH_SHORT).show();
	}
	
	public void onLoginClicked(View v)
	{
		connection();
	}
	
	public void connection()
	{
		progressbar = ProgressDialog.show(Login_In.this, " " , "Processing...");
		new Thread(){
			public void run()
			{
				try
				{
					String id = username.getText().toString();
					Log.d("Username : ", "" + id);
					String pass = password.getText().toString();
					Log.d("Password : ", "" + pass);
					String updated = "";
			        	DefaultHttpClient client = new DefaultHttpClient();
			        	
			        	Log.i(getClass().getName(), "One is complete");
			        	HttpPost post = new HttpPost("http://192.168.100.106:3000/authenticate");
			        //	post.setHeader("Accept", "application/text");
			        	post.setHeader("Accept", "text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			        //	post.setHeader("Content-Type", "application/text"); 
			        	
			        /*    final List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
			        	nvp.add(new BasicNameValuePair("username", username));
			        	nvp.add(new BasicNameValuePair("password", password));
			        	final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp);
			        	post.setEntity(entity);  
			        	*/
			        	Log.i("Post", "Two is fine"); 
			        	post.setHeader("Content-type", "application/json");
			        	HttpResponse reponse;
			        	JSONObject json = new JSONObject();
			        	try
			        	{
			        		
			        		json.put("txtLogin", id);
			        		json.put("txtPassword", pass);
			        		json.put("imeiCode", imei);
			        		StringEntity se = new StringEntity(json.toString());
			        		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE	, "application/json"));
			        		post.setEntity(se);
			        	}
			        	catch(Exception ex)
			        	{
			        		ex.printStackTrace();
			        	} 
			        	
			        	reponse = client.execute(post);
			        	BufferedReader br = new BufferedReader(new InputStreamReader(reponse.getEntity().getContent()));
			        	StringBuffer sb = new StringBuffer("");
			        	String line = "";
			        	while((line = br.readLine()) != null)
			        	{
			        		sb.append(line);
			        	}
			        	String result = sb.toString();
			        	Log.i("REsponse", "Third is working");
			        	updated = result.replace("\"", "");
			        	Log.i("REsponse", "" + updated);
			        	Editor edit = getSharedPreferences("uniqueCode", 0).edit();
			        	edit.putString("uniqueCode", updated);
			        	edit.commit();
			        	
			        	if(!updated.equals(""))
			        	{
			        		startActivity(new Intent(Login_In.this, Main.class));
			        		Editor remover = getSharedPreferences("auth", 0).edit();
			        		remover.remove("auth");
			        		remover.commit();
			        		Login_In.this.finish();
			        	}
			        
				}catch(Exception ex)
	        	{
	        		ex.printStackTrace();
	        	}
        	
				
			
			progressbar.dismiss();
			}
	}.start();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			try
			{
				Editor remover = getSharedPreferences("auth", 0).edit();
        		remover.remove("auth");
        		remover.commit();
				Login_In.this.finish();
				return super.onKeyDown(keyCode, event);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
