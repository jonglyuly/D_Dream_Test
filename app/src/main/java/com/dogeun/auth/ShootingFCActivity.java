package com.dogeun.auth;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ShootingFCActivity extends AppCompatActivity {

    private Button Folder_name_button;
    private TextView Folder_name;
    String folder_name;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;

    TTSModule ttsModule;
    private static final int RESULT_SPEECH = 1;
    private Intent i;
    private ImageButton SST_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_shooting_fc);

        ttsModule = new TTSModule(ShootingFCActivity.this);

        mFirebaseAuth = mFirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        Folder_name_button = (Button) findViewById(R.id.folder_name_button);
        Folder_name = (TextView) findViewById(R.id.folder_name);
        SST_button = (ImageButton) findViewById(R.id.button);

        Folder_name_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                folder_name = Folder_name.getText().toString();

                mFirebaseDatabase.getReference("Book/" + mFirebaseUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> name = dataSnapshot.getChildren().iterator();
                        while(name.hasNext())
                        {
                            if(name.next().getKey().equals(folder_name))
                            {
                                Toast.makeText(getApplicationContext(),"이미 존재하는 폴더 이름입니다.",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        Intent intent = new Intent(ShootingFCActivity.this, ShootingActivity.class);
                        intent.putExtra("folder_name", folder_name);
                        Folder_name.setText("");
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        /* 버튼에 대한 클릭 리스너 등록 부분*/
        SST_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button) {
                    /* Intent 부분*/
                    i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // Intent 생성
                    i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // 호출한 패키지
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 인식할 언어를 설정한다.
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해주세요"); // 유저에게 보여줄 문자

                    try {
                        startActivityForResult(i, RESULT_SPEECH);
                    }catch(ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(),"Speech To Text를 지원하지 않습니다.",Toast.LENGTH_SHORT).show();
                        e.getStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && (requestCode == RESULT_SPEECH)) {

            /* data.getString...() 호출로 음성 인식 결과를 ArrayList로 받는다. */
            ArrayList<String> sstResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            /* 결과들 중 음성과 가장 유사한 단어부터 시작되는 0번째 문자열을 저장한다.*/
            String result_sst = sstResult.get(0);

            Folder_name.setText("" + result_sst); // 텍스트 뷰에 보여준다.
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //지연시키길 원하는 밀리초 뒤에 동작
                ttsModule.ttsSetup();
                ttsModule.ttsSpeech(Folder_name.getText().toString());
            }
        }, 500 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsModule.ttsClose();
    }

}

