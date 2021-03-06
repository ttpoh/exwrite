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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.server.BodyEUpdate;
import com.nova.exwrite.bodywrite.server.BodyEdit2;
import com.nova.exwrite.bodywrite.server.BodyList2;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExEdit extends AppCompatActivity implements View.OnClickListener{
    EditText exETitle, exEStart, exETime, exEContents;
    Button btn_exEC, btn_exES;
    ImageButton btn_exE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<ExData> exEdataItem;

    private Intent intent;
    int pos, exNum;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_edit);

        intent = getIntent();

        exETitle = (EditText) findViewById(R.id.et_exEtitle);
        exEStart = (EditText) findViewById(R.id.et_exEstart);
        exETime = (EditText) findViewById(R.id.et_exEtime);
        exEContents = (EditText) findViewById(R.id.et_exEcontents);
//        btn_exE_img = findViewById(R.id.btn_exEdit_img);


        exETitle.setText(intent.getStringExtra("eExT"));
        exEStart.setText(intent.getStringExtra("eExs"));
        exETime.setText(intent.getStringExtra("eExt"));
        exEContents.setText(intent.getStringExtra("eExc"));

        pos = intent.getIntExtra("pos", +0);

        exNum = intent.getIntExtra("eExN", +0);

        queue = Volley.newRequestQueue(this);



        btn_exEC = (Button) findViewById(R.id.btn_exEdit_cancle);
        btn_exES = (Button) findViewById(R.id.btn_exEdit_save);
//        btn_exE_img = (ImageButton) findViewById(R.id.btn_exEdit_img);



        btn_exEC.setOnClickListener(this);
        btn_exES.setOnClickListener(this);
//        btn_exE_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (exETitle.equals("") && exEStart.equals("") && exETime.equals("")&& exEContents.equals("")) {//????????? ??????????????? ??????
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "????????? ????????? ?????????.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_exEdit_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_exEdit_save) {
            String extitle = exETitle.getText().toString();
            String exstart = exEStart.getText().toString();
            String extime = exETime.getText().toString();
            String excontents = exEContents.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("?????? ???", response);
//

                    Intent intent = new Intent(getApplicationContext(), ExList.class);
                    startActivity(intent);
                    finish();
                }
            };
            ExUpdate exUpdate = new ExUpdate(Integer.toString(exNum), extitle, exstart, extime, excontents, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ExEdit.this);
            queue.add(exUpdate);
        }
        else if (v.getId() == R.id.btn_exEdit_img) {
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

                    btn_exE_img.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
            }
        }
    }

}
