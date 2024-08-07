package com.example.findtheway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private SpeechRecognizer mSpeechRecognizer = null;
    private boolean mIsListening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSpeechRecognizer();

        if (checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, VOICE_RECOGNITION_REQUEST_CODE);
        }
    }

    private void createSpeechRecognizer() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //String[] matches = bundle.getStringArray(SpeechRecognizer.RESULTS_RECOGNITION);
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && matches.size() > 0) {
                    String command = matches.get(0);
                    handleCommand(command);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    /*
    public void btnNav(View view) {
        //Intent navIntent = new Intent(this, MainActivity.class);
        //startActivity(navIntent);
    }

    public void btnContact(View view) {
        Intent contactIntent = new Intent(this, ContactsActivity.class);
        startActivity(contactIntent);
    }
    */

    public void btnSpeak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");

        if (mIsListening) {
            mIsListening = false;
            mSpeechRecognizer.stopListening();
        }
        else {
            mIsListening = true;
            mSpeechRecognizer.startListening(intent);
        }
    }

    private void handleCommand(String command) {
        if (command.contains("emergency") || command.contains("contacts")) {
            Intent contactIntent = new Intent(this, ContactsActivity.class);
            startActivity(contactIntent);
        }
        else if (command.contains("navigate")) {
            Intent navigateIntent = new Intent(this, NavigateActivity.class);
            startActivity(navigateIntent);
        }
    }
}