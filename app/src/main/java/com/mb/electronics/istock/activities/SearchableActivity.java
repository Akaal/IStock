package com.mb.electronics.istock.activities;

import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.database.DataContract;

public class SearchableActivity extends AppCompatActivity {

    public static final String JARGON = "com.mb.electronics.istock.search";
    private TextView tv_title, tv_product_number, tv_product_name, tv_product_qty, tv_product_price, tv_product_desc, tv_no_product;
    private Toolbar mToolbar;
    private Product mProduct;
    private View ll_product_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        setupIds();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_title.setText("Product Detail");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupData();
    }


    private void setupIds() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_product_desc = (TextView) findViewById(R.id.tv_product_desc);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_product_number = (TextView) findViewById(R.id.tv_product_number);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        tv_product_qty = (TextView) findViewById(R.id.tv_product_qty);
        tv_no_product = (TextView) findViewById(R.id.tv_no_product);
        ll_product_detail = findViewById(R.id.ll_product_detail);
    }

    private void setupData() {

        String search_query = getIntent().getStringExtra(SearchManager.QUERY);
        if (TextUtils.isEmpty(search_query)) {

            Bundle mBundle = getIntent().getBundleExtra(SearchManager.APP_DATA);
            mProduct = (Product) mBundle.getSerializable("product_data");

            setupFields();

        } else {

            fetchProductFromDb(search_query);
        }

    }


    private void fetchProductFromDb(String query) {
        String SELECTION = DataContract.ProductEntry.COLUMN_PRODUCT_NAME + " Like ? ";
        String[] SelectionArgs = new String[1];
        SelectionArgs[0] = "%" + query + "%";
        Cursor cursor = getContentResolver().query(DataContract.ProductEntry.CONTENT_URI,
                null,
                SELECTION,
                SelectionArgs,
                null);

        if (cursor.moveToNext()) {
            mProduct = new Product();
            mProduct.setARTICLE_NUMBER(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_SKU_ID)));
            mProduct.setARTICLE_NAME(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_PRODUCT_NAME)));
            mProduct.setSelling_Price(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE)));
            mProduct.setQuantity(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_QUANTITY)));
            mProduct.setDescription(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION)));
            setupFields();
        } else {

            ll_product_detail.setVisibility(View.GONE);
            tv_no_product.setVisibility(View.VISIBLE);
        }
    }

    private void setupFields() {

        tv_product_number.setText(mProduct.getARTICLE_NUMBER());
        tv_product_name.setText(mProduct.getARTICLE_NAME());
        tv_product_qty.setText(mProduct.getQuantity());
        tv_product_desc.setText(mProduct.getDescription());
        tv_product_price.setText(mProduct.getSelling_Price());

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
