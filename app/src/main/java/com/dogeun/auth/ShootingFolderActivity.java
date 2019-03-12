package com.dogeun.auth;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShootingFolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_shooting_folder);

        Button load_button = (Button) findViewById(R.id.fc_button);
        load_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(ShootingFolderActivity.this, ShootingFCActivity.class);
                        startActivity(intent);
                    }
                });

        Button shooting_button = (Button) findViewById(R.id.fs_button);
        shooting_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(ShootingFolderActivity.this, ShootingFSActivity.class);
                        startActivity(intent);
                    }
                });
    }
}