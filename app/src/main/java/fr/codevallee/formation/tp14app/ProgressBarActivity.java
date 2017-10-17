package fr.codevallee.formation.tp14app;

import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProgressBarActivity extends AppCompatActivity {
    private Handler handler;
    private ProgressBar pBar;

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isPausing = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        this.pBar = (ProgressBar) findViewById(R.id.progressBar);

        this.handler = new Handler() {
            public void handleMessage(Message message) {
                int progress = message.getData().getInt("progress");
                ProgressBarActivity.this.pBar.incrementProgressBy(progress);
            }
        };
    }

    protected void go(View view) throws InterruptedException {
        Log.d("STATE","go !");
        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle messageBundle = new Bundle();
                    while(ProgressBarActivity.this.pBar.getProgress() <
                            ProgressBarActivity.this.pBar.getMax()) {
                        if (isPausing.get()) {
                            Log.d("WAIT", "Sleeping for a while");
                            Thread.sleep(5000);
                        } else {
                            Log.d("WAIT", "Sleeping a little");
                            Thread.sleep(200);
                            Message message = handler.obtainMessage();
                            messageBundle.putInt("progress", 5);
                            message.setData(messageBundle);
                            handler.sendMessage(message);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        isRunning.set(true);
        progressThread.start();

    }

    @Override
    protected void onDestroy() {
        isRunning.set(false);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isPausing.set(true);
        super.onPause();
    }
    @Override
    protected void onResume() {
        isPausing.set(false);
        super.onResume();
    }
}
