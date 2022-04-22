package com.nova.exwrite.bodywrite.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BodyListD2 extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://solution12441.dothome.co.kr/bodylistD.php";
    private Map<String, String> map;


    public BodyListD2(String bodyNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("no", bodyNum);

//        map.put("weight", weight);
//        map.put("muscle", muscle);
//        map.put("fat", fat);
//        map.put("memo", memo);
//        map.put("bodyImg", img_path);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
