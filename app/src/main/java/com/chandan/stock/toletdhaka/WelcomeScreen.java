package com.chandan.stock.toletdhaka;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {
    TextView textView;ImageView imageView;Animation animation;
    Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        ActionBar action=getSupportActionBar();
        action.hide();
        setContentView(R.layout.activity_welcome_screen);
        //font= Typeface.createFromAsset(getAssets(),"fonts/font.ttf");
        imageView=findViewById(R.id.imageView2);
        textView=findViewById(R.id.textView2);
        textView.setTypeface(font);
        Animation animation=AnimationUtils.loadAnimation(WelcomeScreen.this,R.anim.myanim);
        textView.startAnimation(animation);
        textView.startAnimation(animation);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(WelcomeScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }

}