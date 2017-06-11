package com.nminhzien.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nminhzien.todoapp.MainActivity.ITEM_NAME;
import static com.nminhzien.todoapp.MainActivity.ITEM_POSITION;

public class EditItemActivity extends AppCompatActivity {

    String todoItemName;
    int pos;
    @BindView(R.id.et_EditItem) EditText et_EditItem;
    @BindView(R.id.edit_item_toolbar) Toolbar edit_item_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        ButterKnife.bind(this);
        setSupportActionBar(edit_item_toolbar);

        todoItemName = getIntent().getStringExtra(ITEM_NAME);
        pos = getIntent().getExtras().getInt(ITEM_POSITION);
        et_EditItem.setText(todoItemName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_new_item:
                // User chose the "Settings" item, show the app settings UI...
                onSaveItem();
                return true;

            case R.id.cancel_action:
                //
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSaveItem(){
        Intent data = new Intent();
        data.putExtra(ITEM_NAME, et_EditItem.getText().toString());
        data.putExtra(ITEM_POSITION, pos);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
