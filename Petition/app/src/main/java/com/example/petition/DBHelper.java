package com.example.petition;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //DBHelper 생성자 (관리할 DB 이름, 버전 정보)
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("[DBHelper: DBHelper]", "생성자가 호출됩니다.");
    }

    //DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //새로운 테이블 생성 (ID, BEGIN_DATE, END_DATE, CATEGORY, TITLE, KEYWORD, AGREE, LIKED)
        sqLiteDatabase.execSQL("CREATE TABLE PETITION (ID TEXT PRIMARY KEY, BEGIN_DATE DATE, END_DATE DATE, CATEGORY TEXT, TITLE TEXT, KEYWORD TEXT, AGREE INTEGER, LIKED INTEGER);");
        Log.i("[DBHelper: onCreate]", "테이블을 불러옵니다.");
    }

    //DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // 레코드 추가, Primary Key 이용해 중복 처리
    public void insert(Petition data) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PETITION VALUES('"+ data.getID()+"', '"+data.getBegin()+"', '"+data.getEnd()+ "', '"+data.getCategory()+"', '"+data.getTitle()+"', '"+data.getKeyword()+"', '"+data.getAgree()+"', 0);");
        Log.i("[DBHelper: insert]", "레코드가 추가되었습니다.");
        db.close();
    }
}
