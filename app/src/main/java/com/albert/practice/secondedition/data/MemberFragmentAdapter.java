package com.albert.practice.secondedition.data;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MemberFragmentAdapter extends FragmentPagerAdapter {
    // 存放要显示的子试图
    ArrayList<Fragment> datas;
    // 存放要显示的标题
    ArrayList<String> titles;

    public MemberFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<Fragment> datas) {
        this.datas = datas;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    // 获取对应位置的Fragment
    @Override
    public Fragment getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    // 子视图的数目
    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    // 获得某个标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? null : titles.get(position);
    }
}
