package com.artiqk.smartph0ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PowerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button powerBtn;
    private final String POWER_TAG = "power";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        powerBtn = (Button) findViewById(R.id.msgLaunchButton);
        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                if(ConnectionActivity.isClientConnected) {
                    Thread t = new Thread(new ConnectionActivity.MayTheHackBegin(ConnectionActivity.client, POWER_TAG));
                    t.start();
                }
            }
        });
    }

    public void playBeep() {
        mediaPlayer = (MediaPlayer) MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.start();
    }
}
