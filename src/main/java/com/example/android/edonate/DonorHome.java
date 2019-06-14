package com.example.android.edonate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

public class DonorHome extends AppCompatActivity implements DonorDonate.OnFragmentInteractionListener, DonorProfile.OnFragmentInteractionListener, DonorNotifications.OnFragmentInteractionListener, DonorHelp.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donor_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        DonorPagerAdapter donorPagerAdapter = new DonorPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(donorPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }



}
