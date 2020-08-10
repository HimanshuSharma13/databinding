package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.model.Account;

import java.util.ArrayList;


public class SpinnerAccountAdapter extends BaseAdapter {
    Context context;
    ArrayList<Account> userAccountArrayList;

    public SpinnerAccountAdapter(Context context, ArrayList<Account> userAccountArrayList) {
        this.context = context;
        this.userAccountArrayList = userAccountArrayList;
    }

    @Override
    public int getCount() {
        return userAccountArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userAccountArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.simple_spinner_item, parent, false);
        }

        // get current item to be displayed
        Account mUserAccount = (Account) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.account_number);


        //sets the text for item name and item description from the current item object
        textViewItemName.setText(mUserAccount.getAccountNumber());
       /* if (position == 0) {
            textViewItemName.setTextColor(Color.parseColor("#aeaeae"));
        } else {
            textViewItemName.setTextColor(Color.parseColor("#5a5a5a"));
        }*/
//        textViewItemDescription.setText(currentItem.getItemDescription());

        // returns the view for the current row
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

//        if (convertView == null) {
        convertView = LayoutInflater.from(context).
                inflate(R.layout.simple_spinner_item_dropdown, parent, false);
//        }


//        if (position == 0) {
//            ;
//            convertView.setLayoutParams(new AbsListView.LayoutParams(-1, 1));
//            convertView.setVisibility(View.GONE);
//            return convertView;
//        } else {
        convertView.setVisibility(View.VISIBLE);
        convertView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        // get current item to be displayed
        Account mUserAccount = (Account) getItem(position);

        // get the TextView for item name and item description
        TextView accountNumber = (TextView)
                convertView.findViewById(R.id.account_number);


        //sets the text for item name and item description from the current item object
        accountNumber.setText(mUserAccount.getAccountNumber());
        // get the TextView for item name and item description
        TextView accountType = (TextView)
                convertView.findViewById(R.id.account_type);


        //sets the text for item name and item description from the current item object


        return convertView;
//        }

//        return super.getDropDownView(position, convertView, parent);
    }
}
