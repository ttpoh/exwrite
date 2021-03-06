package com.nova.exwrite.meal;

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
import com.nova.exwrite.exercise.ExEdit;
import com.nova.exwrite.exercise.ExList;
import com.nova.exwrite.exercise.ExUpdate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealEdit extends AppCompatActivity implements View.OnClickListener{
    EditText mETitle, mEtime, mEamount, mEMemo;
    Button btn_mEC, btn_mES;
    ImageButton btn_mE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    ArrayList<MealData> mEdataItem;

    private Intent intent;
    int pos, mnum;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_edit);

        intent = getIntent();

        mETitle = (EditText) findViewById(R.id.et_mEtitle);
        mEtime = (EditText) findViewById(R.id.et_mEtime);
        mEamount = (EditText) findViewById(R.id.et_mEamount);
        mEMemo = (EditText) findViewById(R.id.et_mEMemo);
        btn_mE_img = findViewById(R.id.mealEditImg);




//        byte[] arr = mEdataItem.get(pos).getMeal_pic();



        mETitle.setText(intent.getStringExtra("eMealT"));
        mEtime.setText(intent.getStringExtra("eMeals"));
        mEamount.setText(intent.getStringExtra("eMealt"));
        mEMemo.setText(intent.getStringExtra("eMealc"));

        pos = intent.getIntExtra("position1", -1);

        mnum = intent.getIntExtra("eMealN", +0);
        queue = Volley.newRequestQueue(this);

        btn_mEC = (Button) findViewById(R.id.btn_mEdit_cancle);
        btn_mES = (Button) findViewById(R.id.btn_mEdit_save);
//        btn_exE_img = (ImageButton) findViewById(R.id.btn_exEdit_img);

        btn_mEC.setOnClickListener(this);
        btn_mES.setOnClickListener(this);
//        btn_mE_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mETitle.equals("") && mEtime.equals("") && mEamount.equals("")&& mEMemo.equals("")) {//????????? ??????????????? ??????
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "????????? ????????? ?????????.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_exEdit_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_mEdit_save) {
            String mname = mETitle.getText().toString();
            String mtime = mEtime.getText().toString();
            String mamount = mEamount.getText().toString();
            String mmemo = mEMemo.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("?????? ???", response);
//            bodyData = new BodyData2(bodyweight1, bodyweight2, bodymuscle, bodyfat);
//            bodydata2.set(pos, bodyData);

                    Intent intent = new Intent(getApplicationContext(), MealList.class);
                    startActivity(intent);
                    finish();
                }
            };
            MealEditR mealEdit = new MealEditR(Integer.toString(mnum), mname, mtime, mamount, mmemo, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MealEdit.this);
            queue.add(mealEdit);
        }
        else if (v.getId() == R.id.mealEditImg) {
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

                    btn_mE_img.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
            }
        }
    }

}
