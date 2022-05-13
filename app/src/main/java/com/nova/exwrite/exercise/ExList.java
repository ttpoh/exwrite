package com.nova.exwrite.exercise;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.server.BodyAdapter2;
import com.nova.exwrite.bodywrite.server.BodyData2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExList extends AppCompatActivity implements View.OnClickListener, ExAdapter.OnListItemSelectedInterface {

    RecyclerView recyclerView;
    private ExAdapter exAdapter;

    private ArrayList<ExData> exdataItem;

    final Context context = this;

    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;

    RequestQueue queue;
    String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", "0"); //키값, 디폴트값


        Log.d("아이디가 null이 아닐 때", "2");
        setContentView(R.layout.exlist);
        Button Add_ex_back = (Button) findViewById(R.id.btn_exlist_back);
        Add_ex_back.setOnClickListener(this);
        Button Add_ex = (Button) findViewById(R.id.btn_exlist_add);
        Add_ex.setOnClickListener(this);
//        }


        recyclerView = findViewById(R.id.ex_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        queue = Volley.newRequestQueue(this);
        exRecord();



        exAdapter = new ExAdapter(context, exdataItem);
        recyclerView.setAdapter(exAdapter);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_exlist_back || v.getId() == R.id.btn_exlist_logout_back) {
            finish();
        } else if (v.getId() == R.id.btn_exlist_add) {
            Intent intent = new Intent(getApplicationContext(), ExWrite.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemSelected(View v, int position) {
        ExAdapter.CustomViewHolder viewHolder = (ExAdapter.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

    }

    public void exRecord() {
//        String URL = "http://solution12441.dothome.co.kr/exList/exlist.php";

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ex", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arraynick = jsonObject.getJSONArray("result");
                    ArrayList<ExData> exdata = new ArrayList<ExData>();

                    for (int i = 0, j = arraynick.length(); i < j; i++) {
                        JSONObject obj = arraynick.getJSONObject(i);

                        obj.getInt("no");
                        obj.getString("id");
                        obj.getString("title");
                        obj.getString("start_time");
                        obj.getString("ex_time");
                        obj.getString("contents");
//                        obj.getString("memo");
//                       obj.getString("img");

                        ExData exData = new ExData(obj.getInt("no"), obj.getString("id"), obj.getString("title"), obj.getString("start_time"), obj.getString("ex_time"), obj.getString("contents"));
//                        bodyData2.setBodyNickname(obj.getString("nickname"));
//                        bodyData2.setBodyweight(obj.getString("weight"));
//                        bodyData2.setBodymuscle(obj.getString("muscle"));
//                        bodyData2.setBodyfat(obj.getString("fat"));
//                        bodyData2.setBodyweight(obj.getString("memo"));
//                        bodyData2.setBodyweight(obj.getString("weight"));

                        exdata.add(exData);

                    }

                    exAdapter = new ExAdapter(context, exdata);
                    recyclerView.setAdapter(exAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ExListReq exListReq = new ExListReq(loginID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ExList.this);
        queue.add(exListReq);
    }
}
