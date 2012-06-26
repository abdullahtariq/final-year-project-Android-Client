package com.yawar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Reset_password extends Activity{

	Spinner spinner;
	EditText answer;
	EditText old_password;
	Button set;
	TextView question;
	String given_answer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_pass);
		
		Log.i("First Tag:", "LAyout is set");
		answer= (EditText) findViewById(R.id.answer);
		old_password = (EditText) findViewById(R.id.old_password_values);
		set = (Button) findViewById(R.id.Set_new_password);
		question = (TextView) findViewById(R.id.Retrievedquestion);
		Log.i("Second Tag:", "Widget is set");
		SharedPreferences prefs1 = getSharedPreferences("answer", 0);
		given_answer = prefs1.getString("answer", "");
		Log.i("Third Tag:", "Answer is set");
		setQuestion();
		
	}
	
	public void setQuestion()
	{
		SharedPreferences prefs = getSharedPreferences("questionAsked", 0);
		String returnText = prefs.getString("questionAsked", " ");
		Log.i("Four Tag:", returnText);
		question.setText((CharSequence)returnText);
		
	}
	
	public void setClicked(View v)
	{
		SharedPreferences prefs = getSharedPreferences("password", 0);
		String letters = prefs.getString("password", " ");
		String text = answer.getText().toString();
		if(given_answer.equals(text))
		{
			StringBuilder sb = new StringBuilder(letters);
			for(int i=0 ; i< sb.length(); i++)
			{
				
			}
		}
		Log.i("Set Button:  ", "Clicked");	
		startActivity(new Intent(Reset_password.this, Set_Password.class));
	}
	
	
}
