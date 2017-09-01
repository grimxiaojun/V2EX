package com.example.kingsoft.v2ex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kingsoft.v2ex.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/8/14.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {



    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();


    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }


    public void addFragmentView(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }
}
