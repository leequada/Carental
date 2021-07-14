package com.example.parking.Control;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.parking.home_fragment;
import com.example.parking.notification_fragment;


public class AdapterViewPager extends FragmentStatePagerAdapter {
    private int pagenum;
    public AdapterViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pagenum = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new home_fragment();
            case 1: return new notification_fragment();
            default: return new home_fragment();
        }
    }

    @Override
    public int getCount() {
        return pagenum;
    }
}
