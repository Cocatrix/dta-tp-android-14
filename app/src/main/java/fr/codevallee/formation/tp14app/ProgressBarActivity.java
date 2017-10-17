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

public class ProgressBarActivity extends AppCompatActivity {
    private Handler handler;
    private ProgressBar pBar;

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
                        Log.d("WAIT","Sleeping");
                        Thread.sleep(300);
                        Message message = handler.obtainMessage();
                        messageBundle.putInt("progress", 10);
                        message.setData(messageBundle);
                        handler.sendMessage(message);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        /* Does not work, tried to stop the circle rotating after it's finished
        Thread progressCircleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProgressBar pCirBar = (ProgressBar) findViewById(R.id.progressBarCircle);
                    Bundle messageBundle = new Bundle();
                    while(ProgressBarActivity.this.pBar.getProgress() <
                            ProgressBarActivity.this.pBar.getMax()) {
                        Log.d("WAIT","Sleeping");
                        pCirBar.animate();
                        Thread.sleep(100);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        RotateDrawable rotateDrawable = (RotateDrawable) pCirBar.getIndeterminateDrawable();
                        rotateDrawable.setToDegrees(0);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        progressCircleThread.start();
        */
        progressThread.start();

    }
}
