package com.albert.practice.secondedition.data;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.albert.practice.secondedition.ListViewActivity;
import com.albert.practice.secondedition.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberListFragment extends Fragment {

    private ListViewActivity.MemberAdapter theAdapter;

    public MemberListFragment(ListViewActivity.MemberAdapter theAdapter) {
        // Required empty public constructor
        this.theAdapter = theAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member_list, container, false);
        ListView lv_Members = view.findViewById(R.id.lv_StrawHat);
        lv_Members.setAdapter(theAdapter);
        // 注册上下文菜单
        this.registerForContextMenu(lv_Members);
        // Inflate the layout for this fragment
        return view;
    }

}
