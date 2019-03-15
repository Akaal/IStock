package com.mb.electronics.istock.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mb.electronics.istock.R;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_add_product, iv_search_product, iv_edit_product, iv_misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupIds();
        setupListeners();
    }

    private void setupListeners() {

        iv_add_product.setOnClickListener(this);
        iv_edit_product.setOnClickListener(this);
        iv_misc.setOnClickListener(this);
        iv_search_product.setOnClickListener(this);

    }

    private void setupIds() {

        iv_add_product = (ImageView) findViewById(R.id.iv_add_product);
        iv_edit_product = (ImageView) findViewById(R.id.iv_edit_product);
        iv_misc = (ImageView) findViewById(R.id.iv_misc);
        iv_search_product = (ImageView) findViewById(R.id.iv_search_product);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_add_product:
                startActivity(new Intent(DashboardActivity.this, AddProductsActivity.class));
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;
            case R.id.iv_edit_product:
                startActivity(new Intent(DashboardActivity.this, EditProductsActivity.class));
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;
            case R.id.iv_misc:
                startActivity(new Intent(DashboardActivity.this, MiscActivity.class));
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;
            case R.id.iv_search_product:
                startActivity(new Intent(DashboardActivity.this, SearchProductsActivity.class));
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;

        }
    }
}
