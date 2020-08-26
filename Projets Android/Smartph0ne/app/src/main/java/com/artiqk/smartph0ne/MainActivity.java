package com.artiqk.smartph0ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.connectBtn);
        startDaemon();
    }

    public void playBeep() {
        mediaPlayer = (MediaPlayer) MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.start();
    }

    public void startDaemon() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                openConnectionActivity();
            }
        });
    }

    public void openConnectionActivity() {
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivity(intent);
    }
}