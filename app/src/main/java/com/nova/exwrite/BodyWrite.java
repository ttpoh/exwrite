package com.nova.exwrite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BodyWrite extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_write);


        Button btn_bodyC = (Button) findViewById(R.id.btn_bodywrite_cancle);
        Button btn_bodyS = (Button) findViewById(R.id.btn_bodywrite_save);
        ImageButton btn_body_img = (ImageButton) findViewById(R.id.btn_bodywrite_img);


        btn_bodyC.setOnClickListener(this);
        btn_bodyS.setOnClickListener(this);
        btn_body_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_bodywrite_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.btn_bodywrite_save) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == R.id.btn_bodywrite_img) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
//            Toast myToast2 = Toast.makeText(this.getApplicationContext(), "이미지 저장버튼", Toast.LENGTH_SHORT);
//            myToast2.show();
//            startActivityForResult(intent, REQUEST_CODE);

        }
    }
}
