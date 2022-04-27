package com.nova.exwrite.meal.logout;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.logout.ExAdapterOut;
import com.nova.exwrite.exercise.logout.ExDataOut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealListOut extends AppCompatActivity implements View.OnClickListener, MealAdapterOut.OnListItemSelectedInterface {

    private BottomNavigationView bottomView;
    RecyclerView recyclerView;
    private static final int REQUEST_CODE = 0;
    String sharedBody = "MDataOut";
    private ArrayList<MealDataOut> mdataItem;
    Gson gson = new GsonBuilder().create();
    private MealAdapterOut mAdapter;
    final Context context = this;

    Type typeMdata = new TypeToken<ArrayList<MealDataOut>>() {
    }.getType();



    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meallist);

        bottomView = findViewById(R.id.btmNavMeal);
        bottomView.setOnNavigationItemSelectedListener(listener);

        recyclerView = findViewById(R.id.meal_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String shared_mdata = sharedPreferences.getString("MDataOut", "");
        if (shared_mdata.equals("")) {
            mdataItem = new ArrayList<MealDataOut>();
            String sharedMdata = gson.toJson(mdataItem, typeMdata);
            editor.putString("MDataOut", sharedMdata);
            editor.commit();
        } else {

            mdataItem = gson.fromJson(shared_mdata, typeMdata);
        }



        mAdapter = new MealAdapterOut(context, this, mdataItem);
        recyclerView.setAdapter(mAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String body_data = sharedPreferences.getString("MDataOut", "");
        if (body_data.equals("")) {
            mdataItem = new ArrayList<MealDataOut>();
            String meal_array = gson.toJson(mdataItem, typeMdata);
            editor.putString("MDataOut", meal_array);
            editor.commit();
        } else {

            mdataItem = gson.fromJson(body_data, typeMdata);
        }

        Log.d(TAG, "arraybook : " + "사이즈" + mdataItem.size());
        mAdapter = new MealAdapterOut(context, this, mdataItem);
        recyclerView.setAdapter(mAdapter);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mealText:
                    Intent mWrite = new Intent(getApplicationContext(), MealWriteOut.class);  // 0
                    startActivity(mWrite);
                    break;
//                case R.id.mealCam:
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivity(cameraIntent);//
////                    break;
//
//                case R.id.mealGal:
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, 101);
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
        MealAdapterOut.CustomViewHolder viewHolder = (MealAdapterOut.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }

    @Override
    public void onClick(View v) {

    }
}