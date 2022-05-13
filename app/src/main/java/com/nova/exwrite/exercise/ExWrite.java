package com.nova.exwrite.exercise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.fido.fido2.api.common.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.server.BodyList2;
import com.nova.exwrite.bodywrite.server.BodyRequest2;
import com.nova.exwrite.bodywrite.server.BodyWrite2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExWrite extends AppCompatActivity implements View.OnClickListener {
    EditText exTitle, exStart, exTime, exContents;
    TextView exWriter;
    Button btn_exC, btn_exS;
    ImageButton btn_ex_img;
    Bitmap sendBitmap, img;
    private static final int REQUEST_CODE = 0;

    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;
    private String imageUrl = "";

    File tempFile;

    RequestQueue queue;
    String loginID;
    Intent intent;
    private int GALLEY_CODE = 10;
    String encodeImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_write);
        intent = getIntent();

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", "0"); //키값, 디폴트값

        exWriter = (TextView) findViewById(R.id.exWriter);
        exTitle = (EditText) findViewById(R.id.et_extitle);
        exStart = (EditText) findViewById(R.id.et_exstart);
        exTime = (EditText) findViewById(R.id.et_extime);
        exContents = (EditText) findViewById(R.id.et_excontents);

        exTime.setText(intent.getStringExtra("exTime"));


        btn_exC = (Button) findViewById(R.id.btn_exwrite_cancle);
        btn_exS = (Button) findViewById(R.id.btn_exwrite_save);
        btn_ex_img = (ImageButton) findViewById(R.id.btn_exwrite_img);

        btn_exC.setOnClickListener(this);
        btn_exS.setOnClickListener(this);
        btn_ex_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (exTitle.equals("") && exStart.equals("") && exTime.equals("") && exContents.equals("")) {//빈값이 넘어올때의 처리
            Toast myToast3 = Toast.makeText(this.getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            myToast3.show();
        }
        if (v.getId() == R.id.btn_exwrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_exwrite_save) {

            String extitle2 = exTitle.getText().toString();
            String exstart = exStart.getText().toString();
            String extime = exTime.getText().toString();
            String excontents = exContents.getText().toString();

//            sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.btn_bodywrite_img);
//            sendBitmap = ((BitmapDrawable) btn_ex_img.getDrawable()).getBitmap();
//
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            float scale = (float) (1024 / (float) sendBitmap.getWidth());
//            int image_w = (int) (sendBitmap.getWidth() * scale);
//            int image_h = (int) (sendBitmap.getHeight() * scale);
//            Bitmap resize = Bitmap.createScaledBitmap(sendBitmap, image_w, image_h, true);
//            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] exImg = stream.toByteArray();
//
//            encodeImageString = android.util.Base64.encodeToString(exImg, Base64.DEFAULT);


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("hongchul" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {

                            Intent intent = new Intent(ExWrite.this, ExList.class);//
                            startActivity(intent);
                            finish();
                        } else { // 로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            ExWrReq exWrReq = new ExWrReq(loginID, extitle2, exstart, extime, excontents, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ExWrite.this);
            queue.add(exWrReq);

        } else if (v.getId() == R.id.btn_exwrite_img) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, REQUEST_CODE);

        }
    }
//    public String getPath(Uri uri)
//    {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        startManagingCursor(cursor);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLEY_CODE) {
//
//            Uri photoUri = data.getData();
//
//            Cursor cursor = null;
//
//            try {
//                /*
//                 *  Uri 스키마를
//                 *  content:/// 에서 file:/// 로  변경한다.
//                 */
//                String[] proj = { MediaStore.Images.Media.DATA };
//
//                assert photoUri != null;
//                cursor = getContentResolver().query(photoUri, proj, null, null, null);
//
//                assert cursor != null;
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//                cursor.moveToFirst();
//
//                tempFile = new File(cursor.getString(column_index));
//
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//
//            setImage();
//
//        }
//    }
//    private void setImage() {
//
//        btn_ex_img = findViewById(R.id.btn_exwrite_img);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
//
//        btn_ex_img.setImageBitmap(originalBm);
//
//    }

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
                    encodeBitmapImage(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }

}
