package com.example.petition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentDate extends Fragment {
    // 변수 초기화
    private Button textView_Date;
    private Button textView_Date2;
    private Button search;
    private Context context;
    private ListView playlist;
    private int year;
    private int month;
    private int day;
    private Boolean flag = false;


    Iterator it1, it2;
    ArrayList<DateData> p_data = new ArrayList<DateData>();
    HashMap<String,Integer> hsMap1 = new HashMap<>();   // 키워드 반복 회수
    HashMap<String,Integer> hsMap2 = new HashMap<>();   // 키워드 동의수
    String date1;
    String date2;

    AsyncTask<?, ?, ?> printTask;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_date, container, false);
        textView_Date = (Button) v.findViewById(R.id.btnStart);
        textView_Date2 = (Button) v.findViewById(R.id.btnEnd);
        search = (Button) v.findViewById(R.id.btnSearch);

        textView_Date.setOnClickListener(this::mOnClick);
        textView_Date2.setOnClickListener(this::mOnClick);
        search.setOnClickListener(this::mOnClick);

        assert container != null;
        context = container.getContext();
        playlist = (ListView)v.findViewById(R.id.playlist);
        printTask = new FragmentDate.printTask().execute();

        return v;
    }

    // 달력 버튼 클릭 이벤트 핸들러
    @SuppressLint("NonConstantResourceId")
    public void mOnClick(View v){
        Calendar c = Calendar.getInstance();
        switch (v.getId()){
            case R.id.btnStart:
                Log.i("[FragmentDate]", '\n' + "첫 번째 달력 버튼 클릭");
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), mDateSetListener, year, month, day).show();
                break;
            case R.id.btnEnd:
                Log.i("[FragmentDate]", '\n' + "두 번째 달력 버튼 클릭");
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), mDateSetListener2, year, month, day).show();
                break;
            case R.id.btnSearch:
                flag = true;
                Log.i("[FragmentDate]", '\n' + "검색 버튼 클릭");
                Toast.makeText(context, "검색 시작", Toast.LENGTH_SHORT).show();
                printTask = new FragmentDate.printTask().execute();
        }
    }

    // 시작 날짜 설정
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date1 = "";
            String tmp0, tmp1, tmp2;
            tmp0=Integer.toString(year);
            if((month+1)/10==0){
                tmp1 = "0" + Integer.toString(month+1);
            }
            else
                tmp1 = Integer.toString(month+1);
            if((dayOfMonth/10)==0){
                tmp2 = "0" + Integer.toString(dayOfMonth);
            }
            else
                tmp2 = Integer.toString(dayOfMonth);
            textView_Date.setText(tmp0+"-"+tmp1+"-"+tmp2);
            switch (month){
                case 0:
                    tmp1="Jan";
                    break;
                case 1:
                    tmp1="Feb";
                    break;
                case 2:
                    tmp1="Mar";
                    break;
                case 3:
                    tmp1="Apr";
                    break;
                case 4:
                    tmp1="May";
                    break;
                case 5:
                    tmp1="Jun";
                    break;
                case 6:
                    tmp1="Jul";
                    break;
                case 7:
                    tmp1="Aug";
                    break;
                case 8:
                    tmp1="Sep";
                    break;
                case 9:
                    tmp1="Oct";
                    break;
                case 10:
                    tmp1="Nov";
                    break;
                case 11:
                    tmp1="Dec";
                    break;
            }
            date1 = tmp1+" "+tmp2+" "+tmp0;
        }
    };

    // 종료 날짜 설정
    private final DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date2 = "";
            String tmp0, tmp1, tmp2;
            tmp0=Integer.toString(year);
            if((month+1)/10==0){
                tmp1="0" + Integer.toString(month+1);
            }
            else
                tmp1=Integer.toString(month+1);
            if((dayOfMonth/10)==0){
                tmp2 = "0" + Integer.toString(dayOfMonth);
            }
            else
                tmp2 = Integer.toString(dayOfMonth);
            textView_Date2.setText(tmp0+"-"+tmp1+"-"+tmp2);
            switch (month){
                case 0:
                    tmp1="Jan";
                    break;
                case 1:
                    tmp1="Feb";
                    break;
                case 2:
                    tmp1="Mar";
                    break;
                case 3:
                    tmp1="Apr";
                    break;
                case 4:
                    tmp1="May";
                    break;
                case 5:
                    tmp1="Jun";
                    break;
                case 6:
                    tmp1="Jul";
                    break;
                case 7:
                    tmp1="Aug";
                    break;
                case 8:
                    tmp1="Sep";
                    break;
                case 9:
                    tmp1="Oct";
                    break;
                case 10:
                    tmp1="Nov";
                    break;
                case 11:
                    tmp1="Dec";
                    break;
            }
            date2 = tmp1+" "+tmp2+" "+tmp0;
        }
    };


    //출력 기능 구현
    @SuppressLint("StaticFieldLeak")
    private class printTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("[FragmentDate]", '\n' + "백그라운드 안 여기 실행된다.");

            // 검색 받은 경우, p_data 다시 읽어옴
            if(flag){
                p_data.clear();

                //DB 관련
                String getString  = "";
                final DBHelper dbHelper = new DBHelper(getActivity(), "PETITION.db", null, 1);
                it1=dbHelper.returnSortedKey(date1,date2);
                it2=dbHelper.returnSortedKeyForTotal(date1,date2);
                hsMap1=dbHelper.returnHashMapForAgree(date1,date2);
                hsMap2=dbHelper.returnHashMapForTotal(date1,date2);
                DateData tmp;

                while(it1.hasNext()) {
                    String tmp1 = (String)it1.next();
                    //String tmp2 = (String)it2.next();
                    tmp = new DateData("1",tmp1,(hsMap1.get(tmp1)),(hsMap2.get(tmp1)));
                    p_data.add(tmp);
                }

                flag = false;
            }
            return null;
        }

        //DB에서 읽어온 데이터를 이용해 리스트 만들기
        @Override
        protected void onPostExecute(Void result) {
            if(p_data != new ArrayList<DateData>()){
                FragmentDate.StoreListAdapter mAdapter = new FragmentDate.StoreListAdapter(context, R.layout.listview_date, p_data);
                playlist.setAdapter(mAdapter);
            }
        }
    }

    //현재 DB에 저장된 정보 출력
    public class StoreListAdapter extends ArrayAdapter<DateData> {
        private ArrayList<DateData> items;
        DateData fInfo;

        //ListView 세팅함수
        public StoreListAdapter(Context context, int textViewResourseId, ArrayList<DateData> items) {
            super(context, textViewResourseId, items);
            this.items = items;
        }

        //ListView 출력함수
        @SuppressLint({"ViewHolder", "InflateParams"})
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            fInfo = items.get(position);
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_date, null);

            //키워드 전달
            final String Keyword = fInfo.getKeyword();
            ((TextView)v.findViewById(R.id.keyword)).setText(Keyword);

            //관련 청원 수
            final String Agree = Integer.toString(fInfo.getAgree());
            ((TextView) v.findViewById(R.id.title)).setText(Agree);

            //총 참여인원 수
            final String Total = Integer.toString(fInfo.getTotal());
            ((TextView) v.findViewById(R.id.total)).setText(Total);

            return v;
        }
    }
}