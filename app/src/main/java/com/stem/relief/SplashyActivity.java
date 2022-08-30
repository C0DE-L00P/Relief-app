package com.stem.relief;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class SplashyActivity extends Activity {

    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        ImageView imageView = (ImageView) findViewById(R.id.splash_gif);
        Glide.with(this).load(R.drawable.intro).into(imageView);

        intent = new Intent(this, LoginPage.class);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException e)
                { e.printStackTrace();}
            }
        };
        timer.start();
    }
}