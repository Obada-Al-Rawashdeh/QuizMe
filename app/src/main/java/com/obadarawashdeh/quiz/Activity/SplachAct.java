package com.obadarawashdeh.quiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.obadarawashdeh.quiz.R;

public class SplachAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);


        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2*1000);
                    startActivity(new Intent(SplachAct.this,MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
