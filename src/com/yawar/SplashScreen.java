package com.yawar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends Activity {
	
	boolean auth = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlayout);
        
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        
        Thread splash = new Thread() {
        	@Override
        	public void run() {
        		try 
        		{
        			sleep(2000);
        			Intent intent = new Intent(SplashScreen.this, Login_In.class);
        			startActivity(intent);
        		}
        		catch (InterruptedException e)
        		{
        			e.printStackTrace();
        		}
        		finally
        		{
        			finish();
        			
        		}
        	}
        };
        splash.start();
        
    }
    
    public void imageClicked(View v)
    {
    	auth = true;
    	Editor edit = getSharedPreferences("auth", 0).edit();
    	edit.putBoolean("auth", auth);
    	edit.commit();	
    }
  
    
}
