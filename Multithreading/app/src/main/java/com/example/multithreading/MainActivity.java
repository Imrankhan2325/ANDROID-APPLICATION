package com.example.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar p1, p2;
    TextView text1, text2, status, overall, log;
    Button start;

    Handler handler = new Handler();

    int a = 0, b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1 = findViewById(R.id.progress1);
        p2 = findViewById(R.id.progress2);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);

        status = findViewById(R.id.status);
        overall = findViewById(R.id.overall);
        log = findViewById(R.id.log);

        start = findViewById(R.id.start);

        start.setOnClickListener(v -> startThreads());
    }

    void startThreads() {

        start.setEnabled(false);

        status.setText("🧵 Two Threads Running...");
        log.setText("Thread 1 started\nThread 2 started\n");

        Thread thread1 = new Thread(() -> {

            for (a = 0; a <= 100; a++) {

                int value = a;

                handler.post(() -> {
                    p1.setProgress(value);
                    text1.setText("Processing: " + value + "%");
                    updateOverall();
                });

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            handler.post(() ->
                    log.append("Thread 1 completed ✓\n"));
        });


        Thread thread2 = new Thread(() -> {

            for (b = 0; b <= 100; b++) {

                int value = b;

                handler.post(() -> {
                    p2.setProgress(value);
                    text2.setText("Processing: " + value + "%");
                    updateOverall();
                });

                try {
                    Thread.sleep(80);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            handler.post(() ->
                    log.append("Thread 2 completed ✓\n"));
        });


        thread1.start();
        thread2.start();

        log.append("Both threads are executing simultaneously...\n");
    }

    void updateOverall() {

        int progress = (a + b) / 2;

        overall.setText(
                "Overall Progress: " + progress + "%"
        );

        if (progress == 100) {
            status.setText("✓ Both Threads Completed");
            start.setEnabled(true);
        }
    }
}
