package com.unaiamutxastegi.todolistfragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.unaiamutxastegi.todolistfragment.fragment.ImputFragment;
import com.unaiamutxastegi.todolistfragment.model.ToDo;

import static com.unaiamutxastegi.todolistfragment.fragment.ImputFragment.TODOItemListener;


public class MainActivity extends ActionBarActivity implements TODOItemListener {

    private final String TODO = "TODO";
    private TODOItemListener listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            listFragment = (TODOItemListener) getFragmentManager().findFragmentById(R.id.listFragment);
        } catch (ClassCastException ex) {
            throw new ClassCastException(this.toString() + " must implement TODOItemListener");
        }
    }

    @Override
    public void addTodo(ToDo todo) {
        listFragment.addTodo(todo);
    }
}
