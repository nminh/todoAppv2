package com.nminhzien.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nminhzien.todoapp.MainActivity.ITEM_NAME;
import static com.nminhzien.todoapp.MainActivity.ITEM_POSITION;
import static com.nminhzien.todoapp.MainActivity.NEW_ITEM;


public class AddNewItemActivity extends AppCompatActivity {

    @BindView(R.id.et_new_item) EditText et_new_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_Add)
    public void onAdd(View view) {
        Intent data = new Intent();
        data.putExtra(NEW_ITEM, et_new_item.getText().toString());
        setResult(RESULT_OK, data);
        this.finish();
    }

}
