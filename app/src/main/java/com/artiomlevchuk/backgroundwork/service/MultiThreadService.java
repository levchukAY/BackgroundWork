package com.artiomlevchuk.backgroundwork.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MultiThreadService extends Service {

    @Override
    public void onCreate() {
        Log.d("MultiThreadService " + hashCode(), "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.d("MultiThreadService " + this.hashCode(), "onStartCommand()");

        final int attempt = intent.getIntExtra("EXTRA_ATTEMPT", -1);

        new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    Log.d("MultiThreadService", "thread " + this.hashCode() + " attempt = " + attempt + " (" + i + ")");
                    synchronized (this) {
                        try {
                            wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                stopSelf(startId);
        }).start();

        Log.d("MultiThreadService " + this.hashCode(), "return onStartCommand()");
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("MultiThreadService " + this.hashCode(), "onDestroy()");
    }

}