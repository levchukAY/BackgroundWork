package com.artiomlevchuk.backgroundwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.artiomlevchuk.backgroundwork.activity.BinderBoundServiceActivity;
import com.artiomlevchuk.backgroundwork.activity.IntentServiceActivity;
import com.artiomlevchuk.backgroundwork.activity.MessengerBoundServiceActivity;
import com.artiomlevchuk.backgroundwork.activity.StartedServiceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button_started_service).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, StartedServiceActivity.class)));

        findViewById(R.id.button_intent_service).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, IntentServiceActivity.class)));

        findViewById(R.id.button_bound_service_binder).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, BinderBoundServiceActivity.class)));

        findViewById(R.id.button_bound_service_messenger).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, MessengerBoundServiceActivity.class)));

    }

}
