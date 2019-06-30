package com.routehelperr.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.routehelperr.fragments.GoldBaqqFragment;
import com.routehelperr.fragments.SilverBaqqaFragment;

public class SelectBaqqaAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    String gold_Baqqa_desc;
    String silver_Baqqa_desc;

    public SelectBaqqaAdapter(FragmentManager fm, int numOfTabs, String gold_Baqqa_desc, String silver_Baqqa_desc) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.gold_Baqqa_desc = gold_Baqqa_desc;
        this.silver_Baqqa_desc = silver_Baqqa_desc;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GoldBaqqFragment();
                Bundle bundle = new Bundle();
                bundle.putString("gold_desc", gold_Baqqa_desc);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment = new SilverBaqqaFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("silver_desc", silver_Baqqa_desc);
                fragment.setArguments(bundle2);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
