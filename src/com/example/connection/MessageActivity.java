package com.example.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MessageActivity extends Activity{

	
	EditText _message;
	Button _send;
	
	
	@Override

	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.message);
	
	_message = (EditText) findViewById(R.id.message_text);
	_send = (Button) findViewById(R.id.send);
	Log.i("Message Ceated", "Error");
}
	
	public void sendClicked(View v) {
		// TODO Auto-generated method stub
		
		connectionClicked();
	}

	private void connectionClicked() {
		// TODO Auto-generated method stub
		String text = _message.getText().toString();
		try {
        	DefaultHttpClient client = new DefaultHttpClient();
        	
        	Log.i(getClass().getName(), "One is complete");
        	HttpPost post = new HttpPost("http://192.168.1.100:3000/display");
        /*	post.setHeader("Accept", "application/json");
        	post.setHeader("Content-Type", "application/json"); */
        	
        	 final List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
        	nvp.add(new BasicNameValuePair("query", text));
        	//nvp.add(new BasicNameValuePair("passWord", pass.getText().toString()));
        	final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp);
        	post.setEntity(entity); 
        	
        	Log.i("Psot", "Two is fine");
        //	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.getText().toString()+ ":" + pass.getText().toString());
        //	client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY), credentials);
        //	post.setHeader("Authorization", "Basic" + Base64.encodeToString("shah:shah".getBytes(), Base64.NO_WRAP));
        	
        	HttpResponse reponse = client.execute(post);
        	BufferedReader br = new BufferedReader(new InputStreamReader(reponse.getEntity().getContent()));
        	
        	
        	//HttpEntity content = reponse.getEntity();
        	Log.i("REsponse", "Third is working");
        	
        	
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
	}
	
}
