package com.example.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClientActivity extends Activity {
	
	Button btn ;
	EditText txtLogin;
	EditText txtPassword;
	TextView rand;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn = (Button) findViewById(R.id.conn);
        txtLogin = (EditText) findViewById(R.id.editText2);
        txtPassword = (EditText) findViewById(R.id.editText1);
        rand = (TextView) findViewById(R.id.textView1);
        
    }
    
    public void connClicked(View v) {
		// TODO Auto-generated method stub
    	connection();
    	//startActivity(new Intent(ClientActivity.this, MessageActivity.class));
	}
    
    public void connection()
    {
    	String username = txtLogin.getText().toString();
		Log.d("Username : ", "" + username);
		String password = txtPassword.getText().toString();
		Log.d("Password : ", password);
		
    	try {
        	DefaultHttpClient client = new DefaultHttpClient();
        	
        	Log.i(getClass().getName(), "One is complete");
        	HttpPost post = new HttpPost("http://192.168.100.105:3000/authenticate");
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
        		
        		json.put("username", username);
        		json.put("password", password);
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
        	//HttpEntity content = reponse.getEntity();
        	Log.i("REsponse", "Third is working");
        	rand.setText(result.toString());
        	
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
        	//startActivity(new Intent(ClientActivity.this, MessageActivity.class));
    }
    
  /*  private void onClick() {
		// TODO Auto-generated method stub
    	try {
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost("192.168.2.103:3000/authenticate");
    	client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user.getText().toString()	, pass.getText().toString()));
    	HttpResponse reponse = client.execute(post);
    	rand.setText(reponse.toString());
    	
    	}
    	catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}

	} */
}