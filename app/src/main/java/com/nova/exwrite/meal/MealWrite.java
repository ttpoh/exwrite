package com.nova.exwrite.meal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MealWrite extends AppCompatActivity implements View.OnClickListener{

    EditText mTitle, mAmount, mTime, mContents;
    Button btn_mC, btn_mS;
    ImageButton btn_m_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    ArrayList<MealData> mdataItem;

    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;
    String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_write);

        mTitle = (EditText) findViewById(R.id.et_mtitle);
        mAmount = (EditText) findViewById(R.id.et_mealg);
        mTime = (EditText) findViewById(R.id.et_mtime);
        mContents = (EditText) findViewById(R.id.et_mMemo);

        btn_mC = (Button) findViewById(R.id.btn_mwrite_cancle);
        btn_mS = (Button) findViewById(R.id.btn_mwrite_save);
        btn_m_img = (ImageButton) findViewById(R.id.mealImg);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", "0"); //키값, 디폴트값


        btn_mC.setOnClickListener(this);
        btn_mS.setOnClickListener(this);
        btn_m_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mTitle.equals("") && mAmount.equals("") && mTime.equals("")&& mContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_mwrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_mwrite_save) {

            String mtitle2 = mTitle.getText().toString();
            String mtime = mTime.getText().toString();
            String mamount = mAmount.getText().toString();
            String mcontents = mContents.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("hongchul" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {


//                            Toast.makeText(getApplicationContext(), "item 추가", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MealWrite.this, MealList.class);
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
            MealWrReq mealWrReq = new MealWrReq(loginID, mtitle2, mtime, mamount, mcontents, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MealWrite.this);
            queue.add(mealWrReq);

        }
        else if (v.getId() == R.id.mealImg) {
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

                    btn_m_img.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
