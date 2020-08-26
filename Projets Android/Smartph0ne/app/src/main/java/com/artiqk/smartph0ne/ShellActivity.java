package com.artiqk.smartph0ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShellActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button shellBtn;
    private final String SHELL_TAG = "shell";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);

        shellBtn = (Button) findViewById(R.id.shellLaunchButton);
        shellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                if(ConnectionActivity.isClientConnected) {
                    Thread t = new Thread(new ConnectionActivity.MayTheHackBegin(ConnectionActivity.client, SHELL_TAG));
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
