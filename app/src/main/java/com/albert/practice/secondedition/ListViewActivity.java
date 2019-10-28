package com.albert.practice.secondedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.albert.practice.secondedition.data.BookFragmentAdapter;
import com.albert.practice.secondedition.data.BookListFragment;
import com.albert.practice.secondedition.data.BookSaver;
import com.albert.practice.secondedition.data.model.Book;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    public static final int item_new = Menu.FIRST;
    public static final int item_delete = Menu.FIRST + 1;
    public static final int item_update = Menu.FIRST + 2;
    public static final int item_about = Menu.FIRST + 3;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK = 902;
    private List<Book> listBooks = new ArrayList<>();
    BookAdapter bookAdapter;
    BookSaver bookSaver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保存数据
        bookSaver.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // 调用加载数据的函数
        bookSaver = new BookSaver(this);
        listBooks = bookSaver.load();
        if (listBooks.size() == 0)
            InitData();// 定义数据
        // 数组适配器
        bookAdapter = new BookAdapter(ListViewActivity.this, R.layout.book_list_view, listBooks);

        BookFragmentAdapter myPageAdapter = new BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment(bookAdapter));
        datas.add(new BookListFragment(bookAdapter));
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("A");
        titles.add("B");
        myPageAdapter.setTitles(titles);

        // 使ViewPager和TabLayout相关联
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        // 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
        // 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);

    }

    // 定义数据
    private void InitData() {
        listBooks.add(new Book("book1", "40", R.drawable.book_001));
        listBooks.add(new Book("book2", "30", R.drawable.book_002));
        listBooks.add(new Book("book3", "33", R.drawable.book_003));
    }

    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == findViewById(R.id.lv_BookList)) {
            int itemPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menu.setHeaderTitle(listBooks.get(itemPosition).getName());
            //添加菜单项
            menu.add(0, item_new, 0, "新建");
            menu.add(0,item_update,0,"修改");
            menu.add(0, item_delete, 0, "删除");
            menu.add(0, item_about, 0, "关于");
        }
    }

    // 重载onActivityResult，接收返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 新建
            case REQUEST_CODE_NEW_BOOK:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("姓名");
                    String reword = data.getStringExtra("价格");
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    listBooks.add(insertPosition, new Book(name, reword, R.drawable.book_004));
                    bookAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            // 修改
            case REQUEST_CODE_UPDATE_BOOK:
                if (resultCode == RESULT_OK) {
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    Book bookAtPosition = listBooks.get(insertPosition);
                    bookAtPosition.setName(data.getStringExtra("姓名"));
                    bookAtPosition.setPrice(data.getStringExtra("价格"));
                    bookAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //上下文菜单响应事件
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // 新建
            case item_new: {
//                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Intent intent = new Intent(this, NewBookActivity.class);
                intent.putExtra("姓名", "");
                intent.putExtra("价格", "");
                intent.putExtra("insert_position", ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                startActivityForResult(intent, REQUEST_CODE_NEW_BOOK);
            }
            break;
            // 修改
            case item_update:
            {
                int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2 = new Intent(this, NewBookActivity.class);
                intent2.putExtra("姓名", listBooks.get(position).getName());
                intent2.putExtra("价格", listBooks.get(position).getPrice());
                intent2.putExtra("insert_position", position);
                startActivityForResult(intent2, REQUEST_CODE_UPDATE_BOOK);
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
                                listBooks.remove(itemPosition);
                                bookAdapter.notifyDataSetChanged();
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

    public class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.iv_book_pic)).setImageResource(book.getPicResourceId());
            ((TextView) view.findViewById(R.id.tv_book_name)).setText(book.getName());
            ((TextView) view.findViewById(R.id.tv_book_reword)).setText(book.getPrice() + " 元");
            return view;
        }
    }
}
