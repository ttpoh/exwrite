package com.nova.exwrite.exercise.logout;

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

public class ExEditOut extends AppCompatActivity implements View.OnClickListener{
    EditText exETitle, exEStart, exETime, exEContents;
    Button btn_exEC, btn_exES;
    ImageButton btn_exE_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;
    Gson gson;
    ArrayList<ExDataOut> exEdataItem;

    String sharedBody = "ExDataOut";
    SharedPreferences sharedPreferences;

    Type typeExEdata = new TypeToken<ArrayList<ExDataOut>>() {
    }.getType();
    private Intent intent;
    int pos, exNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_edit);

        intent = getIntent();

        exETitle = (EditText) findViewById(R.id.et_exEtitle);
        exEStart = (EditText) findViewById(R.id.et_exEstart);
        exETime = (EditText) findViewById(R.id.et_exEtime);
        exEContents = (EditText) findViewById(R.id.et_exEcontents);
        btn_exE_img = findViewById(R.id.btn_exEdit_img);


        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        String exdata = sharedPreferences.getString("ExDataOut", "");
        Log.d("sharedReceive",exdata);
        if (exdata.equals("")) {
            exEdataItem = new ArrayList<ExDataOut>();
            String shared_bodyEdata = gson.toJson(exEdataItem, typeExEdata);
            editor.putString("ExDataOut", shared_bodyEdata);
            editor.commit();
        } else {

            exEdataItem = gson.fromJson(exdata, typeExEdata);
        }

        pos = intent.getIntExtra("position1", -1);

        byte[] arr = exEdataItem.get(pos).getEx_pic();
        img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        btn_exE_img.setImageBitmap(img);
        exETitle.setText(exEdataItem.get(pos).getExtitle());
        exEStart.setText(exEdataItem.get(pos).getExstart());
        exETime.setText(exEdataItem.get(pos).getExtime());
        exEContents.setText(exEdataItem.get(pos).getExcontents());


        btn_exEC = (Button) findViewById(R.id.btn_exEdit_cancle);
        btn_exES = (Button) findViewById(R.id.btn_exEdit_save);
        btn_exE_img = (ImageButton) findViewById(R.id.btn_exEdit_img);



        btn_exEC.setOnClickListener(this);
        btn_exES.setOnClickListener(this);
        btn_exE_img.setOnClickListener(this);

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

            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_exEdit_img);
            sendBitmap = ((BitmapDrawable) btn_exE_img.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            float scale = (float) (1024 / (float) sendBitmap.getWidth());
            int image_w = (int) (sendBitmap.getWidth() * scale);
            int image_h = (int) (sendBitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] exImg = stream.toByteArray();

            gson = new Gson();
            ExDataOut exData = new ExDataOut(extitle, exstart, extime, excontents,exImg);

            String ex_data = sharedPreferences.getString("ExDataOut", "");
            Log.d("shared ????????? ??????", ex_data);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (ex_data.equals("")) {

                exEdataItem = new ArrayList<ExDataOut>();
                String addBodydata = gson.toJson(exEdataItem, typeExEdata);
                editor.putString("exData", addBodydata);
                editor.commit();
            } else {

                exEdataItem = gson.fromJson(ex_data, typeExEdata);
            }

            exEdataItem.set(pos, exData);
            String sharedUpEex = gson.toJson(exEdataItem, typeExEdata);
            editor.putString("ExDataOut", sharedUpEex);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), ExListOut.class);
                    startActivity(intent);
                    finish();

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
