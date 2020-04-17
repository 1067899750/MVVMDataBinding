package com.example.z_model_main;

import android.view.MenuItem;

import com.example.z_lib_base.bus.command.BindingCommand;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;


/**
 * @author puyantao
 * @describe
 * @create 2020/4/15 13:35
 */
public class ViewAdapter {
    @BindingAdapter(value = {"onNavigationItemSelected"}, requireAll = false)
    public static void onNavigationItemSelected(BottomNavigationView view, BindingCommand command){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                command.execute(item);
                return true;
            }
        });

    }
}
















