package com.nminhzien.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> TodoList;
    ArrayAdapter<String> aTodoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 01;
    public static final String ITEM_NAME = "todoItemName";
    public static final String ITEM_POSITION = "position";
    TodoItemDatabase tododb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tododb = new TodoItemDatabase(this);
        TodoList = new ArrayList<String>();
        TodoList = tododb.getAllTasks();

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        etEditText = (EditText) findViewById(R.id.edEditTest);
        //Long Click Listener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long arg3) {
                TodoList.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                tododb.doDeleteaTask(position);
                //update All task id
                tododb.refreshAllTasks(TodoList);
                return true;
            }
        });
        //Click Listener
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v,
                                       int position, long arg3){
                launchEditItemView(position);
            }
        });
    }

    public void populateArrayItems() {
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TodoList);
    }

    public void onAddItem(View view) {
        aTodoAdapter.add(etEditText.getText().toString());
        tododb.addTask(TodoList.size() - 1, etEditText.getText().toString());
        etEditText.setText("");
    }

    public void launchEditItemView(int pos){
        Intent i =  new Intent(this, EditItemActivity.class);
        i.putExtra(ITEM_NAME, getViewByPosition(pos,lvItems));
        i.putExtra(ITEM_POSITION, pos);
        startActivityForResult(i, REQUEST_CODE);
    }

    public String getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return null;
        } else {
            return lvItems.getItemAtPosition(pos).toString();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String todoItemName = data.getExtras().getString(ITEM_NAME);
            int pos = data.getExtras().getInt(ITEM_POSITION);
            TodoList.set(pos, todoItemName);
            aTodoAdapter.notifyDataSetChanged();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            //Update to SQLite Database
            tododb.updateTaskName(pos,todoItemName);
        }
    }
}
