package com.android.ui.kent.demo.tab;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.text.SpannableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 2016/10/4.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<String> tabNames = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString sb = new SpannableString(tabNames.get(position));
        return sb;
    }

    public void addFragment(String tabName, Fragment fragment){
        tabNames.add(tabName);
        fragments.add(fragment);
    }



}