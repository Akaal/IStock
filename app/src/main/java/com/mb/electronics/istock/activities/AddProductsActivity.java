package com.mb.electronics.istock.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.database.DataContract;

public class AddProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private EditText et_product_number, et_product_name, et_product_qty, et_product_price, et_product_desc;
    private Button btn_submit;
    private Product newProduct;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        setupIds();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupListeners();
        setupData();

    }


    private void setupIds() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_product_number = (EditText) findViewById(R.id.et_product_number);
        et_product_name = (EditText) findViewById(R.id.et_product_name);
        et_product_price = (EditText) findViewById(R.id.et_product_price);
        et_product_desc = (EditText) findViewById(R.id.et_product_desc);
        et_product_qty = (EditText) findViewById(R.id.et_product_qty);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    private void setupData() {

        tv_title.setText("Add New Product");
        newProduct = new Product();
    }

    private void setupListeners() {

        btn_submit.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
            values.put(DataContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION, newProduct.getDescription());
            values.put(DataContract.ProductEntry.COLUMN_ADDED_ON, 0);
            Uri abc = resolver.insert(DataContract.ProductEntry.CONTENT_URI,
                    values);

            Log.d("Entry Submitted", abc.toString());

            clearFields();
        } catch (Exception e) {

            Log.e("Database error", e.getMessage());
        }

    }

    private void clearFields() {
        hideKeyboard();
        Toast.makeText(getApplicationContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show();
        et_product_name.setText("");
        et_product_number.setText("");
        et_product_price.setText("");
        et_product_qty.setText("");
        et_product_desc.setText("");

    }

    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initData() {

        newProduct.setARTICLE_NAME(et_product_name.getText().toString());
        newProduct.setARTICLE_NUMBER(et_product_number.getText().toString());
        newProduct.setSelling_Price(et_product_price.getText().toString());
        newProduct.setQuantity(et_product_qty.getText().toString());
        newProduct.setDescription(et_product_desc.getText().toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
    }
}
