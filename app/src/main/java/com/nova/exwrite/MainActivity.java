package com.nova.exwrite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomSheetDialog btmSD_write, btmSD_profile;
    private TextView exwrite, mealwrite, bodywrite, profile_login, profile_join;

//    private bottomSheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_ham);
        actionBar.setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);

        bottomView.setOnNavigationItemSelectedListener(listener);

        LayoutInflater inflaterW = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflaterP = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View btmDialogWrite = inflaterW.inflate(R.layout.btm_dialog_write, null, false);
        View btmDialogProfile = inflaterP.inflate(R.layout.btm_dialog_profile, null, false);

        btmSD_write = new BottomSheetDialog(this);
        btmSD_write.setContentView(btmDialogWrite);

        btmSD_profile = new BottomSheetDialog(this);
        btmSD_profile.setContentView(btmDialogProfile);

        profile_login = btmDialogProfile.findViewById(R.id.profile_login);
        profile_join = btmDialogProfile.findViewById(R.id.profile_join);

        exwrite = btmDialogWrite.findViewById(R.id.ex_wr);
        mealwrite = btmDialogWrite.findViewById(R.id.meal_wr);
        bodywrite = btmDialogWrite.findViewById(R.id.body_wr);

        exwrite.setOnClickListener(this);
        mealwrite.setOnClickListener(this);
        bodywrite.setOnClickListener(this);

        profile_login.setOnClickListener(this);
        profile_join.setOnClickListener(this);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.exList) {

                    Intent intent = new Intent(MainActivity.this, ExList.class);
                    startActivity(intent);
//                    drawerLayout.closeDrawers();

                } else if (item.getItemId() == R.id.mealList) {
                    Intent intent = new Intent(MainActivity.this, MealList.class);
                    startActivity(intent);
//                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainbar_obtion, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(navigationView);
                break;
            }
            case R.id.obtion_btn_search:
//                Intent intent = new Intent(MainActivity.this, Activity_map.class);
//                startActivity(intent);
                break;
            case R.id.obtion_btn_todo:
//                Intent todoIntent = new Intent(this, Activity_todolist.class);
//                startActivity(todoIntent);
                break;
        }


        return super.

                onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ic_home:
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);  // 0
                    startActivity(intent1);
                    break;
                case R.id.ic_add:

                    btmSD_write.show();
                    break;

                case R.id.ic_person:

                    btmSD_profile.show();
                    break;
            }

            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ex_wr:
                Intent exWrite = new Intent(MainActivity.this, ExWrite.class);
                startActivity(exWrite);
                break;
            case R.id.meal_wr:
                Intent mealWrite = new Intent(MainActivity.this, MealWrite.class);
                startActivity(mealWrite);
                break;
            case R.id.body_wr:
                Intent bodyWrite = new Intent(MainActivity.this, BodyWrite.class);
                startActivity(bodyWrite);
                break;
            case R.id.profile_login:
                Intent profileLogin = new Intent(MainActivity.this, Login.class);
                startActivity(profileLogin);
                break;
            case R.id.profile_join:
                Intent profileJoin = new Intent(MainActivity.this, Join.class);
                startActivity(profileJoin);
                break;

        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.profile_login:
//                Intent profileLogin = new Intent(MainActivity.this, Login.class);
//                startActivity(profileLogin);
//                break;
//            case R.id.profile_join:
//                Intent profileJoin = new Intent(MainActivity.this, Join.class);
//                startActivity(profileJoin);
//                break;
//        }
    }
