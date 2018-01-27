package org.tyaa.notebookandroid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.tyaa.notebookandroid.adapter.NotesListAdapter;
import org.tyaa.notebookandroid.fetchr.JsonFetchr;
import org.tyaa.notebookandroid.interfaces.IFetchedDataHandler;
import org.tyaa.notebookandroid.model.Order;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements IFetchedDataHandler {

    //private ListView mNotesListView;
    private List<Order> mOrders;
    private NotesListAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private Context mContext;

    public static final int DETAILS_REQUEST = 0;
    public static final int ADD_ORDER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_main);

        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddOrder.class);
                startActivityForResult(intent, MainActivity.ADD_ORDER_REQUEST);
            }
        });

        //mNotesListView = findViewById(android.R.id.list);

        //mOrders = generateOrders();
        mOrders = new ArrayList<>();

        mAdapter =
                new NotesListAdapter(this, R.layout.notes_list_item, mOrders);

        //mNotesListView.setAdapter(adapter);
        setListAdapter(mAdapter);

        getAllOrders();
    }

    private List<Order> generateOrders(){

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++){

            Order order = new Order();
            order.setCustomer("Customer " + i);
            order.setId(i);
            order.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
            orders.add(order);
        }
        return orders;
    }

    @Override
    public void onOrdersFetched(List _orders) {

        mOrders.clear();
        mOrders.addAll(_orders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.ADD_ORDER_REQUEST){
            getAllOrders();
        }
    }

    private void getAllOrders(){

        new JsonFetchr(this)
                .fetch("http://10.20.60.10:8080/NoteBookServer-war/Api?action=get_orders");
        //.fetch("http://10.0.3.2:8080/NoteBookServer-war/Api?action=get_orders");
    }
}
