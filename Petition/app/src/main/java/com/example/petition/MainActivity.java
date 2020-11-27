package com.example.petition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //중간 Frame 구성
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final FragmentHome fragmentHome = new FragmentHome();
    private final FragmentDate fragmentDate = new FragmentDate();
    private final FragmentLike fragmentLike = new FragmentLike();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //첫 번째 실행여부 판단
        //CheckAppFirstExecute();

        //상단 액션바 프로젝트 명 없애고, 뒤로가기 아이콘 생성
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //중간 내용
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

        //하단 메뉴 구성 바
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    //상단 부분, 로고 삽입
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar, menu);
        return true;
    }

    //뒤로가기, 홈으로 이동 동작
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                //뒤로가기 동작
                finish();
                return true;
            case R.id.bluehouse:
                //청와대 국민청원 공식 홈페이지로 이동
                //FragmentTransaction transaction = fragmentManager.beginTransaction();
                //transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www1.president.go.kr/petitions"));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //하단 메뉴 부분
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId()) {
                case R.id.homeItem:
                    transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.dateItem:
                    transaction.replace(R.id.frameLayout, fragmentDate).commitAllowingStateLoss();
                    break;
                case R.id.likeItem:
                    transaction.replace(R.id.frameLayout, fragmentLike).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    // 첫 번째 실행 시 IntroActivity 전환
    /*public void CheckAppFirstExecute(){
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if (!isFirst) {
            // 첫 번째 실행 시
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.apply();

            // DB에 청원 분석 결과 파일 저장
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
            finish();
        }
    }*/
}