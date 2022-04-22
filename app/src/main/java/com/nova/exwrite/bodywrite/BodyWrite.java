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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.user.Login;
import com.nova.exwrite.user.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BodyWrite extends AppCompatActivity implements View.OnClickListener{
    EditText bodyWeight, bodyMuscle, bodyFat, bodyContents;
    Button btn_bodyC, btn_bodyS;
    ImageButton btn_body_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<BodyData> bodydataItem;

    String sharedBody = "bodyData";
    SharedPreferences sharedPreferences;

    Type arraylist_bodyData = new TypeToken<ArrayList<BodyData>>() {
    }.getType();

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

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);

        btn_bodyC.setOnClickListener(this);
        btn_bodyS.setOnClickListener(this);
        btn_body_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (bodyWeight.equals("") && bodyMuscle.equals("") && bodyFat.equals("")&& bodyContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_bodywrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_bodywrite_save) {

            String bodyweight = bodyWeight.getText().toString();
            String bodymuscle = bodyMuscle.getText().toString();
            String bodyfat = bodyFat.getText().toString();
            String bodycontents = bodyContents.getText().toString();

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_bodywrite_img);
            sendBitmap = ((BitmapDrawable) btn_body_img.getDrawable()).getBitmap();

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

                bodydataItem = new ArrayList<BodyData>();
                String addBodydata = gson.toJson(bodydataItem, arraylist_bodyData);
                editor.putString("BodyData", addBodydata);
                editor.commit();
            } else {

                bodydataItem = gson.fromJson(body_data, arraylist_bodyData);
            }

            bodydataItem.add(bodyData);

            String arraylist = gson.toJson(bodydataItem, arraylist_bodyData);
            Log.d("item",String.valueOf(bodydataItem.size()));
            editor.putString("BodyData", arraylist);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), BodyList.class);
            startActivity(intent);
            finish();
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
