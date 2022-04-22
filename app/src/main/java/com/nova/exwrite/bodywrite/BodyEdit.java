package com.nova.exwrite.bodywrite;

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
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.exercise.ExList;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BodyEdit extends AppCompatActivity implements View.OnClickListener{
    EditText bodyEweight, bodyEmuscle, bodyEfat, bodyEContents;
    Button btn_bodyEC, btn_bodyES;
    ImageButton btn_bodyE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<BodyData> bodyEdataItem;

    String sharedBody = "bodyData";
    SharedPreferences sharedPreferences;

    Type typeBodyEdata = new TypeToken<ArrayList<BodyData>>() {
    }.getType();
    private Intent intent;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.body_edit);

        intent = getIntent();

        bodyEweight = (EditText) findViewById(R.id.et_bodyEweight);
        bodyEmuscle = (EditText) findViewById(R.id.et_bodyEmuscle);
        bodyEfat = (EditText) findViewById(R.id.et_bodyEfat);
        bodyEContents = (EditText) findViewById(R.id.et_bodyEcontents);
        btn_bodyE_img = findViewById(R.id.btn_bodyEdit_img);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        String bodydata = sharedPreferences.getString("BodyData", "");
        Log.d("sharedReceive",bodydata);
        if (bodydata.equals("")) {
            bodyEdataItem = new ArrayList<BodyData>();
            String shared_bodyEdata = gson.toJson(bodyEdataItem, typeBodyEdata);
            editor.putString("bodyData", shared_bodyEdata);
            editor.commit();
        } else {

            bodyEdataItem = gson.fromJson(bodydata, typeBodyEdata);
        }

        pos = intent.getIntExtra("position1", -1);

        byte[] arr = bodyEdataItem.get(pos).getBody_pic();
        img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        btn_bodyE_img.setImageBitmap(img);
        bodyEweight.setText(bodyEdataItem.get(pos).getBodyweight());
        bodyEweight.setText(bodyEdataItem.get(pos).getBodyweight());
        bodyEmuscle.setText(bodyEdataItem.get(pos).getBodymuscle());
        bodyEfat.setText(bodyEdataItem.get(pos).getBodyfat());
        bodyEContents.setText(bodyEdataItem.get(pos).getBodycontents());



        btn_bodyEC = (Button) findViewById(R.id.btn_bodyEdit_cancle);
        btn_bodyES = (Button) findViewById(R.id.btn_bodyEdit_save);
        btn_bodyE_img = (ImageButton) findViewById(R.id.btn_bodyEdit_img);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);

        btn_bodyEC.setOnClickListener(this);
        btn_bodyES.setOnClickListener(this);
        btn_bodyE_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (bodyEweight.equals("") && bodyEmuscle.equals("") && bodyEfat.equals("")&& bodyEContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_bodyEdit_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_bodyEdit_save) {



            String bodyweight = bodyEweight.getText().toString();
            String bodymuscle = bodyEmuscle.getText().toString();
            String bodyfat = bodyEfat.getText().toString();
            String bodycontents = bodyEContents.getText().toString();

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_bodyEdit_img);
            sendBitmap = ((BitmapDrawable) btn_bodyE_img.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            float scale = (float) (1024 / (float) sendBitmap.getWidth());
            int image_w = (int) (sendBitmap.getWidth() * scale);
            int image_h = (int) (sendBitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bodyImg = stream.toByteArray();

            gson = new Gson();
            BodyData bodyData = new BodyData(bodyweight, bodymuscle, bodyfat, bodycontents,bodyImg);

            String body_data = sharedPreferences.getString("BodyData", "");
            Log.d("shared 업로드 내용", body_data);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (body_data.equals("")) {

                bodyEdataItem = new ArrayList<BodyData>();
                String addBodydata = gson.toJson(bodyEdataItem, typeBodyEdata);
                editor.putString("BodyData", addBodydata);
                editor.commit();
            } else {

                bodyEdataItem = gson.fromJson(body_data, typeBodyEdata);
            }

            bodyEdataItem.set(pos, bodyData);
            String sharedUpEex = gson.toJson(bodyEdataItem, typeBodyEdata);
            editor.putString("BodyData", sharedUpEex);
            editor.commit();


            Intent intent = new Intent(getApplicationContext(), BodyList.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == R.id.btn_bodyEdit_img) {
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

                    btn_bodyE_img.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
