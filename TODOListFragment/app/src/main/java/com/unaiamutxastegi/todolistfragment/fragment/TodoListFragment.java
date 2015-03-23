package com.unaiamutxastegi.todolistfragment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unaiamutxastegi.todolistfragment.DetailActivity;
import com.unaiamutxastegi.todolistfragment.R;

import com.unaiamutxastegi.todolistfragment.adapters.ToDoAdapter;
import com.unaiamutxastegi.todolistfragment.fragment.dummy.DummyContent;
import com.unaiamutxastegi.todolistfragment.model.ToDo;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.

 */
public class TodoListFragment extends ListFragment implements ImputFragment.TODOItemListener {

    private final String TODOS_KEY = "todos_key";
    public static final String TODO_ITEM = "TODO_ITEM";

    private ArrayList<ToDo> todos;
    private ToDoAdapter aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

        todos = new ArrayList<>();
        aa = new ToDoAdapter(getActivity(),R.layout.todo_list_item,todos);

        setListAdapter(aa);

        if (savedInstanceState != null){
            ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
            todos.addAll(tmp);
        }
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TODOS_KEY, todos);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ToDo todo = todos.get(position);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(TODO_ITEM,todo);
        startActivity(detailIntent);
        //todos.remove(position);
        //aa.notifyDataSetChanged();
    }

    @Override
    public void addTodo(ToDo todo) {
        todos.add(0,todo);
        aa.notifyDataSetChanged();
    }
}
