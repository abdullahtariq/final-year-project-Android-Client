package com.yawar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Set_Password extends Activity{

	EditText new_password;
	EditText confirm_password;
	Pattern pattern;
	private Matcher matcher;
	private static final String Password_Pattern = 
		"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";
	private static String password="";
	Settings pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_password);
		
		new_password = (EditText) findViewById(R.id.new_password_text);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		
		pattern = Pattern.compile(Password_Pattern);
		pass = new Settings();
	
	}
	
	public void setClicked(View v) {
		// TODO Auto-generated method stub
		
		String newpass = new_password.getText().toString();
		boolean value = validate(newpass); 
		Log.i("Clicked", "In SEtting:");
		if(value)
		{
			String confirm = confirm_password.getText().toString();
			if(newpass.equals(confirm))
			{
				pass.setPassword(newpass);
				startActivity(new Intent(Set_Password.this, Main.class));
				Set_Password.this.finish();
				Toast.makeText(this, "Password Set!!", Toast.LENGTH_LONG);
			}
		}
			
	}
	
	public boolean validate(final String password_text)
	{
		matcher = pattern.matcher(password_text);
		return matcher.matches();
	}
	
	
	
	
}
