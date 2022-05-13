package com.nova.exwrite.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText loginmail, loginpw;
    Button btnLogin, btnLoginC;
    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginmail = (EditText) findViewById(R.id.login_email);
        loginpw = (EditText) findViewById(R.id.login_pw);


        btnLoginC = (Button) findViewById(R.id.btn_login_cancle);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLoginC.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_join_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_login) {
            String loginEmail = loginmail.getText().toString();
            String loginPw = loginpw.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("hongchul" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { // 로그인에 성공한 경우

                            String loginEmail = jsonObject.getString("id");

                            sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
                            sharedPreferences.getString("loginID", loginEmail);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loginID", loginEmail);
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("loginID", loginEmail);
                            startActivity(intent);
                        } else { // 로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(loginEmail, loginPw, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(loginRequest);
        }
    }

}
