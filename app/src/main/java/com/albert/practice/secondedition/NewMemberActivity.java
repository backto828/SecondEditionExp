package com.albert.practice.secondedition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewMemberActivity extends AppCompatActivity {

    private Button buttonConfirm, buttonCancel;
    private EditText editTextName, editTextValue;
    private int insertPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);
        buttonConfirm = findViewById(R.id.bt_Confirm);
        buttonCancel = findViewById(R.id.bt_Cancel);
        editTextName = findViewById(R.id.et_Name);
        editTextValue = findViewById(R.id.et_Value);
        editTextName.setText(getIntent().getStringExtra("姓名"));
        editTextValue.setText(getIntent().getStringExtra("悬赏金"));
        insertPosition = getIntent().getIntExtra("insert_position", 0);

    }

    // 确认更改
    public void confirmChange(View view) {
        Intent intent = new Intent();
        intent.putExtra("姓名", editTextName.getText().toString());
        intent.putExtra("悬赏金", editTextValue.getText().toString());
        intent.putExtra("insert_position", insertPosition);
        setResult(RESULT_OK, intent);
        NewMemberActivity.this.finish();
    }

    // 取消更改
    public void cancelChange(View view) {
        NewMemberActivity.this.finish();
    }

}
