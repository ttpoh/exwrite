package com.nova.exwrite.exercise;

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

public class ExList extends AppCompatActivity implements View.OnClickListener, ExAdapter.OnListItemSelectedInterface {

    RecyclerView recyclerView;
    private ExAdapter exAdapter;
    String sharedEx = "exData";

    private ArrayList<ExData> exdataItem;
    private ExAdapter adapter;
    Gson gson = new GsonBuilder().create();
    final Context context = this;
    Type typeExdata = new TypeToken<ArrayList<ExData>>() {
    }.getType();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exlist);

        recyclerView = findViewById(R.id.ex_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences(sharedEx, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String shared_exdata = sharedPreferences.getString("ExData", "");
        if (shared_exdata.equals("")) {
            exdataItem = new ArrayList<ExData>();
            String sharedExdata = gson.toJson(exdataItem, typeExdata);
            editor.putString("ExData", sharedExdata);
            editor.commit();
        } else {

            exdataItem = gson.fromJson(shared_exdata, typeExdata);
        }

        Log.d(TAG, "arrayconcert : " + "사이즈" + exdataItem.size());
        exAdapter = new ExAdapter(context, this, exdataItem);
        recyclerView.setAdapter(exAdapter);

        Button Add_ex = (Button) findViewById(R.id.btn_exlist_add);
        Button Add_ex_back = (Button) findViewById(R.id.btn_exlist_back);

        Add_ex.setOnClickListener(this);
        Add_ex_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String ex_Edata = sharedPreferences.getString("ExData", "");
        if (ex_Edata.equals("")) {
            exdataItem = new ArrayList<ExData>();
            String book_array = gson.toJson(exdataItem, typeExdata);
            editor.putString("bookData", book_array);
            editor.commit();
        } else {

            exdataItem = gson.fromJson(ex_Edata, typeExdata);
        }

        Log.d(TAG, "arraybook : " + "사이즈" + exdataItem.size());
        adapter = new ExAdapter(context, this, exdataItem);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_exlist_add) {
            Intent intent = new Intent(getApplicationContext(), ExWrite.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_exlist_back) {

            finish();

        }
    }

    @Override
    public void onItemSelected(View v, int position) {
        ExAdapter.CustomViewHolder viewHolder = (ExAdapter.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }
}
