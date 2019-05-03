package com.armusat_taubah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


public class ActJadwal extends AppCompatActivity {
    //Deklarasi Toolbar dan Layout
    private Toolbar xToolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        xToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(xToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        xToolbar.setNavigationIcon(R.drawable.ic_action_back);
        xToolbar.setNavigationOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        }));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SEP"));
        tabLayout.addTab(tabLayout.newTab().setText("OKT"));
        tabLayout.addTab(tabLayout.newTab().setText("NOV"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final ActJadwalAdapter adapter = new ActJadwalAdapter
                (getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
            });
    }}

