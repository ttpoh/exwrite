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

public class ExWrite extends AppCompatActivity implements View.OnClickListener{
    EditText exTitle, exStart, exTime, exContents;
    Button btn_exC, btn_exS;
    ImageButton btn_ex_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<ExData> exdataItem;

    String sharedEx = "exData";
    SharedPreferences sharedPreferences;

    Type arraylist_exData = new TypeToken<ArrayList<ExData>>() {
    }.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_write);


        exTitle = (EditText) findViewById(R.id.et_extitle);
        exStart = (EditText) findViewById(R.id.et_exstart);
        exTime = (EditText) findViewById(R.id.et_extime);
        exContents = (EditText) findViewById(R.id.et_excontents);

        btn_exC = (Button) findViewById(R.id.btn_exwrite_cancle);
        btn_exS = (Button) findViewById(R.id.btn_exwrite_save);
        btn_ex_img = (ImageButton) findViewById(R.id.btn_exwrite_img);

        sharedPreferences = getSharedPreferences(sharedEx, MODE_PRIVATE);

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
            String extitle = exTitle.getText().toString();
            String exstart = exStart.getText().toString();
            String extime = exTime.getText().toString();
            String excontents = exContents.getText().toString();

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_exwrite_img);
            sendBitmap = ((BitmapDrawable) btn_ex_img.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            float scale = (float) (1024 / (float) sendBitmap.getWidth());
            int image_w = (int) (sendBitmap.getWidth() * scale);
            int image_h = (int) (sendBitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] exImg = stream.toByteArray();

            gson = new Gson();
            ExData exData = new ExData(extitle, exstart, extime, excontents,exImg);

            String ex_data = sharedPreferences.getString("ExData", "");
            Log.d("shared 업로드 내용", ex_data);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (ex_data.equals("")) {

                exdataItem = new ArrayList<ExData>();
                String addExdata = gson.toJson(exdataItem, arraylist_exData);
                editor.putString("ExData", addExdata);
                editor.commit();
            } else {

                exdataItem = gson.fromJson(ex_data, arraylist_exData);
            }

            exdataItem.add(exData);

            String arraylist = gson.toJson(exdataItem, arraylist_exData);
            Log.d("item",String.valueOf(exdataItem.size()));
            editor.putString("ExData", arraylist);
            editor.commit();



            Intent intent = new Intent(getApplicationContext(), ExList.class);
            startActivity(intent);
            finish();
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
