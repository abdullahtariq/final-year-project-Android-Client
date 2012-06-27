package com.yawar;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class PrepareReuqestTokenAcitivty extends Activity{

	private static final String Tag = "Oauth";
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			System.setProperty("debug", "true");
			this.consumer = new CommonsHttpOAuthConsumer(Contacts.CONSUMER_KEY, Contacts.CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(
			Contacts.REQUEST_URL + "?scope=" + URLEncoder.encode(Contacts.SCOPE, Contacts.ENCODING), Contacts.ACCESS_URL, Contacts.AUTHORIZE_URL);
		}
		catch(Exception ex)
		{
			Log.d(Tag, "Error crreating consumer / provider", ex);
		}
		Log.i(Tag, "Starting task to retrieve token");
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if(uri != null && uri.getScheme().equals(Contacts.OAUTH_CALLBACK_SCHEME))
		{
			Log.i(Tag, "Callback recieved: " + uri);
			Log.i(Tag, "Retrieving access token");
			new RetrieveAccessTokenTask(this, consumer, provider, prefs).execute(uri);
			finish();
		}
	}
	
	
}
