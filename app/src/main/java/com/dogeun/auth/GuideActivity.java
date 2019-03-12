package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_guide);

        Button guide_button = (Button) findViewById(R.id.notice_button);
        guide_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActivity.this, NoticeActivity.class);
                        startActivity(intent);
                    }
                });

        Button general_capture_button = (Button) findViewById(R.id.gc_guide_button);
        general_capture_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActivity.this, GeneralCaptureGuideActivity.class);
                        startActivity(intent);
                    }
                });

        Button library_button = (Button) findViewById(R.id.library_guide_button);
        library_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActivity.this, LibraryGuideActivity.class);
                        startActivity(intent);
                    }
                });
    }
}