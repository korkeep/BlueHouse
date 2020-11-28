package com.example.petition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentHome extends Fragment {
    private Context context;
    private EditText searchOption;
    private ListView playlist;
    private String keyword = "";

    //리스트 출력에 필요한 Parameter
    ArrayList<HomeData> p_data = new ArrayList<HomeData>();
    AsyncTask<?, ?, ?> printTask;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        assert container != null;
        context = container.getContext();
        playlist = (ListView)v.findViewById(R.id.playlist);
        printTask = new FragmentHome.printTask().execute();

        //검색 기능
        searchOption = v.findViewById(R.id.search_option);
        searchOption.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchOption.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    //검색 기능 구현
    public void performSearch() {
        searchOption.clearFocus();
        InputMethodManager in = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchOption.getWindowToken(), 0);

        //EditText 검색한 결과 키워드로 받기
        keyword = searchOption.getText().toString();
        Log.i("[FragmentHome]", '\n' + keyword);
        printTask = new FragmentHome.printTask().execute();
    }

    //출력 기능 구현
    @SuppressLint("StaticFieldLeak")
    private class printTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("[FragmentHome]", '\n' + "백그라운드 안 여기 실행된다.");

            p_data.clear();  //리스트 템플릿인 pdata 비워주기

            //DB 관련
            String getString  = "";
            final DBHelper dbHelper = new DBHelper(getActivity(), "PETITION.db", null, 1);

            getString = dbHelper.get_HomeData(keyword);

            //DB 읽어오기
            if(!getString.equals("")){
                String[] line = getString.split("\n");
                String[] subStr;
                HomeData temp;

                // 레코드 가져오기 (ID, CATEGORY, TITLE, AGREE, LIKED)
                for (String s : line) {
                    subStr = s.split("\t");
                    temp = new HomeData(subStr[0], subStr[3], subStr[4], Integer.parseInt(subStr[6]), Integer.parseInt(subStr[7]));
                    p_data.add(temp);
                }
            }
            return null;
        }

        //DB에서 읽어온 데이터를 이용해 리스트 만들기
        @Override
        protected void onPostExecute(Void result) {
            FragmentHome.StoreListAdapter mAdapter = new FragmentHome.StoreListAdapter(context, R.layout.listview_home, p_data);
            playlist.setAdapter(mAdapter);
        }
    }

    //현재 DB에 저장된 정보 출력
    public class StoreListAdapter extends ArrayAdapter<HomeData> {
        private ArrayList<HomeData> items;
        HomeData fInfo;

        //DB 관련
        final DBHelper dbHelper = new DBHelper(getActivity(), "PETITION.db", null, 1);

        //ListView 세팅함수
        public StoreListAdapter(Context context, int textViewResourseId, ArrayList<HomeData> items) {
            super(context, textViewResourseId, items);
            this.items = items;
        }

        //ListView 출력함수
        @SuppressLint({"ViewHolder", "InflateParams"})
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            fInfo = items.get(position);
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_home, null);

            //ID Intent 전달
            final String ID = fInfo.getID();

            //카테고리 설정
            final String Category = fInfo.getCategory();
            ((TextView) v.findViewById(R.id.category)).setText(Category);

            //제목 설정
            final String Title = fInfo.getTitle();
            ((TextView) v.findViewById(R.id.title)).setText(Title);

            //동의인원 설정
            final String Agree = Integer.toString(fInfo.getAgree());
            ((TextView) v.findViewById(R.id.agree)).setText(Agree);

            //좋아요 버튼 설정
            final ToggleButton like = (ToggleButton) v.findViewById(R.id.like);

            // DB에 레코드가 존재한다면 채워진 하트 모양으로 전환
            if(dbHelper.get_Liked(ID)){
                like.setBackgroundDrawable(getResources().getDrawable(R.drawable.like_white));
            }

            //like 버튼 클릭 시 like 버튼 활성화
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // like 체크 상태로 전환
                    if(dbHelper.get_Liked(ID)) like.setChecked(true);
                    else like.setChecked(false);
                    // 보관함에 추가
                    if(!like.isChecked()) {
                        //Like 클릭시 하트 채워짐, 토스트메시지
                        like.setBackgroundDrawable(getResources().getDrawable(R.drawable.like_white));
                        Toast.makeText(context, "보관함에 추가", Toast.LENGTH_SHORT).show();
                    }
                    // 보관함에 삭제
                    else {
                        like.setBackgroundDrawable(getResources().getDrawable(R.drawable.like_dark));
                        Toast.makeText(context, "보관함에서 삭제", Toast.LENGTH_SHORT).show();
                    }
                    dbHelper.update_Liked(ID);
                }
            });

            //썸네일, 제목 클릭 시 청원 URL 이동
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String URL = "https://www1.president.go.kr/petitions/" + ID;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    startActivity(intent);
                }
            });
            return v;
        }
    }
}