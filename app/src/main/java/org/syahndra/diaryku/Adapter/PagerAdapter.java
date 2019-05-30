package org.syahndra.diaryku.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.syahndra.diaryku.FragmentDiary;
import org.syahndra.diaryku.FragmentHomeWork;
import org.syahndra.diaryku.FragmentMemo;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                FragmentHomeWork tab1= new FragmentHomeWork();
                return tab1;
            case 1:
                FragmentMemo tab2 = new FragmentMemo();
                return tab2;
            case 2:
                FragmentDiary tab3 = new FragmentDiary();
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
