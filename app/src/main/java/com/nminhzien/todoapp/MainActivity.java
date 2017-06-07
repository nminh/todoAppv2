package com.nminhzien.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> TodoList;
    ArrayAdapter<String> aTodoAdapter;
    private final int REQUEST_CODE = 01;
    public static final String ITEM_NAME = "todoItemName";
    public static final String ITEM_POSITION = "position";
    TodoItemDatabase tododb;
    @BindView(R.id.edEditTest) EditText etEditText;
    @BindView(R.id.lvItems) ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        tododb = new TodoItemDatabase(this);
        TodoList = new ArrayList<String>();
        TodoList = tododb.getAllTasks();

        populateArrayItems();

        lvItems.setAdapter(aTodoAdapter);

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

    @OnClick(R.id.btnAddItem)
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
