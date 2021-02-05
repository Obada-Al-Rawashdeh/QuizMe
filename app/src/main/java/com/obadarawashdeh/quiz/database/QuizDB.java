package com.obadarawashdeh.quiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class QuizDB extends SQLiteAssetHelper {

    public static final String TB_NAME ="QUS";
    public static final String qus="question";
    public static final String Ans1="ans1";
    public static final String Ans2="ans2";
    public static final String Ans3="ans3";
    public static final String Ans4="ans4";
    public static final String RAns="rightAns";

    private static final int DB_VR=1;
    private static final String DB_name= "QUIZ.db";

    public QuizDB(Context context) {
        super(context,DB_name,null,DB_VR);
    }

}