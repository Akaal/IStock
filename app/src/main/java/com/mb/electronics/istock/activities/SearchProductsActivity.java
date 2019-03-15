package com.mb.electronics.istock.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.adapters.SearchListAdapter;
import com.mb.electronics.istock.adapters.SearchListLoader;
import com.mb.electronics.istock.listeners.GetSearchChangeValueListener;

import java.util.ArrayList;
import java.util.List;

public class SearchProductsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Product>>, AdapterView.OnItemClickListener {

    private Toolbar mToolbar;
    private SearchView searchView;
    private SearchListLoader searchLoader;
    private SearchListAdapter searchListAdapter;
    private ListView mListView;
    private ArrayList<Product> mProductList;
    private String mCurFilter;
    private TextView tv_title;
    private GetSearchChangeValueListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);


        setupIds();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
    }

    private void setupIds() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.lv_product_list);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    private void init() {

        tv_title.setText("Product List");
        mProductList = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(this);
        mListView.setAdapter(searchListAdapter);
        mListView.setOnItemClickListener(this);
        getSupportLoaderManager().initLoader(0, null, this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        Bundle appData = new Bundle();
        appData.putBoolean(SearchableActivity.JARGON, true);
        startSearch(null, false, appData, false);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the options menu from XML

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_activity_menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
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

        searchListAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {
        searchListAdapter.setData(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent_bar_code_search = new Intent(Intent.ACTION_SEARCH);
        intent_bar_code_search.setClass(SearchProductsActivity.this, SearchableActivity.class);
        intent_bar_code_search.putExtra(SearchManager.QUERY, query);
        intent_bar_code_search.putExtra(SearchManager.APP_DATA, new Bundle());
        startActivity(intent_bar_code_search);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        getData();
        searchLoader.onContentChanged();
        return true;
    }

    private void getData() {

        if (!(mListener instanceof GetSearchChangeValueListener)) {
            mListener = (GetSearchChangeValueListener) searchLoader;
            mListener.getValue(mCurFilter);
        } else {
            mListener.getValue(mCurFilter);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle app_bundle = new Bundle();
        app_bundle.putSerializable("product_data", mProductList.get(position));
        Intent intent_search = new Intent(Intent.ACTION_SEARCH);
        intent_search.setClass(SearchProductsActivity.this, SearchableActivity.class);
        intent_search.putExtra(SearchManager.QUERY, "");
        intent_search.putExtra(SearchManager.APP_DATA, app_bundle);
        startActivity(intent_search);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

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
/*  private ArrayList<Product> getAllItems(Cursor cursor) {
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
    }*/