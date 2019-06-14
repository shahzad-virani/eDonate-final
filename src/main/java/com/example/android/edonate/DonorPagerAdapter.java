package com.example.android.edonate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DonorPagerAdapter extends FragmentPagerAdapter {
    public DonorPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DonorDonate();
            case 1: return new DonorProfile();
            case 2: return new DonorNotifications();
            case 3: return new DonorHelp();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 4;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Donate";
        case 1: return "Profile";
        case 2: return "Notifications";
        case 3: return "Help";
        default: return null;
    }
    }}
