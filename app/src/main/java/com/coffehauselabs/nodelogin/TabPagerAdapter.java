package com.coffehauselabs.nodelogin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter{

    public TabPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public Fragment getItem(int i){
        switch(i){
            case 0:
                return new Frag1();
            case 1:
                return new Frag2();
            case 2:
                return new Frag3();
        }

        return null;
    }

    public int getCount(){
        return 3;   //NUMBER OF TABS
    }

}
