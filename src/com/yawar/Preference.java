package com.yawar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		android.preference.Preference prefs = findPreference("OAuth");
		prefs.setIntent(new Intent(getApplicationContext(), MainOAuth.class));
	}
	
	
	
}
