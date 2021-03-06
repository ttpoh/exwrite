package com.nova.exwrite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.nova.exwrite.bodywrite.logout.BodyList;
import com.nova.exwrite.bodywrite.server.BodyList2;
import com.nova.exwrite.bodywrite.server.BodyWrite2;
import com.nova.exwrite.exercise.ExList;
import com.nova.exwrite.exercise.ExWrite;
import com.nova.exwrite.exercise.logout.ExListOut;
import com.nova.exwrite.meal.MealList;
import com.nova.exwrite.meal.MealWrite;
import com.nova.exwrite.meal.logout.MealListOut;
import com.nova.exwrite.user.Join;
import com.nova.exwrite.user.Login;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomSheetDialog btmSD_write, btmSD_profile, btmSD_login;

    private TextView exwrite, mealwrite, bodywrite, profile_login, profile_join;
    private TextView welcomeView, profile, profileLogout;
    String loginID;

    RequestQueue queue;

    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;

    View btmDialogWrite, btmDialogLogin, btmDialogProfile;
    Intent loginId;

    boolean login = false;

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
        welcomeView = findViewById(R.id.welcomeView);

        bottomView.setOnNavigationItemSelectedListener(listener);

        LayoutInflater inflaterW = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflaterP = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflaterL = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        btmDialogWrite = inflaterW.inflate(R.layout.btm_dialog_write, null, false);
        btmDialogLogin = inflaterL.inflate(R.layout.btm_dialog_login, null, false);
        btmDialogProfile = inflaterL.inflate(R.layout.btm_dialog_profile, null, false);

        btmSD_write = new BottomSheetDialog(this);
        btmSD_write.setContentView(btmDialogWrite);


        profile = btmDialogLogin.findViewById(R.id.myProfile);
        profileLogout = btmDialogLogin.findViewById(R.id.profile_logout);

        queue = Volley.newRequestQueue(this);

        loginId = getIntent();
        loginID = loginId.getStringExtra("loginID");


        if (loginID == null) {
            login = false;
//            if (login == false) {    // ???????????? ??????
            btmDialogProfile = inflaterP.inflate(R.layout.btm_dialog_profile, null, false);
            btmSD_profile = new BottomSheetDialog(this);
            btmSD_profile.setContentView(btmDialogProfile);

            profile_login = btmDialogProfile.findViewById(R.id.profile_login);
            profile_join = btmDialogProfile.findViewById(R.id.profile_join);

            btmDialogLogin.setVisibility(View.INVISIBLE);
            btmDialogProfile.setVisibility(View.VISIBLE);

            profile_login.setOnClickListener(this);
            profile_join.setOnClickListener(this);
            welcomeView.setText("??????????????? ?????? ????????? ????????? ????????? ?????? ???????????????.");

//            }
        } else {
            sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
            SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
            loginID = prefs.getString("loginID", null); //??????, ????????????
            login = true;

            btmDialogLogin = inflaterP.inflate(R.layout.btm_dialog_login, null, false);

            btmSD_login = new BottomSheetDialog(this);
            btmSD_login.setContentView(btmDialogLogin);

            profile = btmDialogLogin.findViewById(R.id.myProfile);
            profileLogout = btmDialogLogin.findViewById(R.id.profile_logout);

            profile.setOnClickListener(this);
            profileLogout.setOnClickListener(this);

            btmDialogLogin.setVisibility(View.VISIBLE);
            btmDialogProfile.setVisibility(View.INVISIBLE);

            welcomeView.setText(loginID + " ????????? ???????????????.");
        }

//        if (login == false) {    // ???????????? ??????
//            btmDialogProfile = inflaterP.inflate(R.layout.btm_dialog_profile, null, false);
//            btmSD_profile = new BottomSheetDialog(this);
//            btmSD_profile.setContentView(btmDialogProfile);
//
//            profile_login = btmDialogProfile.findViewById(R.id.profile_login);
//            profile_join = btmDialogProfile.findViewById(R.id.profile_join);
//
//            btmDialogLogin.setVisibility(View.INVISIBLE);
//            btmDialogProfile.setVisibility(View.VISIBLE);
//
//            profile_login.setOnClickListener(this);
//            profile_join.setOnClickListener(this);
//            welcomeView.setText("????????? ??????????????? ?????? ????????? ????????? ????????? ????????? ???????????????.");
//
//        } else if (login == true) {
//
//
//            btmDialogLogin = inflaterP.inflate(R.layout.btm_dialog_login, null, false);
//            btmSD_login = new BottomSheetDialog(this);
//            btmSD_login.setContentView(btmDialogLogin);
//
//            profile = btmDialogLogin.findViewById(R.id.myProfile);
//            profileLogout = btmDialogLogin.findViewById(R.id.profile_logout);
//
//            profile.setOnClickListener(this);
//            profileLogout.setOnClickListener(this);
//
//            btmDialogLogin.setVisibility(View.VISIBLE);
//            btmDialogProfile.setVisibility(View.INVISIBLE);
//
//            welcomeView.setText(loginID + " ????????? ???????????????.");
//        }

        exwrite = btmDialogWrite.findViewById(R.id.ex_wr);
        mealwrite = btmDialogWrite.findViewById(R.id.meal_wr);
        bodywrite = btmDialogWrite.findViewById(R.id.body_wr);


        exwrite.setOnClickListener(this);
        mealwrite.setOnClickListener(this);
        bodywrite.setOnClickListener(this);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.exList) {
                    if (login == true) {
                        Intent exWrite = new Intent(MainActivity.this, ExList.class);
                        startActivity(exWrite);
                    } else {
                        Intent exWrite = new Intent(MainActivity.this, ExListOut.class);
                        startActivity(exWrite);
                    }
                } else if (item.getItemId() == R.id.mealList) {
                    if (login == true) {
                        Intent mWrite = new Intent(MainActivity.this, MealList.class);
                        startActivity(mWrite);
                    } else {
                        Intent intent = new Intent(MainActivity.this, MealListOut.class);
                        startActivity(intent);
                    }
                } else if (item.getItemId() == R.id.bodyList) {
                    if (login == true) {
                        Intent intent = new Intent(MainActivity.this, BodyList2.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, BodyList.class);
                        startActivity(intent);
                    }
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
            case R.id.obtion_btn_stopwatch:
                Intent todoIntent = new Intent(this, StopWatch.class);
                startActivity(todoIntent);
                break;
            case R.id.obtion_btn_share:
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
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                    login = true;
                    break;
                case R.id.ic_add:

                    btmSD_write.show();

//                        Toast myToast3 = Toast.makeText(getApplicationContext(), "????????? ??? ?????? ???????????????", Toast.LENGTH_SHORT);
//                        myToast3.show();
                    break;


                case R.id.ic_person:

                    if (loginID == null) {

                        btmSD_profile.show();

                    } else {
                        btmSD_login.show();
                    }
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
                Intent bodyWrite = new Intent(MainActivity.this, BodyWrite2.class);
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
            case R.id.myProfile:
                Intent profile = new Intent(MainActivity.this, Myprofile.class);
                startActivity(profile);
                break;
            case R.id.profile_logout:

                Intent profileLogout = new Intent(MainActivity.this, MainActivity.class);
                startActivity(profileLogout);
                login = false;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                finish();
                break;
        }
    }
}
