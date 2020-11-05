package com.example.pj01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //날짜 설정
    private String mStrDate=" ";//시작 날짜
    private String mStrDate2=" ";//끝 날짜
    private int year,month,day;//날짜 데이터 저장을 위한 tmp값

    //웹뷰 설정
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateResult();

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        mWebView.loadUrl("https://quickchart.io/wordcloud?text=이 사이트는" +
                "단어의 빈도에 맞춰 그림으로 나타내는 워드클라우드 이다." +
                "예를 들어 오 오 오 오 오 삼 삼 삼 일" +
                "이러면 오가 가장 클것이다"); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }
    //사용자로부터 날짜 입력받으면 날짜 변경
    private void updateResult(){
        TextView textResult = (TextView)findViewById(R.id.textResult);
        textResult.setText("날짜: "+mStrDate+"~"+mStrDate2);
    }


    public void mOnClick(View v){
        Calendar c = Calendar.getInstance();
        switch(v.getId()){
            case R.id.btnSelectData:
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH);
                day=c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, mDateSetListener,year,month,day).show();
                break;
            case R.id.btnSelectData2:
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH);
                day=c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, mDateSetListener2,year,month,day).show();
                break;
        }
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mStrDate=String.format("%d년 %d월 %d일",year,month+1,dayOfMonth);
            updateResult();
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mStrDate2=String.format("%d년 %d월 %d일",year,month+1,dayOfMonth);
            updateResult();
        }
    };
}