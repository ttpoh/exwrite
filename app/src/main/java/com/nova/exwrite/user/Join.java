package com.nova.exwrite.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nova.exwrite.MainActivity;
import com.nova.exwrite.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Join extends AppCompatActivity implements View.OnClickListener {

    EditText joinemail, joinpw, joinnickname;
    Button btnJoin_cancle, btnJoin, btnIdck;

    private AlertDialog dialog;
    private boolean validate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        joinemail = (EditText) findViewById(R.id.et_joinemail);
        joinpw = (EditText) findViewById(R.id.et_joinpw);
        joinnickname = (EditText) findViewById(R.id.et_nickname);

        btnJoin_cancle = (Button) findViewById(R.id.btn_join_cancle);
        btnJoin = (Button) findViewById(R.id.btn_join);
        btnIdck = (Button) findViewById(R.id.btn_join_idck);

        btnJoin_cancle.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnIdck.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_join_cancle) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.btn_join_idck) {
            String joinEmail = joinemail.getText().toString();
            if(validate){
                return;
            }
            if(joinEmail.equals("")){
                AlertDialog.Builder builder=new AlertDialog.Builder( Join.this );
                dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();
                return;
            }
            Response.Listener<String> responseListener=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);
                    try {
                        JSONObject jsonResponse=new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            AlertDialog.Builder builder=new AlertDialog.Builder( Join.this );
                            dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                    .setPositiveButton("확인",null)
                                    .create();
                            dialog.show();
                            joinemail.setEnabled(false);
                            validate=true;
                            btnIdck.setText("확인");
                        }
                        else{
                            AlertDialog.Builder builder=new AlertDialog.Builder( Join.this );
                            dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                    .setNegativeButton("확인",null)
                                    .create();
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            JoinidCheck validateRequest=new JoinidCheck(joinEmail,responseListener);
            RequestQueue queue= Volley.newRequestQueue(Join.this);
            queue.add(validateRequest);



        } else if (v.getId() == R.id.btn_join) {
            if (validate == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                dialog = builder.setMessage("아이디 중복 체크하셨나요?.")
                        .setNegativeButton("확인", null)
                        .create();
                dialog.show();
            } else if (validate == true) {
                String joinEmail = joinemail.getText().toString();
                String joinPw = joinpw.getText().toString();
                String joinNickname = joinnickname.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(response, "이메일값");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                JoinRequest registerRequest = new JoinRequest(joinEmail, joinPw, joinNickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Join.this);
                queue.add(registerRequest);

            }
        }
    }

}



