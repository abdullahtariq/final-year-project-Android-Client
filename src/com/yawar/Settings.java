package com.yawar;

import java.sql.SQLData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends Activity{
 
	Spinner spinner;
	EditText newPassword;
	EditText confirmPassword;
	Button save;
	String question_asked;
	String answer;
	EditText answerText;
	Pattern pattern;
	private Matcher matcher;
	private static final String Password_Pattern = 
		"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";
	private static String password;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("In Settings", "Good Work");
		setContentView(R.layout.settings);
		
		spinner = (Spinner) findViewById(R.id.questionslist);
		ArrayAdapter array = ArrayAdapter.createFromResource(this, R.array.questions, android.R.layout.simple_spinner_item);
		array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(array);
		Log.i("In Second stage", "Good Work");
		newPassword = (EditText) findViewById(R.id.editText1);
		confirmPassword = (EditText) findViewById(R.id.editText2);
		save = (Button) findViewById(R.id.save_button);
		answerText = (EditText) findViewById(R.id.set_answer);
		pattern = Pattern.compile(Password_Pattern);
		Log.i("In Third stage", "Good Work");
		getlayout();
	}
	
	private void getlayout() {
		SharedPreferences prefs = getSharedPreferences("password", 0);
		String password = prefs.getString("password", " ");
		boolean cond = password.equals(" ");
		if(!cond)
		{
			startActivity(new Intent(Settings.this, Login.class));
			Settings.this.finish();
		}
	}

	public void saveClicked(View v)
	{
		question_asked = spinner.getSelectedItem().toString();
		Log.i("Question: ", question_asked);
		setQuestion(question_asked);
		answer = answerText.getText().toString();
		setAnswer(answer);
		String newpass = newPassword.getText().toString();
		boolean value = validate(newpass); 
		if(value)
		{
			Log.i("Value : ", "True");
			String confirm = confirmPassword.getText().toString();
			if(newpass.equals(confirm))
			{
				setPassword(newpass);
				startActivity(new Intent(Settings.this, Main.class));
				Settings.this.finish();
				Toast.makeText(this, "Password Set!!", Toast.LENGTH_LONG);
			}
			else
			{
				Toast.makeText(this, "Password is wrong. It should have a digit and letter > 6", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	
	public boolean validate(final String password_text)
	{
		matcher = pattern.matcher(password_text);
		return matcher.matches();
	}
	
	public void setPassword(String password)
	{
		/*Database dbHelper = new Database(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Database.C_ID, 1);
		values.put(Database.C_Password, password);
		db.insertWithOnConflict(dbHelper.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		dbHelper.close();
		db.close(); */
	//	this.password = password;
		Editor editor = getSharedPreferences("password", 0).edit();
		editor.putString("password", password);
		editor.commit();
	}
	
	public void setQuestion(String question)
	{
		Editor editor = getSharedPreferences("questionAsked", 0).edit();
		editor.putString("questionAsked", question);
		editor.commit();
	}
	
	public void setAnswer(String answer)
	{
		Editor editor = getSharedPreferences("answer", 0).edit();
		editor.putString("answer", answer);
		editor.commit();
	}
}
