package com.nova.exwrite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    private ExAdapter exAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exlist);

        recyclerView = findViewById(R.id.ex_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//        exAdapter = new ExAdapter(context, this, arrayBook);
        recyclerView.setAdapter(exAdapter);

        Button Add_ex = (Button) findViewById(R.id.btn_b_add);
        Button Add_ex_back = (Button) findViewById(R.id.btn_b_back);

        Add_ex.setOnClickListener(this);
        Add_ex_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
