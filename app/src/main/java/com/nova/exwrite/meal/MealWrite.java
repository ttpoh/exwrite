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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealWrite extends AppCompatActivity implements View.OnClickListener{

    EditText mTitle, mAmount, mTime, mContents;
    Button btn_mC, btn_mS;
    ImageButton btn_m_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<mealData> mdataItem;

    String sharedEx = "mData";
    SharedPreferences sharedPreferences;

    Type arraylist_mData = new TypeToken<ArrayList<mealData>>() {
    }.getType();

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

        sharedPreferences = getSharedPreferences(sharedEx, MODE_PRIVATE);

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
            String mtitle = mTitle.getText().toString();
            String mtime = mTime.getText().toString();
            String mamount = mAmount.getText().toString();
            String mcontents = mContents.getText().toString();

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.mealImg);
            sendBitmap = ((BitmapDrawable) btn_m_img.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            float scale = (float) (1024 / (float) sendBitmap.getWidth());
            int image_w = (int) (sendBitmap.getWidth() * scale);
            int image_h = (int) (sendBitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] mImg = stream.toByteArray();

            gson = new Gson();
            mealData mData = new mealData(mtitle, mtime, mamount, mcontents,mImg);

            String m_data = sharedPreferences.getString("mealData", "");
            Log.d("shared 업로드 내용", m_data);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (m_data.equals("")) {

                mdataItem = new ArrayList<mealData>();
                String addExdata = gson.toJson(mdataItem, arraylist_mData);
                editor.putString("mealData", addExdata);
                editor.commit();
            } else {

                mdataItem = gson.fromJson(m_data, arraylist_mData);
            }

            mdataItem.add(mData);

            String arraylist = gson.toJson(mdataItem, arraylist_mData);
            Log.d("item",String.valueOf(mdataItem.size()));
            editor.putString("mealData", arraylist);
            editor.commit();



            Intent intent = new Intent(getApplicationContext(), MealList.class);
            startActivity(intent);
            finish();
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
