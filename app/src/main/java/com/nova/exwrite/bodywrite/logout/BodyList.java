package com.nova.exwrite.bodywrite.logout;

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

public class BodyList extends AppCompatActivity implements View.OnClickListener, BodyAdapter.OnListItemSelectedInterface {

    RecyclerView recyclerView;
    private BodyAdapter bodyAdapter;
    String sharedBody = "bodyData";

    private ArrayList<BodyData> bodydataItem;
    private
    BodyAdapter adapter;
    Gson gson = new GsonBuilder().create();
    final Context context = this;
    Type typeBodydata = new TypeToken<ArrayList<BodyData>>() {
    }.getType();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodylist);

        recyclerView = findViewById(R.id.body_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String shared_bodydata = sharedPreferences.getString("BodyData", "");
        if (shared_bodydata.equals("")) {
            bodydataItem = new ArrayList<BodyData>();
            String sharedBodydata = gson.toJson(bodydataItem, typeBodydata);
            editor.putString("BodyData", sharedBodydata);
            editor.commit();
        } else {

            bodydataItem = gson.fromJson(shared_bodydata, typeBodydata);
        }

        bodyAdapter = new BodyAdapter(context, this, bodydataItem);
        recyclerView.setAdapter(bodyAdapter);

        Button Add_body = (Button) findViewById(R.id.btn_bodylist_add);
        Button Add_body_back = (Button) findViewById(R.id.btn_bodylist_back);

        Add_body.setOnClickListener(this);
        Add_body_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String body_data = sharedPreferences.getString("BodyData", "");
        if (body_data.equals("")) {
            bodydataItem = new ArrayList<BodyData>();
            String book_array = gson.toJson(bodydataItem, typeBodydata);
            editor.putString("bookData", book_array);
            editor.commit();
        } else {

            bodydataItem = gson.fromJson(body_data, typeBodydata);
        }

        Log.d(TAG, "arraybook : " + "사이즈" + bodydataItem.size());
        adapter = new BodyAdapter(context, this, bodydataItem);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_bodylist_add) {
            Intent intent = new Intent(getApplicationContext(), BodyWrite.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_bodylist_back) {

            finish();

        }
    }

    @Override
    public void onItemSelected(View v, int position) {
        BodyAdapter.CustomViewHolder viewHolder = (BodyAdapter.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }
}
