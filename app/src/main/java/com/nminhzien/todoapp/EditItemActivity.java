package com.nminhzien.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    String todoItemName;
    int pos;
    EditText et_EditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        todoItemName = getIntent().getStringExtra("todoItemName");
        pos = getIntent().getExtras().getInt("position");
        et_EditItem = (EditText) findViewById(R.id.et_EditItem);
        et_EditItem.setText(todoItemName);
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("todoItemName", et_EditItem.getText().toString());
        data.putExtra("position", pos);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
