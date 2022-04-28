package com.nova.exwrite.exercise;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.server.BodyList2;
import com.nova.exwrite.bodywrite.server.BodyRequest2;
import com.nova.exwrite.bodywrite.server.BodyWrite2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExWrite extends AppCompatActivity implements View.OnClickListener{
    EditText exTitle, exStart, exTime, exContents;
    TextView exWriter;
    Button btn_exC, btn_exS;
    ImageButton btn_ex_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;

    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;

    RequestQueue queue;
    String loginID;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_write);
        intent = getIntent();

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs =getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", "0"); //키값, 디폴트값

        exWriter = (TextView) findViewById(R.id.exWriter);
        exTitle = (EditText) findViewById(R.id.et_extitle);
        exStart = (EditText) findViewById(R.id.et_exstart);
        exTime = (EditText) findViewById(R.id.et_extime);
        exContents = (EditText) findViewById(R.id.et_excontents);

        exTime.setText(intent.getStringExtra("exTime"));


        btn_exC = (Button) findViewById(R.id.btn_exwrite_cancle);
        btn_exS = (Button) findViewById(R.id.btn_exwrite_save);
        btn_ex_img = (ImageButton) findViewById(R.id.btn_exwrite_img);

        btn_exC.setOnClickListener(this);
        btn_exS.setOnClickListener(this);
        btn_ex_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (exTitle.equals("") && exStart.equals("") && exTime.equals("")&& exContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_exwrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_exwrite_save) {

            String extitle2 = exTitle.getText().toString();
            String exstart = exStart.getText().toString();
            String extime = exTime.getText().toString();
            String excontents = exContents.getText().toString();

            System.out.println("loginid" + loginID);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("hongchul" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {

                            Intent intent = new Intent(ExWrite.this, ExList.class);//
                            startActivity(intent);
                            finish();
                        } else { // 로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            ExWrReq exWrReq = new ExWrReq(loginID, extitle2, exstart, extime, excontents, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ExWrite.this);
            queue.add(exWrReq);

        }
        else if (v.getId() == R.id.btn_exwrite_img) {
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
                    btn_ex_img.setImageBitmap(img);

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
