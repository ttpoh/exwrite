package com.nova.exwrite.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://solution12441.dothome.co.kr/register.php";
    private Map<String, String> map;


    public JoinRequest(String id, String pw, String nickname, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("pw", pw);
        map.put("nickname", nickname);
        }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
