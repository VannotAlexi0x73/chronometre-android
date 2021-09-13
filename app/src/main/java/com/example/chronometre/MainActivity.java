package com.example.chronometre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private long millis = 0; // number of millis since launched
    private boolean running; // is it running or not?
    private boolean runningBefore; // is it running or not?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            millis = savedInstanceState.getLong("millis");
            running = savedInstanceState.getBoolean("running");
            runningBefore = savedInstanceState.getBoolean("runningBefore");
        }
        runTimer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        runningBefore = running;
        running = false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (runningBefore) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("millis", millis);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("runningBefore", runningBefore);
    }

    // Start the stopwatch running when the Start button is clicked.
    public void onClickStart(android.view.View view) {
        running = true;
    }

    // Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(android.view.View view) {
        running = false;
    }

    // Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(android.view.View view) {
        running = false;
        millis = 0;
    }

    private void runTimer() {
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Handler handler = new Handler();
        handler.post(() -> {
            String time = String.format(
                    "%02d:%02d,%01d",
                    (millis / 600) % 60,
                    (millis / 10) % 60,
                    (millis % 600) % 10
            );
            textView.setText(time);
            if (running) millis++;
            handler.postDelayed(this::runTimer, 100);
        });
    }
}