package com.coffehauselabs.nodelogin;

import android.os.Bundle;
import android.app.Activity;

public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_register
        setContentView(R.layout.activity_profile);
    }
}
