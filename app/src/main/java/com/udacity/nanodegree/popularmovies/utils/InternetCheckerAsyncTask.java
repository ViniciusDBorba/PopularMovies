package com.udacity.nanodegree.popularmovies.utils;

import android.os.AsyncTask;
import android.support.v4.util.Consumer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetCheckerAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final Consumer<Boolean> consumer;

    public InternetCheckerAsyncTask(Consumer<Boolean> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();

            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);

            sock.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        consumer.accept(internet);
    }
}
