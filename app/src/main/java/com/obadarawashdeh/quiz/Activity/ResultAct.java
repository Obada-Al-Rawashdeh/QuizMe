package com.obadarawashdeh.quiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.obadarawashdeh.quiz.R;

public class ResultAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel=findViewById(R.id.scoreLabel);
        TextView highScoreLabel=findViewById(R.id.highScore);

        int scoreValue=getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(scoreValue+"");

        SharedPreferences settings=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScoreValue=settings.getInt("HIGH_SCORE",0);

        if(scoreValue>highScoreValue){
            highScoreLabel.setText(scoreValue+"");
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",scoreValue);
            editor.commit();
        }
        else {
            highScoreLabel.setText(highScoreValue+"");
        }

    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),PlayingAct.class));
        finish();
    }
}
