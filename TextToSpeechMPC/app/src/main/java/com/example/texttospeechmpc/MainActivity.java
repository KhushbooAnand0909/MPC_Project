package com.example.texttospeechmpc;
import android.accessibilityservice.AccessibilityService;
import android.widget.FrameLayout;
import android.speech.tts.TextToSpeech;
import android.view.WindowManager;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import java.util.Locale;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AccessibilityService {

    FrameLayout fLayout;
    TextToSpeech tts;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        WindowManager wman = (WindowManager) getSystemService(WINDOW_SERVICE);
        fLayout = new FrameLayout(this);
        WindowManager.LayoutParams laypar = new WindowManager.LayoutParams();
        laypar.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        laypar.format = PixelFormat.TRANSLUCENT;
        laypar.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        laypar.width = WindowManager.LayoutParams.WRAP_CONTENT;
        laypar.height = WindowManager.LayoutParams.WRAP_CONTENT;
        laypar.gravity = Gravity.BOTTOM;
        LayoutInflater inf = LayoutInflater.from(this);
        inf.inflate(R.layout.activity_main, fLayout);
        wman.addView(fLayout, laypar);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override

    public void onAccessibilityEvent(AccessibilityEvent event) {

        final int eType = event.getEventType();
        String eText = null;
        switch (eType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eText = "Clicked: ";
                break;
        }

        if (event.getContentDescription() != null) {
            eText = eText + event.getContentDescription();
        } else {
            eText = eText + event.getText();
        }
        voiceForUser(eText);
    }

    private void voiceForUser(String eText) {
        tts.speak(eText, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInterrupt() {

    }
    String TAG="Text";
}