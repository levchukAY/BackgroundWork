package com.artiomlevchuk.backgroundwork.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BindBoundService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private static int message = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BindBoundService", "onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BindBoundService", "onBind()");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("BindBoundService", "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("BindBoundService", "onDestroy()");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {

        public BindBoundService getService() {
            Log.d("BindBoundService", "LocalBinder getService()");
            // Return this instance of LocalService so clients can call public methods
            return BindBoundService.this;
        }
    }

    /** method for clients */
    public int getMessage() {
        Log.d("BindBoundService", "getMessage()");
        return message++;
    }

}
