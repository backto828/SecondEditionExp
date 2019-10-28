package com.albert.practice.secondedition.data;

import android.content.Context;

import com.albert.practice.secondedition.data.model.Book;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BookSaver {
    public BookSaver(Context context) {
        this.context = context;
    }

    // context对象用于读写内部文件
    Context context;

    public ArrayList<Book> getBooks() {
        return books;
    }

    // ArrayList保存数据
    ArrayList<Book> books = new ArrayList<>();

    public void save() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(books);
            outputStream.close();
        }
        // 捕获异常
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 返回读取的数据
    public ArrayList<Book> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt"));
            books = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回读入的数据
        return books;
    }

}
