package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_main);

        Button guide_button = (Button) findViewById(R.id.guide_button);
        guide_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                        startActivity(intent);
                    }
                });

        Button general_capture_button = (Button) findViewById(R.id.general_capture_button);
        general_capture_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GeneralCaptureActivity.class);
                        startActivity(intent);
                    }
                });

        Button library_button = (Button) findViewById(R.id.library_button);
        library_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                        startActivity(intent);
                    }
                });
    }
}