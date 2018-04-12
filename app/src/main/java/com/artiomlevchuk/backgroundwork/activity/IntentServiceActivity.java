package com.artiomlevchuk.backgroundwork.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.artiomlevchuk.backgroundwork.R;
import com.artiomlevchuk.backgroundwork.service.MyIntentService;

public class IntentServiceActivity extends AppCompatActivity {

    private static int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        findViewById(R.id.button_handle_intent).setOnClickListener(view -> {
            Intent intent = new Intent(IntentServiceActivity.this, MyIntentService.class);
            intent.putExtra("EXTRA_ATTEMPT", attempts++);
            startService(intent);
        });
    }


}
