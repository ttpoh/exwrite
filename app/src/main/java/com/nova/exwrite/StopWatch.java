package com.nova.exwrite;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.nova.exwrite.exercise.ExWrite;
import com.nova.exwrite.exercise.logout.ExWriteOut;

public class StopWatch extends AppCompatActivity implements View.OnClickListener {

    TextView mEllapse;
    TextView mSplit;
    Button mBtnStart, mBtnSplit, mBtnWrite;
    //스톱워치의 상태를 위한 상수

    final static int IDLE = 0;
    final static int RUNNING = 1;
    final static int PAUSE = 2;
    int mStatus = IDLE;//처음 상태는 IDLE

    long mBaseTime;
    long mPauseTime;
    int mSplitCount;

    String loginID;
    String sharedBody = "LoginID";
    SharedPreferences sharedPreferences;
    String sSplit;

    Context context;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.stop_watch);

        mEllapse = (TextView) findViewById(R.id.ellapse);
        mSplit = (TextView) findViewById(R.id.split);

        mBtnStart = (Button) findViewById(R.id.btnstart);
        mBtnSplit = (Button) findViewById(R.id.btnsplit);


        mBtnStart.setOnClickListener(this);
        mBtnSplit.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(sharedBody, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("LoginID", MODE_PRIVATE);
        loginID = prefs.getString("loginID", null); //키값, 디폴트값

    }
//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.obtion_btn_stopwatch) {
//            if(loginID == null) {
//                Intent exWrite = new Intent(StopWatch.this, ExWriteOut.class);
//                exWrite.putExtra("exTime", sSplit);
//                startActivity(exWrite);
//            }else{
//                Intent exWrite = new Intent(StopWatch.this, ExWrite.class);
//                exWrite.putExtra("exTime", sSplit);
//                startActivity(exWrite);
//            }
//        }
//    }
    //스톱워치 위해 핸들러를 만든다.

    Handler mTimer = new Handler() {

        //핸들러는 기본적으로 handleMessage에서 처리한다.

        public void handleMessage(android.os.Message msg) {
            //텍스트뷰를 수정해준다.
            mEllapse.setText(getEllapse());
            //메시지를 다시 보낸다.
            mTimer.sendEmptyMessage(0);//0은 메시지를 구분하기 위한 것

        }

        ;

    };

    @Override

    protected void onDestroy() {

        // TODO Auto-generated method stub

        mTimer.removeMessages(0);//메시지를 지워서 메모리릭 방지
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //시작 버튼이 눌리면
            case R.id.btnstart:
                switch (mStatus) {
                    //IDLE상태이면
                    case IDLE:
                        //현재 값을 세팅해주고
                        mBaseTime = SystemClock.elapsedRealtime();
                        //핸드러로 메시지를 보낸다
                        mTimer.sendEmptyMessage(0);
                        //시작을 중지로 바꾸고
                        mBtnStart.setText("중지");
                        //옆버튼의 Enable을 푼 다음
                        mBtnSplit.setEnabled(true);
                        //상태를 RUNNING으로 바꾼다.
                        mStatus = RUNNING;
                        break;

                    //버튼이 실행상태이면

                    case RUNNING:
                        //핸들러 메시지를 없애고
                        mTimer.removeMessages(0);
                        //멈춘 시간을 파악
                        mPauseTime = SystemClock.elapsedRealtime();

                        //버튼 텍스트를 바꿔줌
                        mBtnStart.setText("시작");
                        mBtnSplit.setText("초기화");
                        mStatus = PAUSE;//상태를 멈춤으로 표시
                        break;
                    //멈춤이면
                    case PAUSE:
                        //현재값 가져옴
                        long now = SystemClock.elapsedRealtime();
                        //베이스타임 = 베이스타임 + (now - mPauseTime)
                        //잠깐 스톱워치를 멈췄다가 다시 시작하면 기준점이 변하게 되므로..
                        mBaseTime += (now - mPauseTime);
                        mTimer.sendEmptyMessage(0);

                        //텍스트 수정
                        mBtnStart.setText("중지");
                        mBtnSplit.setText("저장");
                        mStatus = RUNNING;
                        break;
                }
                break;

            case R.id.btnsplit:

                switch (mStatus) {

                    //RUNNING 상태일 때.
                    case RUNNING:

                        //기존의 값을 가져온뒤 이어붙이기 위해서

                        sSplit = mSplit.getText().toString();

                        //+연산자로 이어붙임

                        sSplit += String.format("%d => %s\n", mSplitCount, getEllapse());

                        //텍스트뷰의 값을 바꿔줌
                        mSplit.setText(sSplit);
                        mSplitCount++;


                        String st[] = {"1.예", "2.아니오"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("저장하시겠습니까??");
                        builder.setItems(st, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0) {
                                    if (loginID == null) {
                                        Intent exWrite = new Intent(StopWatch.this, ExWriteOut.class);
                                        exWrite.putExtra("exTime", sSplit);
                                        startActivity(exWrite);
                                    } else {
                                        Intent exWrite = new Intent(StopWatch.this, ExWrite.class);
                                        exWrite.putExtra("exTime", sSplit);
                                        startActivity(exWrite);
                                    }

                                } else if (i == 1) {
                                    Intent exWrite = new Intent(StopWatch.this, MainActivity.class);
                                    startActivity(exWrite);
//                                    Toast.makeText(context, "아니오", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                }
                break;

            case PAUSE://여기서는 초기화버튼이 됨

                //핸들러를 없애고
                mTimer.removeMessages(0);

                //처음상태로 원상복귀시킴

                mBtnStart.setText("시작");
                mBtnSplit.setText("저장");
                mEllapse.setText("00:00:00");
                mStatus = IDLE;
                mSplit.setText("");
                mBtnSplit.setEnabled(false);
                break;


        }
    }


    String getEllapse() {
        long now = SystemClock.elapsedRealtime();
        long ell = now - mBaseTime;//현재 시간과 지난 시간을 빼서 ell값을 구하고
        //아래에서 포맷을 예쁘게 바꾼다음 리턴해준다.
        String sEll = String.format("%02d:%02d:%02d", ell / 1000 / 60, (ell / 1000) % 60, (ell % 1000) / 10);

        return sEll;

    }
}



