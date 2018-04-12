package com.artiomlevchuk.backgroundwork.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyIntentService extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyIntentService", "onCreate()" + MyIntentService.this.hashCode());
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        int attempt = intent.getIntExtra("EXTRA_ATTEMPT", -1);
        for (int i = 0; i < 5; i++) {
            Log.d("MyIntentService " + MyIntentService.this.hashCode(), "attempt = " + attempt + " (" + i + ")");
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy()" + MyIntentService.this.hashCode());
    }
}
