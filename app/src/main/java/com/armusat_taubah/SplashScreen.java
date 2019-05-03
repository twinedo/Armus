package com.armusat_taubah;

/**
 * Created by TWIN on 6/28/2016.
 */
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;


public class SplashScreen extends Activity {
    private static final int SPLASH_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }

        }, SPLASH_TIME);
    }
}
