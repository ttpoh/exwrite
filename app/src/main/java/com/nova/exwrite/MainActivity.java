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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.nova.exwrite.bodywrite.BodyList;
import com.nova.exwrite.bodywrite.BodyWrite;
import com.nova.exwrite.bodywrite.server.BodyList2;
import com.nova.exwrite.bodywrite.server.BodyWrite2;
import com.nova.exwrite.exercise.ExAdapter;
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.exercise.ExList;
import com.nova.exwrite.exercise.ExWrite;
import com.nova.exwrite.exercise.logout.ExListOut;
import com.nova.exwrite.meal.MealList;
import com.nova.exwrite.meal.MealWrite;
import com.nova.exwrite.meal.logout.MealListOut;
import com.nova.exwrite.user.Join;
import com.nova.exwrite.user.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
//            if (login == false) {    // 로그아웃 상태
                btmDialogProfile = inflaterP.inflate(R.layout.btm_dialog_profile, null, false);
                btmSD_profile = new BottomSheetDialog(this);
                btmSD_profile.setContentView(btmDialogProfile);

                profile_login = btmDialogProfile.findViewById(R.id.profile_login);
                profile_join = btmDialogProfile.findViewById(R.id.profile_join);

                btmDialogLogin.setVisibility(View.INVISIBLE);
                btmDialogProfile.setVisibility(View.VISIBLE);

                profile_login.setOnClickListener(this);
                profile_join.setOnClickListener(this);
                welcomeView.setText("회원정보를 통해 로그인 하시면 기록이 영구 저장됩니다.");

//            }
        } else {
            sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
            SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
            loginID = prefs.getString("loginID", null); //키값, 디폴트값
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

            welcomeView.setText(loginID + " 회원님 환영합니다.");
        }

//        if (login == false) {    // 로그아웃 상태
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
//            welcomeView.setText("하단부 회원정보를 통해 로그인 하시면 기록한 내용이 저장됩니다.");
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
//            welcomeView.setText(loginID + " 회원님 환영합니다.");
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

//                    drawerLayout.closeDrawers();
                } else if (item.getItemId() == R.id.mealList) {
                    if (login == true) {
                        Intent mWrite = new Intent(MainActivity.this, MealList.class);
                        mWrite.putExtra("id", loginID);
                        Log.d("id", loginID);
                        startActivity(mWrite);
                    } else {
                        Intent intent = new Intent(MainActivity.this, MealListOut.class);
                        startActivity(intent);
//                    drawerLayout.closeDrawers();
                    }
                } else if (item.getItemId() == R.id.bodyList) {
                    Intent intent = new Intent(MainActivity.this, BodyList2.class);
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
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                    login = true;
                    break;
                case R.id.ic_add:

                    btmSD_write.show();

//                        Toast myToast3 = Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다", Toast.LENGTH_SHORT);
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
