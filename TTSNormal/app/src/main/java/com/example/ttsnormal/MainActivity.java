package com.example.ttsnormal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private EditText ed;
    private SeekBar sbPitch;
    private SeekBar sbSpeed;
    private Button btSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSpeak=findViewById(R.id.button_speak);
        //Context context=this;
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
              if(status==TextToSpeech.SUCCESS){
                  int result=tts.setLanguage(Locale.UK);

                  if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                      Log.e("TTS","Language not supported");
                  } else{
                      btSpeak.setEnabled(true);
                  }

              }else{
                  Log.e("TTS","Initialization failed");
              }
            }
        });

        ed=findViewById(R.id.edit_text);
        sbPitch=findViewById(R.id.seek_bar_pitch);
        sbSpeed=findViewById(R.id.seek_bar_speed);

        btSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak(){
        String text=ed.getText().toString();
        float pitch=(float)sbPitch.getProgress() /50;
        if (pitch<0.1) pitch=0.1f;
        float speed=(float)sbSpeed.getProgress() /50;
        if (speed<0.1) speed=0.1f;

        tts.setPitch(pitch);
        tts.setSpeechRate(speed);

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (tts!=null){
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }
}
