package com.mb.electronics.istock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;

    // private List<String> searchArrayList ;
    public SearchListAdapter(Context context) {
        super(context, R.layout.search_list_item);
        //    this.searchArrayList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Product list = getItem(position);
        ViewHolder mViewHolder;
        if (view == null) {
            mViewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_item, parent, false);
            mViewHolder.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            mViewHolder.tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            mViewHolder.tv_product_qty = (TextView) view.findViewById(R.id.tv_product_qty);
            mViewHolder.tv_product_sno = (TextView) view.findViewById(R.id.tv_product_sno);
            mViewHolder.tv_product_sno.setTag(position);
            view.setTag(mViewHolder);

        } else {

            mViewHolder = (ViewHolder) view.getTag();
        }
        mViewHolder.tv_product_name.setText(list.getARTICLE_NAME());
        mViewHolder.tv_product_price.setText(list.getSelling_Price());
        mViewHolder.tv_product_qty.setText(list.getQuantity());
        mViewHolder.tv_product_sno.setText(list.getARTICLE_NUMBER());

        mViewHolder.tv_product_name.setSelected(true);
        mViewHolder.tv_product_price.setSelected(true);
        mViewHolder.tv_product_qty.setSelected(true);
        mViewHolder.tv_product_sno.setSelected(true);
        return view;
    }

    public void setData(List<Product> data) {
        clear();
        if (data != null) {
            addAll(data);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder {

        TextView tv_product_name, tv_product_price, tv_product_sno, tv_product_qty;

    }

}
