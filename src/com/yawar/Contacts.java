package com.yawar;

public class Contacts {

	public static final String CONSUMER_KEY 	= "679797626486.apps.googleusercontent.com";
	public static final String CONSUMER_SECRET 	= "O-ynb4nQyAEu2wybFfJCLsgT";

	public static final String SCOPE 			= "https://www.google.com/m8/feeds/";
	public static final String REQUEST_URL 		= "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL 		= "https://www.google.com/accounts/OAuthGetAccessToken";  
	public static final String AUTHORIZE_URL 	= "https://www.google.com/accounts/OAuthAuthorizeToken";
	
	public static final String API_REQUEST 		= "https://www.google.com/m8/feeds/contacts/default/full?alt=json";
	
	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	
	
}
