package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class LoadTextActivity extends AppCompatActivity {

    //TTS 객체 선언
    TTSModule ttsModule;

    String text;
    TextView loadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_load_text);
        //TTS 객체 초기화
        ttsModule = new TTSModule(LoadTextActivity.this);

        loadText = (TextView) findViewById(R.id.loadText);
        loadText.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();

        text = intent.getExtras().getString("text");
        loadText.setText(text);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //지연시키길 원하는 밀리초 뒤에 동작
                ttsModule.ttsSetup();
                ttsModule.ttsSpeech(loadText.getText().toString());
            }
        }, 150 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsModule.ttsClose();
    }
}
