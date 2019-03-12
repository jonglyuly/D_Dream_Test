package com.dogeun.auth;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSModule {
    TextToSpeech tts;

    public TTSModule (Context mContext) {
        tts=new TextToSpeech(mContext.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    protected void ttsSetup(){
        tts.setPitch(0.85f);
        tts.setSpeechRate(0.85f);
    }

    protected void ttsSpeech(String argtext){
        tts.speak(argtext, TextToSpeech.QUEUE_FLUSH,null,null);
    }

    protected void ttsClose(){
        tts.stop();
        tts.shutdown();
    }
}
