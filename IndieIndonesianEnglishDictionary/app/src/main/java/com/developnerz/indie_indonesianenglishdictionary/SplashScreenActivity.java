package com.developnerz.indie_indonesianenglishdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developnerz.indie_indonesianenglishdictionary.modules.PreloaderActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, PreloaderActivity.class);
        startActivity(intent);
        finish();
    }
}
