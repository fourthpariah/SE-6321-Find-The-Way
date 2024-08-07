package com.example.findtheway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.util.Locale;

public class DirectionsActivity extends AppCompatActivity {

    TextToSpeech tts;
    public int stepCount;
    public int roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        stepCount = 0;

        Bundle b = getIntent().getExtras();
        roomNum = b.getInt("roomNum");

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    tts.speak("Click the button with each step", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    public void btnStep(View view) {

        int stepsToRoom = 1;

        switch (roomNum) {
            case 1: stepsToRoom = 3;
            break;
            case 3: stepsToRoom = 4;
            break;
            case 7: stepsToRoom = 5;
            break;
            default: stepsToRoom = 3;
            break;
        }

        if (stepCount < stepsToRoom) {
            stepCount++;
        }
        else {
            tts.speak("You have arrived at your destination", TextToSpeech.QUEUE_FLUSH, null, null);
            finish();
        }

    }
}