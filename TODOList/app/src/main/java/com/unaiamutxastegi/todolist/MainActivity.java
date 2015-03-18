package com.unaiamutxastegi.todolist;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private final String myKey = "ArrayList";

    private ArrayList<String> todos;
    private ArrayAdapter<String> aa;

    private Button btnAdd = null;
    private TextView todoText = null;
    private ListView todoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todos = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todos);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        todoText = (TextView) findViewById(R.id.todoText);
        todoList = (ListView) findViewById(R.id.todoList);
        todoList.setAdapter(aa);

        this.addEventListener();

    }

    private void addItem(String todo) {
        todos.add(0, todo);

        aa.notifyDataSetChanged();
    }

    private void addEventListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoText.getText().toString();

                addItem(todo);

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        aa.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putStringArrayList(myKey,todos);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        todos.addAll(savedInstanceState.getStringArrayList(myKey));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
