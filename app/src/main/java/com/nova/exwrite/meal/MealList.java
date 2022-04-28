package com.nova.exwrite.meal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.exercise.ExList;
import com.nova.exwrite.exercise.ExListReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MealList extends AppCompatActivity implements View.OnClickListener, MealAdapter.OnListItemSelectedInterface {

    private BottomNavigationView bottomView;
    RecyclerView recyclerView;
    private static final int REQUEST_CODE = 0;

    private MealAdapter mAdapter;
    final Context context = this;

    private ArrayList<MealData> mdataItem;
    RequestQueue queue;
    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;
    String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meallist);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", "0"); //키값, 디폴트값

        bottomView = findViewById(R.id.btmNavMeal);
        bottomView.setOnNavigationItemSelectedListener(listener);

        recyclerView = findViewById(R.id.meal_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        queue = Volley.newRequestQueue(this);
        mealRecord();

        mAdapter = new MealAdapter(context, mdataItem);
        recyclerView.setAdapter(mAdapter);


    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//
//        String m_Edata = sharedPreferences.getString("mealData", "");
//        if (m_Edata.equals("")) {
//            mdataItem = new ArrayList<mealData>();
//            String sharedME = gson.toJson(mdataItem, typeMdata);
//            editor.putString("mealData", sharedME);
//            editor.commit();
//        } else {
//
//            mdataItem = gson.fromJson(m_Edata, typeMdata);
//        }
//
//        Log.d(TAG, "arraybook : " + "사이즈" + mdataItem.size());
//        mAdapter = new MealAdapter(context, this, mdataItem);
//        recyclerView.setAdapter(mAdapter);
//
//
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mealText:
                    Intent mWrite = new Intent(getApplicationContext(), MealWrite.class);  // 0
                    startActivity(mWrite);
                    break;
//                case R.id.mealCam:
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivity(cameraIntent);
//
//
//                    break;
//
//                case R.id.mealGal:
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, 101);
//
//                    break;
            }

            return false;
        }

    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101) {
//            if (resultCode == RESULT_OK) {
//                Uri fileUri = data.getData();
//                ContentResolver resolver = getContentResolver();
//                try {
//                    InputStream instream = resolver.openInputStream(fileUri);
//                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
//                    imageView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
//                    instream.close();   // 스트림 닫아주기
//                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
//                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
//        File tempFile = new File(getCacheDir(), imgName);    // 파일 경로와 이름 넣기
//        try {
//            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
//            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
//            out.close();    // 스트림 닫아주기
//            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public void onItemSelected(View v, int position) {
        MealAdapter.CustomViewHolder viewHolder = (MealAdapter.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }

    public void mealRecord() {
//        String URL = "http://solution12441.dothome.co.kr/mealList/meallist.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ex", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arraynick = jsonObject.getJSONArray("result");
                    ArrayList<MealData> mealdata = new ArrayList<MealData>();

                    for (int i = 0, j = arraynick.length(); i < j; i++) {
                        JSONObject obj = arraynick.getJSONObject(i);


                        obj.getInt("no");
                        obj.getString("id");
                        obj.getString("name");
                        obj.getString("time");
                        obj.getString("amount");
                        obj.getString("memo");
//                        obj.getString("memo");
//                       obj.getString("img_path");

                        MealData mealData = new MealData(obj.getInt("no"), obj.getString("name"), obj.getString("time"), obj.getString("amount"), obj.getString("memo"));
//                        bodyData2.setBodyNickname(obj.getString("nickname"));
//                        bodyData2.setBodyweight(obj.getString("weight"));
//                        bodyData2.setBodymuscle(obj.getString("muscle"));
//                        bodyData2.setBodyfat(obj.getString("fat"));
//                        bodyData2.setBodyweight(obj.getString("memo"));
//                        bodyData2.setBodyweight(obj.getString("weight"));

                        mealdata.add(mealData);

                    }

                    mAdapter = new MealAdapter(context, mealdata);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MealListReq mListReq = new MealListReq(loginID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MealList.this);
        queue.add(mListReq);
    }

    @Override
    public void onClick(View v) {

    }
}