package com.nminhzien.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nminhzien.todoapp.MainActivity.ITEM_NAME;
import static com.nminhzien.todoapp.MainActivity.ITEM_POSITION;
import static com.nminhzien.todoapp.MainActivity.NEW_ITEM;


public class AddNewItemActivity extends AppCompatActivity {

    @BindView(R.id.et_new_item)
    EditText et_new_item;
    @BindView(R.id.add_item_toolbar)
    Toolbar add_item_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        ButterKnife.bind(this);
        setSupportActionBar(add_item_toolbar);
    }

    @OnClick(R.id.btn_Add)
    public void onAdd(View view) {
        Intent data = new Intent();
        data.putExtra(NEW_ITEM, et_new_item.getText().toString());
        setResult(RESULT_OK, data);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_new_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                // User chose the "Settings" item, show the app settings UI...

                return true;

            case R.id.cancel_action:

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
