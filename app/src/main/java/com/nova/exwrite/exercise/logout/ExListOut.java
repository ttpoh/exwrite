package com.nova.exwrite.exercise.logout;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;


import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExListOut extends AppCompatActivity implements View.OnClickListener, ExAdapterOut.OnListItemSelectedInterface {

    RecyclerView recyclerView;
    private ExAdapterOut exAdapterOut;
    String sharedBody = "ExDataOut";

    private ArrayList<ExDataOut> exdataItem;

    Gson gson = new GsonBuilder().create();
    final Context context = this;
    Type typeExdata = new TypeToken<ArrayList<ExDataOut>>() {
    }.getType();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exlist);

        recyclerView = findViewById(R.id.ex_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String shared_exdata = sharedPreferences.getString("ExDataOut", "");
        if (shared_exdata.equals("")) {
            exdataItem = new ArrayList<ExDataOut>();
            String sharedExdata = gson.toJson(exdataItem, typeExdata);
            editor.putString("ExDataOut", sharedExdata);
            editor.commit();
        } else {

            exdataItem = gson.fromJson(shared_exdata, typeExdata);
        }

        exAdapterOut = new ExAdapterOut(context, this, exdataItem);
        recyclerView.setAdapter(exAdapterOut);

        Button Add_ex = (Button) findViewById(R.id.btn_exlist_add);
        Button Add_ex_back = (Button) findViewById(R.id.btn_exlist_back);

        Add_ex.setOnClickListener(this);
        Add_ex_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String body_data = sharedPreferences.getString("ExDataOut", "");
        if (body_data.equals("")) {
            exdataItem = new ArrayList<ExDataOut>();
            String ex_array = gson.toJson(exdataItem, typeExdata);
            editor.putString("ExDataOut", ex_array);
            editor.commit();
        } else {

            exdataItem = gson.fromJson(body_data, typeExdata);
        }

        Log.d(TAG, "arraybook : " + "사이즈" + exdataItem.size());
        exAdapterOut = new ExAdapterOut(context, this, exdataItem);
        recyclerView.setAdapter(exAdapterOut);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_exlist_add) {
            Intent intent = new Intent(getApplicationContext(), ExWriteOut.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_exlist_back) {

            finish();

        }
    }

    @Override
    public void onItemSelected(View v, int position) {
        ExAdapterOut.CustomViewHolder viewHolder = (ExAdapterOut.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }
}
