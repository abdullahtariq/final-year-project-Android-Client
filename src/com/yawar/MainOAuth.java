package com.yawar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.PeopleColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainOAuth extends Activity {
	
	final String Tag = getClass().getName();
	private static final int PICK_CONTACT = 0;
	private SharedPreferences prefs;
	List<String> data;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        data = new ArrayList<String>();
        
        
      /*  launchOauth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(v.getContext(), PrepareReuqestTokenAcitivty.class));
			}
		});
        
        clearCredentials.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	clearCredentials();
            	performApiCall();
            }
        }); */
        startActivity(new Intent(this, PrepareReuqestTokenAcitivty.class));
        
        
    }
    
    public void callPrivavte()
    {
    	performApiCall();
    	AlertDialog account_list = createAndShow();
    	account_list.show();
    }
    
    
    private void performApiCall() {
    //	TextView tv = (TextView) findViewById(R.id.textView1);
    //	tv.setMovementMethod(new ScrollingMovementMethod());
    	String jsonOutput = "";
    	try
    	{
    		jsonOutput = doGet(Contacts.API_REQUEST, getConsumer(this.prefs) );
    		JSONObject jsonResponse = new JSONObject(jsonOutput);
        	JSONObject m = (JSONObject)jsonResponse.get("feed");
    		Log.d("Json", "Response error");
        	JSONArray entries =m.getJSONArray("entry");
        	String contacts="";
        	for (int i=0 ; i<entries.length() ; i++) {
        		JSONObject entry = entries.getJSONObject(i);
        		JSONObject title = entry.getJSONObject("title");
        		if (title.getString("$t")!=null && title.getString("$t").length()>0
        				&& title.get("$t") != null) {
        		//	contacts+=title.getString("$t") + "\n";
        			data.add(title.getString("$t"));
        		}
        	}
        	Log.i(Tag,jsonOutput);
    //    	tv.setText(contacts);
        	
        	
    	}
    	catch(Exception ex)
    	{
    		Log.e(Tag, "Error executing request",ex);
	//		tv.setText("Error retrieving contacts : " + jsonOutput);
    	}
		
	}
    
    public AlertDialog createAndShow()
    {
    	AlertDialog.Builder alert = new Builder(this);
    	alert.setTitle("Contact Names");
    	
    	ListView namelist = new ListView(this);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, data );
    	namelist.setAdapter(adapter);
    	
    	alert.setView(namelist);
    //	final Dialog dialog = alert.create();
    	AlertDialog retrieve = alert.create();
    	return retrieve;
    }
    
    
    
    @Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		  super.onActivityResult(reqCode, resultCode, data);

		  switch (reqCode) {
		    case (PICK_CONTACT) :
		      if (resultCode == Activity.RESULT_OK) {
		        Uri contactData = data.getData();
		        Cursor c =  managedQuery(contactData, null, null, null, null);
		        if (c.moveToFirst()) {
		          String name = c.getString(c.getColumnIndexOrThrow(PeopleColumns.NAME));
		          Log.i(Tag,"Response : " + "Selected contact : " + name);
		        }
		      }
		      break;
		  }
		}	

    public void clearCredentials() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Editor edit = prefs.edit();
		edit.remove(OAuth.OAUTH_TOKEN);
		edit.remove(OAuth.OAUTH_TOKEN_SECRET);
		edit.commit();
	}
    
    
    
	private OAuthConsumer getConsumer(SharedPreferences prefs)
    {
    	String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
    	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Contacts.CONSUMER_KEY	, Contacts.CONSUMER_SECRET);
    	consumer.setTokenWithSecret(token, secret);
    	return consumer;
    }
    
    private String doGet(String url, OAuthConsumer consumer) throws Exception
    {
    	DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(url);
    	Log.i(Tag, "Requesting Url : " + url);
    	consumer.sign(request);
    	/*HttpResponse response = (HttpResponse) httpclient.execute(request);
    	Log.i(Tag, "Status Line: " + ((org.apache.http.HttpResponse) response).getStatusLine());
    	InputStream data = ((org.apache.http.HttpResponse) response).getEntity().getContent(); */
    	BasicHttpResponse response = (BasicHttpResponse) httpclient.execute(request);
    	Log.i(Tag, "Status Line: " + (response).getStatusLine());
    	InputStream data = (response).getEntity().getContent(); 
    	BufferedReader bufferreader = new BufferedReader(new InputStreamReader(data));
    	String responseline;
    	StringBuilder responsebuilder = new StringBuilder();
    	while((responseline = bufferreader.readLine()) != null)
    	{
    		responsebuilder.append(responseline);
    	}
    	Log.i(Tag, "Response: " + responsebuilder.toString());
		return responsebuilder.toString();	
    }
}
