package com.nova.exwrite.exercise;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExWrReq extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://solution12441.dothome.co.kr/exList/exwriteR.php";
    private Map<String, String> map;


    public ExWrReq(String id, String extitle, String exstart, String extime, String excontents, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("id", id);
        map.put("title", extitle);
        map.put("start_time", exstart);
        map.put("ex_time", extime);
        map.put("contents", excontents);
//        map.put("memo", memo);
//        map.put("img", exImg);
//        System.out.println("loginid" + exImg);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
