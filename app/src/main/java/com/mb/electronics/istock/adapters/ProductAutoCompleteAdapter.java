package com.mb.electronics.istock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mb.electronics.istock.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAutoCompleteAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> mProducts;

    public ProductAutoCompleteAdapter(Context context, ArrayList<Product> mProductList) {

        super(context, android.R.layout.simple_list_item_1);
        mProducts = mProductList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Product list = getItem(position);
        if (view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(android.R.layout.simple_list_item_1, parent, false);

        }
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(list.getARTICLE_NUMBER());
        return view;
    }


    public void setData(List<Product> data) {
        mProducts.clear();
        if (data != null) {
            mProducts.addAll(data);
            notifyDataSetChanged();
        }
    }


}
