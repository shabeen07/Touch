package com.devcods.touch.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentsList;
    private ArrayList<String> mFragmentsTitleList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentsList=new ArrayList<>();
        mFragmentsTitleList=new ArrayList<>();
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentsList.size();
    }
    public void AddFragment(Fragment fragment, String title){
        mFragmentsList.add(fragment);
        mFragmentsTitleList.add(title);
    }
}