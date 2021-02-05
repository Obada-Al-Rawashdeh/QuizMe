package com.obadarawashdeh.quiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.obadarawashdeh.quiz.R;
import com.obadarawashdeh.quiz.database.Quiz;
import com.obadarawashdeh.quiz.database.QuizDBAccess;

import java.util.ArrayList;

public class PlayingAct extends AppCompatActivity implements View.OnClickListener {

    private TextView qus, ans1, ans2, ans3, ans4, timerText, score, lives;
    private int scoreInt = 0, livesInt = 3, numberQus=0;
    private ArrayList<Quiz> queList;
    private String RAns;
    boolean isExit,isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        initial();
        showQuestion();
        isExit=false;

    }

    private void initial() {
        qus = findViewById(R.id.que);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        timerText = findViewById(R.id.time_txt);
        score = findViewById(R.id.score_txt);
        lives = findViewById(R.id.heart_txt);
        QuizDBAccess quizDBAccess=QuizDBAccess.getInstance(this);
        quizDBAccess.open();
        queList = quizDBAccess.getAllQus();
        quizDBAccess.close();
    }

    MyTimer myTimer;

    private void showQuestion() {
        myTimer =new MyTimer(30000,1000);

            Quiz q = queList.get(numberQus++);
            qus.setText(q.getQus());
            ans1.setText(q.getAns1());
            ans2.setText(q.getAns2());
            ans3.setText(q.getAns3());
            ans4.setText(q.getAns4());
            RAns = q.getR_ans();
            myTimer.start();

    }

    private void saveScore(){
        Intent intent=new Intent(getApplicationContext(),ResultAct.class);
        intent.putExtra("SCORE",scoreInt);
        startActivity(intent);
        finish();
    }

    private void rightAns(){
        scoreInt += 10;
        myTimer.cancel();
        if(numberQus<queList.size())
            setDialog(R.layout.success_dialog);
        else
            setDialog(R.layout.final_dialog);
    }

    private void wrongAns(){
        livesInt--;
        myTimer.cancel();
        if(livesInt>0)
            setDialog(R.layout.wrong_dialog);
        else
            setDialog(R.layout.game_over_dialog);
    }

    private void timeEnd(){
        livesInt--;
        myTimer.cancel();
        if(livesInt>0)
            setDialog(R.layout.time_end_dialog);
        else
            setDialog(R.layout.game_over_dialog);
    }

    private Dialog myDialog;
    private void setDialog(int layout){
        myDialog=new Dialog(PlayingAct.this);
        myDialog.setContentView(layout);
        myDialog.show();
        myDialog.setCancelable(false);
        if(layout==R.layout.success_dialog || layout==R.layout.wrong_dialog ||layout==R.layout.time_end_dialog){
            Button next=myDialog.findViewById(R.id.nextQus);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        myDialog.dismiss();
                        showData();

                }
            });
        }
        else{
            Button exit=myDialog.findViewById(R.id.exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                    saveScore();
                }
            });
        }
    }

    private boolean checkAns(String ans) {
        return ans.equals(RAns);
    }


    @Override
    public void onClick(View v) {
        TextView clicedText = (TextView) v;
        String chosedAns = clicedText.getText() + "";
        if (checkAns(chosedAns))
            rightAns();
        else
            wrongAns();
    }

    private void showData() {
        score.setText(scoreInt + "");
        lives.setText(livesInt + "");

        showQuestion();

    }

    class MyTimer extends CountDownTimer{
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText((millisUntilFinished/1000)+"");

        }

        @Override
        public void onFinish() {
            if(isExit){
               isFinish=true;
            }else {
                timeEnd();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        isExit=true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isFinish){
            isExit=false;
            timeEnd();
        }
    }
}
