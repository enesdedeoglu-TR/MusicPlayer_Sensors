package com.example.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Player extends AppCompatActivity {

    ImageView musicplayer_icon;
    TextView playerTitle;
    ImageButton playerPlayButton;
    ImageButton playerBackButton;
    ImageButton playerNextButton;
    ImageButton shareButton;
    TextView playerTime;
    SeekBar seekBar;
    TextView playerDuration;

    int position;
    int size;
    static MediaPlayer musicPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        defineVariables();
        position = getIntent().getIntExtra("Position", 0);
        size = getIntent().getIntExtra("Size", 0);
        startMusic(position);
        updateSeekBar();

    }

    public void  defineVariables(){
        musicplayer_icon = (ImageView) findViewById(R.id.musicplayer_icon);
        playerTitle = (TextView) findViewById(R.id.playerTitle);
        playerTime = (TextView) findViewById(R.id.playerTime);
        playerDuration = (TextView) findViewById(R.id.playerDuration);
        playerPlayButton = (ImageButton) findViewById(R.id.playerPlayButton);
        playerBackButton = (ImageButton) findViewById(R.id.playerBackButton);
        playerNextButton = (ImageButton) findViewById(R.id.playerNextButton);
        shareButton = (ImageButton) findViewById(R.id.shareButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }


    public void updateSeekBar(){
        seekBar.setMax(musicPlayer.getDuration());
        Player.this.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if(musicPlayer.isPlaying()){
                    long duration = musicPlayer.getCurrentPosition();
                    long minutes = (duration / 1000) / 60;
                    long seconds = (duration / 1000) % 60;
                    String secondsStr = Long.toString(seconds);
                    String secs;
                    if (secondsStr.length() >= 2) {
                        secs = secondsStr.substring(0, 2);
                    } else {
                        secs = "0" + secondsStr;
                    }
                    String duration_str = String.valueOf(minutes) +":" + String.valueOf(secs);
                    playerTime.setText(duration_str);
                    seekBar.setProgress(musicPlayer.getCurrentPosition());
                }
                new Handler().postDelayed(this, 1000);
            }
        }
        );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    musicPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void playPauseButton(View view){
        if (musicPlayer.isPlaying()){
            musicPlayer.pause();
            playerPlayButton.setBackgroundResource(R.drawable.ic_playerplay);
        }else{
            musicPlayer.start();
            playerPlayButton.setBackgroundResource(R.drawable.ic_playerpause);
        }

    }

    public void backButton(View view){
        position = Math.floorMod((position-1), size);
        startMusic(position);
    }

    public void nextButton(View view){
        position = Math.floorMod((position+1), size);
        startMusic(position);
    }

    public void startMusic(int position){
        if(Player.musicPlayer != null){
            Player.musicPlayer.release();
        }
        Music selectedMusic = MusicAdapter.getItem(position);
        playerTitle.setText(selectedMusic.getName());
        playerDuration.setText(selectedMusic.getDuration());
        playerPlayButton.setBackgroundResource(R.drawable.ic_playerpause);
        musicPlayer = MediaPlayer.create(this, selectedMusic.getUri());
        musicPlayer.start();
    }

    public void share(View view){

        Uri uri = MusicAdapter.getItem(position).getUri();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Paylaş"));
    }


}