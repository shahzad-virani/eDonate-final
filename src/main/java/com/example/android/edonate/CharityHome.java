package com.example.android.edonate;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class CharityHome extends AppCompatActivity implements CharityDonations.OnFragmentInteractionListener, CharityProfile.OnFragmentInteractionListener, CharityNotifications.OnFragmentInteractionListener, CharityHelp.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.charity_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        CharityPagerAdapter charityPagerAdapter = new CharityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(charityPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
