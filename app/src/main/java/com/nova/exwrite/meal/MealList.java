package com.nova.exwrite.meal;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.ExAdapter;
import com.nova.exwrite.exercise.ExData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealList extends AppCompatActivity implements View.OnClickListener, MealAdapter.OnListItemSelectedInterface {

    private BottomNavigationView bottomView;
    RecyclerView recyclerView;
    private static final int REQUEST_CODE = 0;
    ImageView meal_img;
    SharedPreferences sharedPreferences;
    String sharedEx = "mData";
    private MealAdapter mAdapter;
    final Context context = this;

    private ArrayList<mealData> mdataItem;
    Gson gson;
    Type typeMdata = new TypeToken<ArrayList<mealData>>() {
    }.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meallist);

        bottomView = findViewById(R.id.btmNavMeal);
        bottomView.setOnNavigationItemSelectedListener(listener);

        recyclerView = findViewById(R.id.meal_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences(sharedEx, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        gson = new Gson();

        String shared_mdata = sharedPreferences.getString("mealData", "");

        if (shared_mdata.equals("")) {
            mdataItem = new ArrayList<mealData>();
            String sharedMdata = gson.toJson(mdataItem, typeMdata);
            editor.putString("mealData", sharedMdata);
            editor.commit();
        } else {

            mdataItem = gson.fromJson(shared_mdata, typeMdata);
        }

        Log.d(TAG, "arrayconcert : " + "사이즈" + mdataItem.size());
        mAdapter = new MealAdapter(context, this, mdataItem);
        recyclerView.setAdapter(mAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String m_Edata = sharedPreferences.getString("mealData", "");
        if (m_Edata.equals("")) {
            mdataItem = new ArrayList<mealData>();
            String sharedME = gson.toJson(mdataItem, typeMdata);
            editor.putString("mealData", sharedME);
            editor.commit();
        } else {

            mdataItem = gson.fromJson(m_Edata, typeMdata);
        }

        Log.d(TAG, "arraybook : " + "사이즈" + mdataItem.size());
        mAdapter = new MealAdapter(context, this, mdataItem);
        recyclerView.setAdapter(mAdapter);


    }

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

    @Override
    public void onClick(View v) {

    }
}