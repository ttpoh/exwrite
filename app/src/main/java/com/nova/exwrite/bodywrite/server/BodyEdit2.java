package com.nova.exwrite.bodywrite.server;

import static android.content.ContentValues.TAG;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.exercise.ExList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BodyEdit2 extends AppCompatActivity implements View.OnClickListener{
    EditText bodyEwriter, bodyEweight, bodyEmuscle, bodyEfat, bodyEContents;
    Button btn_bodyEC, btn_bodyES;
    ImageButton btn_bodyE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;

    private ArrayList<BodyData2> bodydata2;

    private Intent intent;
    int pos, bodyNum;
    BodyData2 bodyData;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_edit);

        intent = getIntent();

//        bodyEwriter = (EditText) findViewById(R.id.et_bodyEweight);
        bodyEweight = (EditText) findViewById(R.id.et_bodyEweight);
        bodyEmuscle = (EditText) findViewById(R.id.et_bodyEmuscle);
        bodyEfat = (EditText) findViewById(R.id.et_bodyEfat);
        bodyEContents = (EditText) findViewById(R.id.et_bodyEcontents);
//        btn_bodyE_img = findViewById(R.id.btn_bodyEdit_img);

//        bodyEwriter.setText(intent.getStringExtra("ebodyW"));
        bodyEweight.setText(intent.getStringExtra("ebodyW"));
        bodyEmuscle.setText(intent.getStringExtra("ebodyw"));
        bodyEfat.setText(intent.getStringExtra("ebodym"));
        bodyEContents.setText(intent.getStringExtra("ebodyf"));
        pos = intent.getIntExtra("pos", +0);

        bodyNum = intent.getIntExtra("ebodyN", +0);

        queue = Volley.newRequestQueue(this);


        btn_bodyEC = (Button) findViewById(R.id.btn_bodyEdit_cancle);
        btn_bodyES = (Button) findViewById(R.id.btn_bodyEdit_save);
        btn_bodyE_img = (ImageButton) findViewById(R.id.btn_bodyEdit_img);

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


//            String bodywriter = bodyEwriter.getText().toString();
//            String bodynickname = bodyEweight.getText().toString();
            String bodyweight2 = bodyEweight.getText().toString();
            String bodymuscle = bodyEmuscle.getText().toString();
            String bodyfat = bodyEfat.getText().toString();
            String bodycontents = bodyEContents.getText().toString();

            Log.d("bodyweight편집",bodyweight2);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("편집 후", response);
//            bodyData = new BodyData2(bodyweight1, bodyweight2, bodymuscle, bodyfat);
//            bodydata2.set(pos, bodyData);

            Intent intent = new Intent(getApplicationContext(), BodyList2.class);
            startActivity(intent);
                    finish();
                }
            };
                BodyEUpdate bodyEUpdate = new BodyEUpdate(Integer.toString(bodyNum), bodyweight2, bodymuscle, bodyfat, bodycontents, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BodyEdit2.this);
                queue.add(bodyEUpdate);

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
