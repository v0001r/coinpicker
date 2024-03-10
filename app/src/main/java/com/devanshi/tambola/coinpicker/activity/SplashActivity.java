package com.devanshi.tambola.coinpicker.activity;

import android.content.*;
import android.os.*;

import androidx.appcompat.app.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.utils.*;

public class SplashActivity extends AppCompatActivity {

    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preference = new Preference(this);
        new Handler().postDelayed(() -> {
            Intent intent;
            if (preference.getUserName().isEmpty()) {
                intent = new Intent(SplashActivity.this, LoginSignupActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}
