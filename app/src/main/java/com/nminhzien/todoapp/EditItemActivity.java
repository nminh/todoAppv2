package com.nminhzien.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.nminhzien.todoapp.MainActivity.ITEM_NAME;
import static com.nminhzien.todoapp.MainActivity.ITEM_POSITION;

public class EditItemActivity extends AppCompatActivity {

    String todoItemName;
    int pos;
    EditText et_EditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        todoItemName = getIntent().getStringExtra(ITEM_NAME);
        pos = getIntent().getExtras().getInt(ITEM_POSITION);
        et_EditItem = (EditText) findViewById(R.id.et_EditItem);
        et_EditItem.setText(todoItemName);
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra(ITEM_NAME, et_EditItem.getText().toString());
        data.putExtra(ITEM_POSITION, pos);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
