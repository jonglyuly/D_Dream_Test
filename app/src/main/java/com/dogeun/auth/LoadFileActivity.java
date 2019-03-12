package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class LoadFileActivity extends AppCompatActivity {

    //TTS 객체 선언
    TTSModule ttsModule;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private List<String> mList = new ArrayList<String>();

    private Button right_button, left_button, select_button;
    int mCount=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_load_file);

        //TTS 객체 초기화
        ttsModule = new TTSModule(LoadFileActivity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = mFirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        right_button = (Button) findViewById(R.id.right);
        left_button = (Button) findViewById(R.id.left);
        select_button = (Button) findViewById(R.id.select);

        Intent intent = getIntent();
        final String folder_name = intent.getExtras().getString("folder_name");

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        mFirebaseDatabase.getReference("Book/" + mFirebaseUser.getDisplayName())
                .child(folder_name)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        adapter.add(dataSnapshot.getKey());
                        mList.add((String) dataSnapshot.getValue());
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

        right_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount == adapter.getCount()-1){
                    mCount = 0;
                }
                else{
                    mCount = mCount+1;
                }
                ttsModule.ttsSpeech(adapter.getItem(mCount)+" 페이지");
                return;
            }
        });

        left_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount <= 0){
                    mCount = adapter.getCount()-1;
                }
                else{
                    mCount = mCount -1;
                }
                ttsModule.ttsSpeech(adapter.getItem(mCount)+" 페이지");
                return;
            }
        });

        select_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadFileActivity.this, LoadTextActivity.class);
                intent.putExtra("text",mList.get(mCount));
                ttsModule.ttsSpeech(adapter.getItem(mCount)+" 페이지가 선택되었습니다.");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsModule.ttsClose();
    }

}