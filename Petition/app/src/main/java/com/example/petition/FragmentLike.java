package com.example.petition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class FragmentLike extends Fragment {

    private Context context;
    private ListView playlist;
    private String select = "";

    //리스트 출력에 필요한 Parameter
    ArrayList<LikeData> p_data = new ArrayList<LikeData>();
    AsyncTask<?, ?, ?> printTask;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like, container, false);
        assert container != null;
        context = container.getContext();
        playlist = (ListView)v.findViewById(R.id.playlist);

        //Spinner 관련
        final String[] data = getResources().getStringArray(R.array.storeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, data);
        Spinner spinner = (Spinner) v.findViewById(R.id.storeSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select = (String) adapterView.getItemAtPosition(i);
                printTask = new printTask().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                refresh();
            }
        });

        return v;
    }

    //Fragment 업데이트
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
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
            //리스트 템플릿인 p_data 비워주기
            p_data.clear();

            //DB 관련
            String getString = "";
            final DBHelper dbHelper = new DBHelper(getActivity(), "PETITION.db", null, 1);

            //공감 순, 제목 순, 날짜 순 정렬
            switch (select) {
                case "제목순":
                    getString = dbHelper.sort_Title();
                    break;
                case "날짜순":
                    getString = dbHelper.sort_Begin();
                    break;
                default:
                    getString = dbHelper.sort_Agree();
                    break;
            }

            //DB 읽어오기
            if(!getString.equals("")){
                String[] line = getString.split("\n");
                String[] subStr;
                LikeData temp;

                // 레코드 가져오기 (ID, CATEGORY, TITLE, AGREE, LIKED)
                for (String s : line) {
                    subStr = s.split("\t");
                    temp = new LikeData(subStr[0], subStr[3], subStr[4], Integer.parseInt(subStr[6]), Integer.parseInt(subStr[7]));
                    p_data.add(temp);
                }
            }
            return null;
        }

        //DB에서 읽어온 데이터를 이용해 리스트 만들기
        @Override
        protected void onPostExecute(Void result) {
            if(p_data != new ArrayList<LikeData>()){
                FragmentLike.StoreListAdapter mAdapter = new FragmentLike.StoreListAdapter(context, R.layout.listview_like, p_data);
                playlist.setAdapter(mAdapter);
            }
        }
    }

    //현재 DB에 저장된 정보 출력
    public class StoreListAdapter extends ArrayAdapter<LikeData> {
        private ArrayList<LikeData> items;
        LikeData fInfo;

        //DB 관련
        final DBHelper dbHelper = new DBHelper(getActivity(), "PETITION.db", null, 1);

        //ListView 세팅함수
        public StoreListAdapter(Context context, int textViewResourseId, ArrayList<LikeData> items) {
            super(context, textViewResourseId, items);
            this.items = items;
        }

        //ListView 출력함수
        @SuppressLint({"ViewHolder", "InflateParams"})
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            fInfo = items.get(position);
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_like, null);

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

                        //해당 Fragment 다시 load
                        //TODO 자연스러운 애니메이션으로 삭제하는 방법 있으면 추가하자
                        refresh();
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