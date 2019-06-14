package com.example.android.edonate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CharityPagerAdapter extends FragmentPagerAdapter  {
    public CharityPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new CharityDonations();
            case 1: return new CharityProfile();
            case 2: return new CharityNotifications();
            case 3: return new CharityHelp();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Donations";
        case 1: return "Profile";
        case 2: return "Notifications";
        case 3: return "Help";
        default: return null;
    }
}}
