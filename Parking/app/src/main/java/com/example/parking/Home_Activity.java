package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.parking.Control.AdapterViewPager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class Home_Activity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        bottomAppBar = findViewById(R.id.bottombar);
        nav = findViewById(R.id.nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new home_fragment()).commit();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragment = new home_fragment();
                        break;
                    case R.id.nav_noti:
                        fragment = new notification_fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
                return true;
            }
        });

    }
}