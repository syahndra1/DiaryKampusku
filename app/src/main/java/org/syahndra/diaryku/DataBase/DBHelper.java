package org.syahndra.diaryku.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    String tag = "SQLite";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ID,D-Day,과목,내용 -> 과제
        //ID,날짜,메모내용 -> 메모장

        db.execSQL("CREATE TABLE DIARY ( _id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, content TEXT, emotion TEXT)");
        db.execSQL("CREATE TABLE HOMEWORK ( _id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, subject TEXT, content TEXT)");
        db.execSQL("CREATE TABLE MEMO ( _id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, content TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void DiaryInsert(String date, String content, String emotion){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DIARY (date, content, emotion) VALUES('"+date+"', '"+content+"', '"+emotion+"');");
    }
    public void HomeworkInsert(String date, String subject, String content){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO HOMEWORK (date, subject, content) VALUES('" + date + "', '" + subject + "', '"+content+"');");
    }
    public void MemoInsert(String date, String content){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MEMO VALUES(null, '" + date + "', '" + content + "');");
    }

    public void DiaryDelete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DIARY WHERE _id="+id+";");
    }
    public void HomeworkDelete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM HOMEWORK WHERE _id="+id+";");
    }
    public void MemoDelete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MEMO WHERE _id="+id+";");
    }


    public void UpdateHomework(String subject, String content, String date, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE HOMEWORK SET date='"+date+"', subject='"+subject+"', content='"+content+"' WHERE _id="+id+";");
    }
    public void UpdateMemo(String content, String date, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MEMO SET date='"+date+"', content='"+content+"' WHERE _id="+id+";");
    }


    public ArrayList<HomeWorkData> getResultHomeworkData(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HomeWorkData> HomeWorkList = new ArrayList<HomeWorkData>();

        int Id;
        String ClassName;
        String Content;
        String Date;

        Cursor cursor = db.rawQuery("SELECT * FROM HOMEWORK;",null);
        while(cursor.moveToNext()){

            Id = cursor.getInt(0);
            ClassName = cursor.getString(2);
            Content = cursor.getString(3);
            Date = cursor.getString(1);

            HomeWorkList.add(new HomeWorkData(Id,ClassName,Content,Date));
            //Toast.makeText(context,cursor.getString(2),Toast.LENGTH_SHORT);
            Log.d(tag,Id +" : "+ClassName);

        }

        //하나 알아야할 것-> 저장은된다.

        return HomeWorkList;
    }

    public ArrayList<MemoData> getResultMemoData(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MemoData> MemoList = new ArrayList<MemoData>();

        int Id;
        String Content;
        String Date;

        Cursor cursor = db.rawQuery("SELECT * FROM MEMO;",null);
        while(cursor.moveToNext()){
            Id = cursor.getInt(0);
            Date = cursor.getString(1);
            Content = cursor.getString(2);

            MemoList.add(new MemoData(Id,Date,Content));
        }

        return MemoList;
    }

    public ArrayList<DiaryData> getResultDiaryData(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DiaryData> DiaryList = new ArrayList<DiaryData>();

        int Id;
        String Content;
        String Date;
        String Emotion;

        Cursor cursor = db.rawQuery("SELECT * FROM DIARY;",null);
        while(cursor.moveToNext()){
            Id = cursor.getInt(0);
            Date = cursor.getString(1);
            Content = cursor.getString(2);
            Emotion = cursor.getString(3);

            DiaryList.add(new DiaryData(Id,Emotion,Date,Content));
        }


        return DiaryList;
    }

}
