package com.nova.exwrite.user;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinidCheck extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://solution12441.dothome.co.kr/user/idCheck.php";
    private Map<String, String> map;

    public JoinidCheck(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}