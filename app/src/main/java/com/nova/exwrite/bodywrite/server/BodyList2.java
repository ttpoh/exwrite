package com.nova.exwrite.bodywrite.server;


import static android.content.ContentValues.TAG;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.nova.exwrite.R;

import com.nova.exwrite.bodywrite.BodyWrite;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class BodyList2 extends AppCompatActivity implements View.OnClickListener, BodyAdapter2.OnListItemSelectedInterface {

    RecyclerView recyclerView;
    private BodyAdapter2 bodyAdapter2;


    final Context context = this;


    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodylist2);

        recyclerView = findViewById(R.id.body_rv2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        queue = Volley.newRequestQueue(this);
        bodyRecord();

        Button Add_body = (Button) findViewById(R.id.btn_bodylist_add2);
        Button Add_body_back = (Button) findViewById(R.id.btn_bodylist_back2);

        Add_body.setOnClickListener(this);
        Add_body_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_bodylist_add2) {
            Intent intent = new Intent(getApplicationContext(), BodyWrite2.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_bodylist_back2) {

            finish();
        }
    }
    @Override
    public void onItemSelected(View v, int position) {
        BodyAdapter2.CustomViewHolder viewHolder = (BodyAdapter2.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }
    public void bodyRecord() {
        String URL = "http://solution12441.dothome.co.kr/bodylist2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("body", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arraynick = jsonObject.getJSONArray("result");
                    ArrayList<BodyData2> bodydata2 = new ArrayList<BodyData2>();

                    for(int i = 0, j = arraynick.length(); i < j; i++ ) {
                        JSONObject obj = arraynick.getJSONObject(i);


                        obj.getInt("no");
                        obj.getString("weight");
                        obj.getString("muscle");
                        obj.getString("fat");
                        obj.getString("memo");
//                        obj.getString("memo");
//                       obj.getString("img_path");

                        BodyData2 bodyData2 = new BodyData2(obj.getInt("no"),obj.getString("weight"),obj.getString("muscle"),obj.getString("fat"),obj.getString("memo"));
//                        bodyData2.setBodyNickname(obj.getString("nickname"));
//                        bodyData2.setBodyweight(obj.getString("weight"));
//                        bodyData2.setBodymuscle(obj.getString("muscle"));
//                        bodyData2.setBodyfat(obj.getString("fat"));
//                        bodyData2.setBodyweight(obj.getString("memo"));
//                        bodyData2.setBodyweight(obj.getString("weight"));
                        bodydata2.add(bodyData2);

                    }

                    bodyAdapter2 = new BodyAdapter2(context, bodydata2);
                    recyclerView.setAdapter(bodyAdapter2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}
