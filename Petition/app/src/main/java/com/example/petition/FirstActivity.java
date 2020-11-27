package com.example.petition;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FirstActivity extends Activity {

    // 변수 초기화
    private final Petition data = new Petition();
    final DBHelper dbHelper = new DBHelper(FirstActivity.this, "PETITION.db", null, 1);

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //첫 번째 실행여부 판단
        if(CheckAppFirstExecute()){
            // DB에 청원 분석 결과 파일 저장
            for (int i = 584274; i <= 593185; i++){
                if(AddPetition(i)) {
                    dbHelper.insert(data);
                }
            }
        }

        // 1초 인트로 화면 보여주기
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // MainActivity 전환
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

    //JSON 파싱해서 테이블에 추가하는 함수
    boolean AddPetition(Integer name) {
        try {
            //JSON 읽어오기
            String json = "";
            try {
                String filename = name + ".json";
                InputStream is = getAssets().open(filename);
                int fileSize = is.available();

                byte[] buffer = new byte[fileSize];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

            // 변수 초기화
            JSONObject jsonObject = new JSONObject(json);

            //JSON 파싱
            data.setID(Integer.toString(name));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            data.setBegin(dateFormat.parse(jsonObject.getString("begin")));
            data.setEnd(dateFormat.parse(jsonObject.getString("end")));
            data.setCategory(jsonObject.getString("category"));
            String split1 = jsonObject.getString("title").replace("'", "");
            String split2 = split1.replace("\"", "");
            data.setTitle(split2);
            StringBuilder list = new StringBuilder();
            JSONArray keywordArray = jsonObject.getJSONArray("keyword");
            for(int i=0; i<keywordArray.length(); i++){
                list.append(keywordArray.getString(i)).append(',');
            }
            data.setKeyword(list);
            data.setAgree(jsonObject.getInt("agree"));

            return true;
        }
        catch (JSONException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 첫 번째 실행 시 IntroActivity 전환
    public boolean CheckAppFirstExecute(){
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if (!isFirst) {
            // 첫 번째 실행 시
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.apply();
            return true;
        }
        return false;
    }
}