package com.yawar;

import android.speech.tts.TextToSpeech;

public class TTS implements TextToSpeech.OnInitListener{

	private TextToSpeech tts;
	private static final int Data_Code = 1234;
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		tts.speak("Welcome to My new TTS demo!", TextToSpeech.QUEUE_FLUSH, null);
	}
	
	TTS(TextToSpeech mtts)
	{
		tts = mtts;
		say(Sentences.welcome);
	}

	public void say(String sent)
	{
		tts.speak(sent, TextToSpeech.QUEUE_FLUSH, null);
	}

}
