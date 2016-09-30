package com.mb.electronics.istock.activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.database.DataContract;

import java.util.ArrayList;

public class SearchProductsActivity extends AppCompatActivity {

    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        init();
    }

    private void init() {
        mContentResolver = getContentResolver();
        Cursor cursor = mContentResolver.query(DataContract.ProductEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        ArrayList<Product> allProducts = getAllItems(cursor);

        for (Product mProduct : allProducts) {
            Toast.makeText(getApplicationContext(), mProduct.getARTICLE_NAME(), Toast.LENGTH_SHORT).show();
        }
    }


    private ArrayList<Product> getAllItems(Cursor cursor) {
        ArrayList<Product> myItemsArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Product myItems = new Product();
            myItems.setARTICLE_NUMBER(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_SKU_ID)));
            myItems.setARTICLE_NAME(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_PRODUCT_NAME)));
            myItems.setSelling_Price(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE)));
            myItems.setQuantity(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_QUANTITY)));
            myItemsArrayList.add(myItems);
        }
        return myItemsArrayList;
    }

}
