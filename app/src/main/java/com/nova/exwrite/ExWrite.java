package com.nova.exwrite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ExWrite extends AppCompatActivity implements View.OnClickListener{
    EditText exTitle, exStart, exTime, exContents;
    Button btn_exC, btn_exS;
    ImageButton btn_ex_img;
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


        btn_exC.setOnClickListener(this);
        btn_exS.setOnClickListener(this);
        btn_ex_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == R.id.btn_exwrite_img) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);


        }
    }



}
