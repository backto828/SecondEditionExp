package com.albert.practice.secondedition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class NewBookActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice;
    private int insertPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        editTextName = findViewById(R.id.et_Name);
        editTextPrice = findViewById(R.id.et_Price);
        editTextName.setText(getIntent().getStringExtra("姓名"));
        editTextPrice.setText(getIntent().getStringExtra("价格"));
        insertPosition = getIntent().getIntExtra("insert_position", 0);

    }

    // 确认更改
    public void confirmChange(View view) {
        Intent intent = new Intent();
        intent.putExtra("姓名", editTextName.getText().toString());
        intent.putExtra("价格", editTextPrice.getText().toString());
        intent.putExtra("insert_position", insertPosition);
        setResult(RESULT_OK, intent);
        NewBookActivity.this.finish();
    }

    // 取消更改
    public void cancelChange(View view) {
        NewBookActivity.this.finish();
    }

}
