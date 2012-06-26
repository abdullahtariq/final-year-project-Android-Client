package com.yawar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {
	
	Settings object;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlayout);
        
        object = new Settings();
        
        Thread splash = new Thread() {
        	@Override
        	public void run() {
        		try 
        		{
        			sleep(2000);
        			Intent intent = new Intent(SplashScreen.this, Settings.class);
        			startActivity(intent);
        		}
        		catch (InterruptedException e)
        		{
        			e.printStackTrace();
        		}
        		finally
        		{
        			finish();
        			stop();
        		}
        	}
        };
        splash.start();
        
    }
    

  
    
}
