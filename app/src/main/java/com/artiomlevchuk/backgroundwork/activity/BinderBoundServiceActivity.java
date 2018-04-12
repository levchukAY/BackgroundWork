package com.artiomlevchuk.backgroundwork.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.artiomlevchuk.backgroundwork.R;
import com.artiomlevchuk.backgroundwork.service.BindBoundService;

public class BinderBoundServiceActivity extends AppCompatActivity {

    private BindBoundService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);

        findViewById(R.id.button_bind).setOnClickListener(view -> {
            Intent intent = new Intent(BinderBoundServiceActivity.this, BindBoundService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        });

        findViewById(R.id.button_send).setOnClickListener(view -> {
            if (mBound) {
                int message = mService.getMessage();
                Toast.makeText(BinderBoundServiceActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_unbind).setOnClickListener(view -> {
            unbindService(mConnection);
            mBound = false;
        });

    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.d("ServiceConnection", "onServiceConnected()");
            BindBoundService.LocalBinder binder = (BindBoundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("ServiceConnection", "onServiceDisconnected()");
            mBound = false;
        }
    };
}
