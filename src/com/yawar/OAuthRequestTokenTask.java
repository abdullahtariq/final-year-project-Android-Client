package com.yawar;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class OAuthRequestTokenTask extends AsyncTask<Void, Void	, Void> {

	final String Tag = getClass().getName();
	private OAuthConsumer comsumer;
	private OAuthProvider provider;
	private Context context;
	
	public OAuthRequestTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider) {
			this.context = context;
			this.comsumer = consumer;
			this.provider = provider;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			Log.i(Tag, "Retrieving request token from Google servers");
			final String url = provider.retrieveRequestToken(comsumer, Contacts.OAUTH_CALLBACK_URL);
			Intent intent = new Intent(Intent.ACTION_VIEW, 
					Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY
							| Intent.FLAG_FROM_BACKGROUND);
			context.startActivity(intent);
		}
		catch(Exception ex)
		{
			Log.e(Tag, "Error during OAuth retrieve request token", ex);
		}
		return null;
	}

}
