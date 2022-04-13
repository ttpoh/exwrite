package com.nova.exwrite;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottomNavHelper {

    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, MainActivity.class);  // 0
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_add:
                        Intent intent2 = new Intent(context, TapActivity.class); // 2
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_person:
                        Intent intent3 = new Intent(context, ProfileActivity.class); // 4
                        context.startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }
}
