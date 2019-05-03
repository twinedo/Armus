package com.armusat_taubah;

/**
 * Created by TWIN on 6/24/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.armusat_taubah.jadwal.JadwalNov;
import com.armusat_taubah.jadwal.JadwalOkt;
import com.armusat_taubah.jadwal.JadwalSep;

public class ActJadwalAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ActJadwalAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                JadwalSep tab1 = new JadwalSep();
                return tab1;
            case 1:
                JadwalOkt tab2 = new JadwalOkt();
                return tab2;
            case 2:
                JadwalNov tab3 = new JadwalNov();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}