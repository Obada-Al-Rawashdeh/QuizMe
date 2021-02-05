package com.obadarawashdeh.quiz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class QuizDBAccess {


    private SQLiteDatabase database;
    private QuizDB quizDB;
    private static QuizDBAccess instance;

    private QuizDBAccess (Context context){
        quizDB=new QuizDB(context);
    }

    public static synchronized QuizDBAccess getInstance(Context context){
        if(instance==null)
            instance=new QuizDBAccess(context);
        return instance;
    }

    public  void open(){
        database=quizDB.getWritableDatabase();
    }

    public  void close(){
        if(database!=null)
            database.close();
    }

    public ArrayList<Quiz> getAllQus(){
        ArrayList<Quiz> returnList=new ArrayList<>();
        Quiz m;
        Cursor cr=database.rawQuery("SELECT * FROM "+ QuizDB.TB_NAME+" ORDER BY RANDOM() LIMIT 8", null);
        if(cr!=null && cr.moveToFirst()){
            do{
                m=new Quiz();
                m.setQus(cr.getString(cr.getColumnIndex(QuizDB.qus)));
                m.setAns1(cr.getString(cr.getColumnIndex(QuizDB.Ans1)));
                m.setAns2(cr.getString(cr.getColumnIndex(QuizDB.Ans2)));
                m.setAns3(cr.getString(cr.getColumnIndex(QuizDB.Ans3)));
                m.setAns4(cr.getString(cr.getColumnIndex(QuizDB.Ans4)));
                m.setR_ans(cr.getString(cr.getColumnIndex(QuizDB.RAns)));
                returnList.add(m);
            }while (cr.moveToNext());
        }
        cr.close();
        return returnList;
    }
}
