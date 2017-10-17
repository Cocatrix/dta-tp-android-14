package fr.codevallee.formation.tp14app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

class ProgressBarAsyncTask extends AsyncTask<Void,Integer,Void> {
    private ProgressBar pBar;
    private Context ctxt;


    protected ProgressBarAsyncTask(ProgressBar pBar, Context ctxt) {
        this.pBar = pBar;
        this.ctxt = ctxt;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int progress;
        try {
            for (int i = 0; i <= 100; i += 10) {
                if (isCancelled()) {
                    break;
                }
                Thread.sleep(500);
                publishProgress(i); // calls onProgressUpdate
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... percent) {
        /**
         * Updates UI. Here making progress on progressBar.
         */
        this.pBar.setProgress(percent[0]);
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(ctxt,"Starting download...",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(ctxt,"Download finished.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(ctxt,"Download cancelled.",Toast.LENGTH_LONG).show();
    }


}
