package com.yawar;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>{

	final String Tag = getClass().getName();
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private Context context;
	private SharedPreferences prefs;
	
	public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider, SharedPreferences prefs) 
	{
		this.consumer = consumer;
		this.context = context;
		this.provider = provider;
		this.prefs = prefs;
	}
	
	@Override
	protected Void doInBackground(Uri... params) {
		final Uri uri = params[0];
		Log.i(Tag, "OAuth stage Two");
		final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		
		try{
			provider.retrieveAccessToken(consumer, oauth_verifier);
			final Editor edit = prefs.edit();
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			edit.commit();
			
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			
			consumer.setTokenWithSecret(token, secret);
			context.startActivity(new Intent(context, Preference.class));
			
			Log.i(Tag, "OAuth stage Two OK");
		}
		catch(Exception ex)
		{
			Log.e(Tag, "OAuth stage two ERROR");
		}
		
		return null;
	}

}
