package com.example.petition;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;

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
    public void Insert(Petition data) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PETITION VALUES('"+ data.getID()+"', '"+data.getBegin()+"', '"+data.getEnd()+ "', '"+data.getCategory()+"', '"+data.getTitle()+"', '"+data.getKeyword()+"', '"+data.getAgree()+"', 0);");
        Log.i("[DBHelper: insert]", "레코드가 추가되었습니다.");
        db.close();
    }

    // 키워드 검색 레코드 출력 (FragmentRecommend)
    public String get_HomeData(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // 레코드 가져오기 (ID, BEGIN_DATE, END_DATE, CATEGORY, TITLE, KEYWORD, AGREE, LIKED)
        String mQuery = "SELECT * FROM PETITION WHERE KEYWORD LIKE '%"+search+"%'";
        Cursor cursor = db.rawQuery(mQuery, null);

        while (cursor.moveToNext()) {
            result += cursor.getString(0)   //ID
                    + '\t'
                    + cursor.getString(1)   //BEGIN_DATE
                    + '\t'
                    + cursor.getString(2)   //END_DATE
                    + '\t'
                    + cursor.getString(3)   //CATEGORY
                    + '\t'
                    + cursor.getString(4)   //TITLE
                    + '\t'
                    + cursor.getString(5)   //KEYWORD
                    + '\t'
                    + cursor.getInt(6)      //AGREE
                    + '\t'
                    + cursor.getInt(7)      //LIKED
                    + '\n';
        }
        Log.i("[DBHelper: get_HomeData]", '\n' + result);
        return result;
    }

    // 좋아요 체크여부 반환
    public boolean get_Liked(String ID) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // 레코드 가져오기 (VIDEO_ID, TITLE, URL, PUBLISHED_AT, LIKE_AT, GROUP_NAME, PLAYED)
        Cursor cursor = db.rawQuery("SELECT LIKED FROM PETITION WHERE ID=?", new String[]{ID});
        while (cursor.moveToNext()) {
            result += cursor.getInt(0);   //LIKED
        }

        if(ID.equals(result)){
            Log.i("[DBHelper: getResult_Liked]", ID + "는 좋아요 클릭한 청원입니다.");
            return true;
        } else {
            Log.i("[DBHelper: getResult_Liked]", ID + "는 좋아요 클릭한 청원이 아닙니다.");
            return false;
        }
    }

    // 좋아요 업데이트
    public void update_Liked(String ID) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        String temp ="";

        // 현재 좋아요 여부 가져오기
        Cursor cursor = db.rawQuery("SELECT LIKED FROM PETITION WHERE ID=?", new String[] {ID});
        while (cursor.moveToNext()) {
            temp += cursor.getString(0);    //PLAYED
        }
        int result = 0;
        if(Integer.parseInt(temp) == 0){
            result = 1;
        }

        // 좋아요 여부 업데이트
        db.execSQL("UPDATE PETITION SET LIKED=" + result + " WHERE ID='" + ID + "';");
        Log.i("[DBHelper: update_Liked]", "좋아요 여부가 업데이트 됐습니다.");
        db.close();
    }
}
