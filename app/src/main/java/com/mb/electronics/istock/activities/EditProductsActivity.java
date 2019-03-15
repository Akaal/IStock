package com.mb.electronics.istock.activities;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.adapters.ProductAutoCompleteAdapter;
import com.mb.electronics.istock.adapters.SearchListLoader;
import com.mb.electronics.istock.database.DataContract;
import com.mb.electronics.istock.listeners.GetSearchChangeValueListener;

import java.util.ArrayList;
import java.util.List;

public class EditProductsActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Product>> {

    private MultiAutoCompleteTextView ac_product_number;
    private EditText et_product_name, et_product_qty, et_product_price, et_product_desc;
    private Button btn_update, btn_delete;
    private Product mProduct;
    private SearchListLoader searchLoader;
    private Toolbar mToolbar;
    private TextView tv_title;
    private ArrayList<Product> mProductList;
    private ArrayList<String> mProductName;
    private ProductAutoCompleteAdapter mProductAutoCompleteAdapter;
    private GetSearchChangeValueListener mListener;
    private String mCurFilter, currentSelection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        setupIds();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_title.setText("Edit Products");
        setupListeners();
        setupAdapter();
        getSupportLoaderManager().initLoader(0, null, this);
    }


    private void setupIds() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_product_name = (EditText) findViewById(R.id.et_product_name);
        ac_product_number = (MultiAutoCompleteTextView) findViewById(R.id.ac_product_number);
        et_product_price = (EditText) findViewById(R.id.et_product_price);
        et_product_qty = (EditText) findViewById(R.id.et_product_qty);
        et_product_desc = (EditText) findViewById(R.id.et_product_desc);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);

    }

    private void setupListeners() {
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ac_product_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mCurFilter = !TextUtils.isEmpty(s.toString()) ? s.toString() : null;
                getData();
                searchLoader.onContentChanged();
            }
        });

        ac_product_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentSelection = mProductList.get(position).getARTICLE_NUMBER();
                ac_product_number.setText(mProductList.get(position).getARTICLE_NUMBER());
                et_product_name.setText(mProductList.get(position).getARTICLE_NAME());
                et_product_price.setText(mProductList.get(position).getSelling_Price());
                et_product_qty.setText(mProductList.get(position).getQuantity());
                et_product_desc.setText(mProductList.get(position).getDescription());
                hideKeyboard();
            }
        });

    }

    private void getData() {

        if (!(mListener instanceof GetSearchChangeValueListener)) {
            mListener = (GetSearchChangeValueListener) searchLoader;
            mListener.getValue(mCurFilter);
        } else {
            mListener.getValue(mCurFilter);
        }
    }

    private void setupAdapter() {
        mProductList = new ArrayList<>();
        mProductAutoCompleteAdapter = new ProductAutoCompleteAdapter(this, mProductList);
        ac_product_number.setAdapter(mProductAutoCompleteAdapter);
        ac_product_number.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_update:

                updateData();
                break;

            case R.id.btn_delete:

                deleteData();
                break;

        }
    }

    private void deleteData() {
        if (!TextUtils.isEmpty(currentSelection)) {


            try {

                String[] selection_args = new String[1];
                selection_args[0] = currentSelection;
                int rows = getContentResolver().delete(DataContract.ProductEntry.CONTENT_URI,
                        DataContract.ProductEntry.COLUMN_SKU_ID + " Like ?", selection_args);

                Log.d("Entry updated", rows + "");
                Toast.makeText(getApplicationContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } catch (Exception e) {

                Log.e("Database error", e.getMessage());
            }
        } else {

            Toast.makeText(getApplicationContext(), "Please select a product", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateData() {

        mProduct = new Product();
        mProduct.setARTICLE_NUMBER(ac_product_number.getText().toString());
        mProduct.setARTICLE_NAME(et_product_name.getText().toString());
        mProduct.setSelling_Price(et_product_price.getText().toString());
        mProduct.setQuantity(et_product_qty.getText().toString());
        mProduct.setDescription(et_product_desc.getText().toString());
        if (!TextUtils.isEmpty(currentSelection)) {


            try {


                ContentValues values = new ContentValues();
                values.put(DataContract.ProductEntry.COLUMN_PRODUCT_NAME, mProduct.getARTICLE_NAME());
                values.put(DataContract.ProductEntry.COLUMN_SKU_ID, mProduct.getARTICLE_NUMBER());
                values.put(DataContract.ProductEntry.COLUMN_QUANTITY, mProduct.getQuantity());
                values.put(DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE, mProduct.getSelling_Price());
                values.put(DataContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION, mProduct.getDescription());
                values.put(DataContract.ProductEntry.COLUMN_ADDED_ON, 0);

                String[] selection_args = new String[1];
                selection_args[0] = currentSelection;
                int rows = getContentResolver().update(DataContract.ProductEntry.CONTENT_URI,
                        values, DataContract.ProductEntry.COLUMN_SKU_ID + " Like ?", selection_args);

                Log.d("Entry updated", rows + "");
                Toast.makeText(getApplicationContext(), "Product updated successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } catch (Exception e) {

                Log.e("Database error", e.getMessage());
            }
        } else {

            Toast.makeText(getApplicationContext(), "Please select a product", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        hideKeyboard();
        currentSelection = "";
        ac_product_number.setText("");
        et_product_name.setText("");
        et_product_price.setText("");
        et_product_qty.setText("");
        et_product_price.setText("");
        et_product_desc.setText("");
    }

    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        searchLoader = new SearchListLoader(this);
        return searchLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        mProductList.clear();
        if (data != null) {
            mProductList.addAll(data);
        }

        mProductAutoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {
    }
}
