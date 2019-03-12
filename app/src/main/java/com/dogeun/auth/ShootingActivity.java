package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ShootingActivity extends AppCompatActivity {

    private Button insert_button;
    private EditText Edit_Text;
    private List<String> KeyList = new ArrayList<String>();

    String text;
    int Key;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_shooting);

        mFirebaseAuth = mFirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        insert_button = (Button) findViewById(R.id.insert_button);
        Edit_Text = (EditText) findViewById(R.id.edit_Text);

        Intent intent = getIntent();

        final String folder_name = intent.getExtras().getString("folder_name");

        mFirebaseDatabase.getReference("Book/" + mFirebaseUser.getDisplayName())
                .child(folder_name)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        KeyList.add((String) dataSnapshot.getKey());
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

                        insert_button.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String Text1 = Edit_Text.getText().toString();
                                if(Text1.isEmpty()){
                                    return;
                                }
                                Key = KeyList.size()+1;
                                text = Edit_Text.getText().toString();

                                mFirebaseDatabase.getReference("Book/"+mFirebaseUser.getDisplayName())
                                        .child(folder_name)
                                        .child(String.valueOf(Key))
                                        .setValue(text)
                                        .addOnSuccessListener(ShootingActivity.this, new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(Edit_Text,Key +"번 파일에 저장 되었습니다.", Snackbar.LENGTH_LONG).show();
                                                Edit_Text.setText("");
                                            }
                                        });
                            }
                        });
    }
}