package com.mb.electronics.istock.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.database.DataContract;

public class AddProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private EditText et_product_number, et_product_name, et_product_qty, et_product_price;
    private Button btn_submit;
    private Product newProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        setupIds();
        setupListeners();
        setupData();
        insertData();

    }


    private void setupIds() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_product_number = (EditText) findViewById(R.id.et_product_number);
        et_product_name = (EditText) findViewById(R.id.et_product_name);
        et_product_price = (EditText) findViewById(R.id.et_product_price);
        et_product_qty = (EditText) findViewById(R.id.et_product_qty);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    private void setupData() {

        tv_title.setText("Add New Product");
        newProduct = new Product();
    }

    private void setupListeners() {

        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submit:

                insertData();
                break;

        }

    }


    private void insertData() {

        initData();

        try {


            ContentResolver resolver = getContentResolver();
            ContentValues values = new ContentValues();

            values.put(DataContract.ProductEntry.COLUMN_PRODUCT_NAME, newProduct.getARTICLE_NAME());
            values.put(DataContract.ProductEntry.COLUMN_SKU_ID, newProduct.getARTICLE_NUMBER());
            values.put(DataContract.ProductEntry.COLUMN_QUANTITY, newProduct.getQuantity());
            values.put(DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE, newProduct.getSelling_Price());
            values.put(DataContract.ProductEntry.COLUMN_ADDED_ON, 0);
            Uri abc = resolver.insert(DataContract.ProductEntry.CONTENT_URI,
                    values);

            Log.d("Entry Submitted", abc.toString());
        } catch (Exception e) {

            Log.e("Database error", e.getMessage());
        }

    }

    private void initData() {

        newProduct.setARTICLE_NAME(et_product_name.getText().toString());
        newProduct.setARTICLE_NUMBER(et_product_number.getText().toString());
        newProduct.setSelling_Price(et_product_price.getText().toString());
        newProduct.setQuantity(et_product_qty.getText().toString());
    }
}
