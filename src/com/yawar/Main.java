package com.yawar;

import java.util.ArrayList;
import java.util.List;

import com.yawar.MainOAuth;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Main extends Activity implements RecognitionListener, OnInitListener{
    
	private static final String tag = "Voice";
	private TextView et;
	private Button click;
	private Button edit;
	private Button done;
	private Button send;
	private EditText editText;
	private SpeechRecognizer recognizer;
	private TextToSpeech mtts;
	private AudioManager am;
	private TTS tts;
	private String JsonText;
	private String last_Sentence;
	private String _command;
	private String _message;
	private String _attachment;
	private String _selected;
	private String command_type;
	private MainOAuth mainAuth;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        mainAuth = new MainOAuth();
        click = (Button) findViewById(R.id.speak);
        et = (TextView) findViewById(R.id.Heard);
        edit = (Button) findViewById(R.id.editButton);
        editText = (EditText) findViewById(R.id.modify);
        done = (Button) findViewById(R.id.done);
        send = (Button) findViewById(R.id.send_button);
        mtts = new TextToSpeech(this, this);
        tts = new TTS(mtts);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.setSpeakerphoneOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        tts.say(Sentences.welcome);
        
        
        editText.setCursorVisible(false);
        InputMethodManager img = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        img.hideSoftInputFromInputMethod(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        
       
        /* Check for the Recognizer */
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if(activities.size() == 0)
        {
        	click.setEnabled(false);
        	Toast ts = Toast.makeText(this, "Recognizer is not present", Toast.LENGTH_LONG);
        	ts.setGravity(Gravity.CENTER, 0, 100);
        	ts.show();
        }
  
    }
    
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
    	// TODO Auto-generated method stub
    	startActivity(new Intent(this, Preference.class));
    	return true;
    }
    
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_list, menu);
    	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId())
    	{
    	case R.id.OAuthRegister:
    		startActivity(new Intent(this, MainOAuth.class));
    		break;
    	case R.id.Voice:
    		mainAuth.callPrivavte();
    		break;
    	}
    		
    	return super.onMenuItemSelected(featureId, item);
    }
   */ 
   
    /* Event Handler when the Speak button is clicked */
    public void speakClicked(View v)
    {
    	startVoiceRexognitionActivity();
    }

    private void startVoiceRexognitionActivity() {
    	Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    	recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	recognizerDirectly(recognizerIntent);
	}

	/* Intent for Speech recognition */
	private void recognizerDirectly(Intent recognizerIntent) {
		if(!recognizerIntent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE))
		{
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.yawar");
		}
		recognizer = getSpeechRecognizer();
		recognizer.startListening(recognizerIntent);
	}

	/* Not to Show the Default dialogue for speech recognition*/
	private SpeechRecognizer getSpeechRecognizer() {
		if(recognizer == null)
		{
			recognizer = SpeechRecognizer.createSpeechRecognizer(this);
			recognizer.setRecognitionListener(this);
		}
		return recognizer;
	}

	
	@Override
	public void onError(int error) {
		recongintionFailure(error);
	}

	/* If recognition failure occurs */
	private void recongintionFailure(int error) {
		Toast ts = Toast.makeText(this, "Error Occured While Recording", Toast.LENGTH_LONG);
		ts.show();
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		Log.d(tag, "on Partial Results");
		receiveResult(partialResults);
	}

	/* The result is received in this method */
	@Override
	public void onResults(Bundle results) {
		Log.d(tag, "on Result");
		receiveResult(results);
	
	}

	/* The onResult calls this method to manipulate with the result */
	private void receiveResult(Bundle results) {
		if((results != null) && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION));
		{
			Log.d(tag, "Working");
			ArrayList<String> heard = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			receiveWhatWasHeard(heard);
			
		}
	}

	private void receiveWhatWasHeard(ArrayList<String> heard) {
		String text = null;
		String words = et.getText().toString();
		
		text = heard.get(0);
	//	searchString(text);
		
		if(words.equals("TextView"))
		{
			_command =text +"\n"  ;
			last_Sentence =  "TO : " + _command;
			et.setText(last_Sentence);
			tts.say(Sentences.msg_resp);
			startVoiceRexognitionActivity();
		}
		else if(words.equals(last_Sentence))
		{
			_message = text  + "\n";
			JsonText = words + "\n" + "Message : " + _message;
			et.setText(JsonText);
			tts.say(Sentences.att_resp);
			startVoiceRexognitionActivity(); 
		}  
		
			
		else if(words.equals(JsonText))
		{
			tts.say("Ok ! What do you want to attach?");
			last_Sentence = words + "\n" + "attachment : " + text;
			_attachment = text + "\n" ;
			et.setText(last_Sentence);
			tts.say(Sentences.thanks);
		} 
		
	}
		
	/*public void searchString(String text)
	{
		String _text  = text;
		int index = _text.indexOf("mail");
		if(index == -1)
		{
			index = text.indexOf("copy");
			if(index != -1)
			{
				command_type = "copy";
				Toast.makeText(this, "Found copy", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			command_type = "mail";		
		}
	} */
	
	public void onPause(Bundle results)
	{
		if(getSpeechRecognizer() == null)
		{
			getSpeechRecognizer().stopListening();
			getSpeechRecognizer().cancel();
			getSpeechRecognizer().destroy();
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy()
	{
		if(recognizer != null)
		{
			recognizer.stopListening();
			recognizer.cancel();
			recognizer.destroy();
		}
		if(mtts != null)
		{
			mtts.stop();
			mtts.shutdown();
		}
		super.onDestroy();
	}
 
//*************************** EDIT BUTTON DIALOG *********************//
	
	/* Event Handler when the Edit button is clicked */
	//EDIT BUTTON
	public void editClicked(View v)
	{
		/* String text = et.getText().toString();
		editText.setText(text); */
		AlertDialog alert = AlertDialogBox();
		alert.show();
		editText.setCursorVisible(true);
		send.setEnabled(false);
	}
	
	/* AlertDialog box for the Choice to make while selecting the right one to editing  */
	private AlertDialog AlertDialogBox()
	{
		final CharSequence[] items = {"Command" , "Message" , "Attachment"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(items, -1, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(items[which].equals("Command"))
				{
					editText.setText(_command);
					_selected = "Command";
					dialog.dismiss();
				}
				else if(items[which].equals("Message"))
				{
					editText.setText(_message);
					_selected = "Message";
					dialog.dismiss();
				}
				else if(items[which].equals("Attachment"))
				{
					editText.setText(_attachment);
					_selected = "Attachment";
					dialog.dismiss();
				}
			}
		});
		AlertDialog alert = builder.create();
		return alert;
	}
	
//********************************************************************//	
	
	
//*************************** DONE BUTTON DIALOG *********************//
	
	/* Event Handler when the Done button is clicked */
	public void doneClicked(View v)
	{
		String text = editText.getText().toString();
		editText.clearFocus();
		if(_selected.equals("Command"))
		{
			et.setText("To : " + text + "\n" + "message : " + _message + "\n" +
					"attachment : "	+ _attachment);	
		}
		else if(_selected.equals("Message"))
		{
			et.setText("To : " + _command + "\n" + "message : " + text + "\n" +
					"attachment : "	+ _attachment);	
		}
		else if(_selected.equals("Attachment"))
		{
			et.setText("To : " + _command + "\n" + "message : " + _message + "\n" +
					"attachment : "	+ text);	
		}
		editText.setText(" ");
		send.setEnabled(true);
	}
	
//********************************************************************//	

	
//*************************** SEND BUTTON DIALOG *********************//
	
	//Send the message to the server when Send button is Clicked
	public void onSendClicked(View v)
	{
		
	}
//********************************************************************//	
	
	
	
//************************** EXIT CONTROL DISLOAG ********************//
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			try
			{
				AlertDialog back_Alert = backAlertdialog();
				back_Alert.show();
				return super.onKeyDown(keyCode, event);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private AlertDialog backAlertdialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?");
    	builder.setCancelable(false);
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           @Override
				public void onClick(DialogInterface dialog, int id) {
    	                Main.this.finish();
    	           }
    	       });
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           @Override
				public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	return alert;
	}

//*********************************************************************//
	
	
	
	
	
	@Override
	public void onInit(int status) {
		
		
	}
	
	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		
	}
	
	
}



