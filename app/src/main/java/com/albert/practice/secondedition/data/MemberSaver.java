package com.albert.practice.secondedition.data;

import android.content.Context;

import com.albert.practice.secondedition.data.model.Member;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MemberSaver {
    public MemberSaver(Context context) {
        this.context = context;
    }

    // context对象用于读写内部文件
    Context context;

    public ArrayList<Member> getMembers() {
        return members;
    }

    // ArrayList保存数据
    ArrayList<Member> members = new ArrayList<Member>();

    public void save() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(members);
            outputStream.close();
        }
        // 捕获异常
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 返回读取的数据
    public ArrayList<Member> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt"));
            members = (ArrayList<Member>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回读入的数据
        return members;
    }

}
