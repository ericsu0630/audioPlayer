package com.example.audiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer media;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        media = MediaPlayer.create(this, R.raw.audio1);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        SeekBar volume = (SeekBar) findViewById(R.id.volumeSlider);
        final SeekBar progress = (SeekBar) findViewById(R.id.progressBar);
        final int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //get the max volume of the device
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //get the current volume of the audio stream
        volume.setMax(maxVolume); //set the max volume of the slider to device's max volume
        volume.setProgress(currentVolume); //set the current volume of the slider to the device's current volume
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Volume:", String.valueOf(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progress.setMax(media.getDuration()); //sets the max value of the progress bar to the duration of the media
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Progress",String.valueOf(i));
                progress.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                media.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media.seekTo(progress.getProgress());
                media.start();
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progress.setProgress(media.getCurrentPosition()); //sets the progress bar to the current position of the media player
            }
        },0,1000); //Task runs once every 100ms
    }

    public void playMedia(View view){
        //Button button = (Button) findViewById(R.id.play);
        media.start();
    }

    public void pauseMedia(View view){
        media.pause();
    }

    public void stopMedia(View view){
        media.stop();
        media = MediaPlayer.create(this, R.raw.audio1);
    }
}