package com.albert.practice.secondedition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    public static final int item_new = Menu.FIRST;
    public static final int item_delete = Menu.FIRST + 1;
    public static final int item_update = Menu.FIRST + 2;
    public static final int item_about = Menu.FIRST + 3;
    private ListView lv_Members;
    private List<Member> listMembers = new ArrayList<>();
    private MemberAdapter theAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // 定义数据
        InitData();
        lv_Members = findViewById(R.id.lv_StrawHat);
        // 数组适配器
        theAdapter = new MemberAdapter(ListViewActivity.this, R.layout.member_list_view, listMembers);
        lv_Members.setAdapter(theAdapter);
        // 注册上下文菜单
        this.registerForContextMenu(lv_Members);
    }

    // 定义数据
    private void InitData() {
        listMembers.add(new Member("路飞", "15亿", R.drawable.op_001));
        listMembers.add(new Member("索隆", "3.2亿", R.drawable.op_002));
        listMembers.add(new Member("山治", "3.3亿", R.drawable.op_003));
    }

    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == lv_Members) {
            int itemPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menu.setHeaderTitle(listMembers.get(itemPosition).getName());
            //添加菜单项
            menu.add(0, item_new, 0, "新建");
            menu.add(0,item_update,0,"修改");
            menu.add(0, item_delete, 0, "删除");
            menu.add(0, item_about, 0, "关于");
        }
    }

    //上下文菜单响应事件
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // 新建
            case item_new: {
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                listMembers.add(menuInfo.position,new Member("娜美","0.5亿",R.drawable.op_004));
                theAdapter.notifyDataSetChanged();
                Toast.makeText(this,"新建成功",Toast.LENGTH_SHORT).show();
            }
            break;
            // 修改
            case item_update:
            {
                Intent intent = new Intent(this, NewMemberActivity.class);
                startActivity(intent);
            }
            break;
            // 删除
            case item_delete: {
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition = menuInfo.position;
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("注意")
                        .setMessage("确定删除此项？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listMembers.remove(itemPosition);
                                theAdapter.notifyDataSetChanged();
                                Toast.makeText(ListViewActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
            break;
            // 关于
            case item_about:
                Toast.makeText(this, "Proudly Presented By Chen", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    class MemberAdapter extends ArrayAdapter<Member> {

        private int resourceId;

        public MemberAdapter(Context context, int resource, List<Member> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Member member = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.iv_member_pic)).setImageResource(member.getPicResourceId());
            ((TextView) view.findViewById(R.id.tv_member_name)).setText(member.getName());
            ((TextView) view.findViewById(R.id.tv_member_reword)).setText(member.getReword());
            return view;
        }
    }
}