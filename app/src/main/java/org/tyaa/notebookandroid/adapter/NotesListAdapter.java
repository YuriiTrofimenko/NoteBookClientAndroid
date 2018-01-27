package org.tyaa.notebookandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.tyaa.notebookandroid.MainActivity;
import org.tyaa.notebookandroid.OrderDetailsActivity;
import org.tyaa.notebookandroid.R;
import org.tyaa.notebookandroid.model.Order;

import java.util.List;

/**
 * Created by student on 13.01.2018.
 */

public class NotesListAdapter extends ArrayAdapter<Order> {

    private List<Order> mOrders;
    private Context mContext;
    private int mResource;

    //private LayoutInflater mInflater;

    public NotesListAdapter(@NonNull Context context, int resource, List<Order> _orders) {

        super(context, resource, _orders);

        mOrders = _orders;
        mContext = context;
        mResource = resource;

        //mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;// = convertView;
        //if(view == null) {
            LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);
        //}

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, mOrders.get(position).getCustomer(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("details",mOrders.get(position).getText());
                ((MainActivity) mContext).startActivityForResult(intent, MainActivity.DETAILS_REQUEST);
            }
        });

        TextView customerTextView = view.findViewById(R.id.customerTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        customerTextView.setText("Customer: " + mOrders.get(position).getCustomer());
        descriptionTextView.setText(
                "Task: " +
                (mOrders.get(position).getText().length() > 25
                ? mOrders.get(position).getText().substring(0, 25) + " ..."
                : mOrders.get(position).getText())
        );

        return view;
    }


}
