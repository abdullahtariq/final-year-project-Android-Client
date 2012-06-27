package com.yawar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{
	
	EditText password;
	Button login;
	private Pattern pattern;
	private Matcher matcher;
	private static final String Password_Pattern = 
		"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";
	Settings pass;
	TextView reset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pass);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login_button);
		reset = (TextView) findViewById(R.id.reset);
		
		Log.i(getLocalClassName(), "In Login");
		pass = new Settings();
		
		
		
		
		
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Login.this, Reset_password.class));
			}
		});
		
	
	}
	
	public void resetClicked(View v)
	{
		
	}
	
	
	public void loginClicked(View v) {
		// TODO Auto-generated method stub
				
		String text =  "" ;
		text = password.getText().toString();
		Log.i("Duzzzz", "In Button");
		
		//pattern = Pattern.compile(Password_Pattern);
		//boolean value = validate(text);
		SharedPreferences prefs = getSharedPreferences("password", 0);
		String value = prefs.getString("password", "");
		if(value.equals(text))
		{
			Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Login.this, Main.class));
			Login.this.finish();
			
		}
		else
		{
			Toast.makeText(this, "Error has Occured", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public boolean validate(final String password_text)
	{
		matcher = pattern.matcher(password_text);
		return matcher.matches();
	}
	
	

		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Login.this.finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
		
}
