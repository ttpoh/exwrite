package com.nova.exwrite.meal.logout;

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
import com.nova.exwrite.exercise.logout.ExDataOut;
import com.nova.exwrite.exercise.logout.ExListOut;
import com.nova.exwrite.meal.MealEditR;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealEditOut extends AppCompatActivity implements View.OnClickListener{
    EditText mETitle, mEtime, mEamount, mEMemo;
    Button btn_mEC, btn_mES;
    ImageButton btn_mE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    ArrayList<MealDataOut> mEdataItem;
    Gson gson;

    String sharedBody = "MDataOut";
    SharedPreferences sharedPreferences;

    Type typeMEdata = new TypeToken<ArrayList<MealDataOut>>() {
    }.getType();
    private Intent intent;
    int pos;


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


        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        String mealdata = sharedPreferences.getString("MDataOut", "");
        Log.d("sharedReceive",mealdata);
        if (mealdata.equals("")) {
            mEdataItem = new ArrayList<MealDataOut>();
            String shared_mEdata = gson.toJson(mEdataItem, typeMEdata);
            editor.putString("MDataOut", shared_mEdata);
            editor.commit();
        } else {

            mEdataItem = gson.fromJson(mealdata, typeMEdata);
        }

        pos = intent.getIntExtra("position1", -1);

        byte[] arr = mEdataItem.get(pos).getMeal_pic();
        img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        btn_mE_img.setImageBitmap(img);
        mETitle.setText(mEdataItem.get(pos).getMtitle());
        mEtime.setText(mEdataItem.get(pos).getMtime());
        mEamount.setText(mEdataItem.get(pos).getMamount());
        mEMemo.setText(mEdataItem.get(pos).getMcontents());


        btn_mEC = (Button) findViewById(R.id.btn_mEdit_cancle);
        btn_mES = (Button) findViewById(R.id.btn_mEdit_save);
        btn_mE_img = (ImageButton) findViewById(R.id.mealEditImg);

        btn_mEC.setOnClickListener(this);
        btn_mES.setOnClickListener(this);
        btn_mE_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mETitle.equals("") && mEtime.equals("") && mEamount.equals("")&& mEMemo.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
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

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_exEdit_img);
            sendBitmap = ((BitmapDrawable) btn_mE_img.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            float scale = (float) (1024 / (float) sendBitmap.getWidth());
            int image_w = (int) (sendBitmap.getWidth() * scale);
            int image_h = (int) (sendBitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] mImg = stream.toByteArray();

            gson = new Gson();
            MealDataOut mealData = new MealDataOut(mname, mtime, mamount, mmemo,mImg);

            String meal_data = sharedPreferences.getString("MDataOut", "");
            Log.d("shared 업로드 내용", meal_data);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (meal_data.equals("")) {

                mEdataItem = new ArrayList<MealDataOut>();
                String addMdata = gson.toJson(mEdataItem, typeMEdata);
                editor.putString("MDataOut", addMdata);
                editor.commit();
            } else {

                mEdataItem = gson.fromJson(meal_data, typeMEdata);
            }

            mEdataItem.set(pos, mealData);
            String sharedUpEex = gson.toJson(mEdataItem, typeMEdata);
            editor.putString("MDataOut", sharedUpEex);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), MealListOut.class);
            startActivity(intent);
            finish();
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
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
