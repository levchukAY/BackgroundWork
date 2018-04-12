package com.artiomlevchuk.backgroundwork.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.artiomlevchuk.backgroundwork.R;
import com.artiomlevchuk.backgroundwork.service.StartedService;

public class StartedServiceActivity extends AppCompatActivity {

    private static int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        findViewById(R.id.button_start_service).setOnClickListener(view -> {
            Intent intent = new Intent(StartedServiceActivity.this, StartedService.class);
            intent.putExtra("EXTRA_ATTEMPT", attempts++);
            startService(intent);
        });
    }
}
