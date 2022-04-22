package com.nova.exwrite.bodywrite.server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.BodyData;
import com.nova.exwrite.bodywrite.BodyList;
import com.nova.exwrite.user.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BodyWrite2 extends AppCompatActivity implements View.OnClickListener {
    EditText bodyWeight, bodyMuscle, bodyFat, bodyContents;
    Button btn_bodyC, btn_bodyS;
    ImageButton btn_body_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<BodyData2> bodydataItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_write);

        bodyWeight = (EditText) findViewById(R.id.body_weight);
        bodyMuscle = (EditText) findViewById(R.id.body_muscle);
        bodyFat = (EditText) findViewById(R.id.body_fat);
        bodyContents = (EditText) findViewById(R.id.body_contents);


        btn_bodyC = (Button) findViewById(R.id.btn_bodywrite_cancle);
        btn_bodyS = (Button) findViewById(R.id.btn_bodywrite_save);
        btn_body_img = (ImageButton) findViewById(R.id.btn_bodywrite_img);


        btn_bodyC.setOnClickListener(this);
        btn_bodyS.setOnClickListener(this);
        btn_body_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (bodyWeight.equals("") && bodyMuscle.equals("") && bodyFat.equals("") && bodyContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_bodywrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_bodywrite_save) {

            String bodyweight1 = bodyWeight.getText().toString();
            String bodyweight2 = bodyWeight.getText().toString();
            String bodymuscle = bodyMuscle.getText().toString();
            String bodyfat = bodyFat.getText().toString();
            String bodymemo = bodyContents.getText().toString();

//            BodyData2 bodyData = new BodyData2(bodywriter, bodyweight, bodymuscle, bodyfat, bodymemo);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("hongchul" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {


//                            Toast.makeText(getApplicationContext(), "item 추가", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BodyWrite2.this, BodyList2.class);
//
                            startActivity(intent);
                            finish();
                        } else { // 로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            BodyRequest2 bodyRequest2 = new BodyRequest2(bodyweight1, bodyweight2, bodymuscle, bodyfat, bodymemo, responseListener);
            RequestQueue queue = Volley.newRequestQueue(BodyWrite2.this);
            queue.add(bodyRequest2);

    }
        else if (v.getId() == R.id.btn_bodywrite_img) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);

    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    btn_body_img.setImageBitmap(img);

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
