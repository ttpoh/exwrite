package com.nova.exwrite.meal;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MealEditR extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://solution12441.dothome.co.kr/mealList/mealU.php";
    private Map<String, String> map;


    public MealEditR(String mealNum, String name, String time, String amount, String memo, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("no", mealNum);
        map.put("name", name);
//        Log.d("bodyweight php송신",weight);
        map.put("time", time);
        map.put("amount", amount);
        map.put("memo", memo);
//        map.put("bodyImg", img_path);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
