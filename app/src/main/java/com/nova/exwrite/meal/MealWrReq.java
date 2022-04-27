package com.nova.exwrite.meal;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MealWrReq extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://solution12441.dothome.co.kr/mealList/mealwriteR.php";
    private Map<String, String> map;


    public MealWrReq(String nickname, String name, String time, String amount, String memo, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("name", name);
        map.put("time", time);
        map.put("amount", amount);
        map.put("memo", memo);
//        map.put("memo", memo);
//        map.put("bodyImg", img_path);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
