package com.dogeun.auth;

import android.app.ActionBar;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;


public class LibraryGuideActivity extends AppCompatActivity {

    //TTS 객체 선언
    TTSModule ttsModule;

    private FirebaseDatabase mFirebaseDatabase;
    private TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_library_guide);

        //TTS 객체 초기화
        ttsModule = new TTSModule(LibraryGuideActivity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTextview = (TextView) findViewById(R.id.textView);

        mFirebaseDatabase.getReference("Guide/" + "개인 도서관")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String library = dataSnapshot.getValue(String.class);
                        mTextview.setText(library);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //지연시키길 원하는 밀리초 뒤에 동작
                ttsModule.ttsSetup();
                ttsModule.ttsSpeech(mTextview.getText().toString());
            }
        }, 500 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsModule.ttsClose();
    }
}
