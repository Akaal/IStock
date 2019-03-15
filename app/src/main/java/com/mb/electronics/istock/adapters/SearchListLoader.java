package com.mb.electronics.istock.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.database.DataContract;
import com.mb.electronics.istock.listeners.GetSearchChangeValueListener;

import java.util.ArrayList;
import java.util.List;

public class SearchListLoader extends AsyncTaskLoader<List<Product>> implements GetSearchChangeValueListener {
    private final Context context;
    private List<Product> list;
    private String textSearch;
    private String responce;
    private ContentResolver mContentResolver;


    public SearchListLoader(Context context) {
        super(context);
        this.context = context;


    }

    @Override
    public List<Product> loadInBackground() {
        list = new ArrayList<>();
        list = loadData();

        return list;
    }

    private List<Product> loadData() {
        mContentResolver = context.getContentResolver();

        String[] PROJECTION =
                {
                        DataContract.ProductEntry.COLUMN_SKU_ID, DataContract.ProductEntry.COLUMN_PRODUCT_NAME, DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE,
                        DataContract.ProductEntry.COLUMN_QUANTITY, DataContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION};
        String SELECTION = DataContract.ProductEntry.COLUMN_PRODUCT_NAME + " Like ? OR " + DataContract.ProductEntry.COLUMN_SKU_ID + " Like ?";
        String[] SelectionArgs = new String[2];
        SelectionArgs[0] = "%" + textSearch + "%";
        SelectionArgs[1] = "%" + textSearch + "%";
        if (textSearch == null || TextUtils.isEmpty(textSearch)) {
            PROJECTION = null;
            SELECTION = null;
            SelectionArgs = null;
        }
        Cursor cursor = mContentResolver.query(DataContract.ProductEntry.CONTENT_URI,
                PROJECTION,
                SELECTION,
                SelectionArgs,
                null);

        ArrayList<Product> myItemsArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Product myItems = new Product();
            myItems.setARTICLE_NUMBER(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_SKU_ID)));
            myItems.setARTICLE_NAME(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_PRODUCT_NAME)));
            myItems.setSelling_Price(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_OUR_SELLING_PRICE)));
            myItems.setQuantity(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_QUANTITY)));
            myItems.setDescription(cursor.getString(cursor.getColumnIndex(DataContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION)));
            myItemsArrayList.add(myItems);
        }
        return myItemsArrayList;
    }


    @Override
    public void deliverResult(List<Product> data) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<Product> oldData = list;
        list = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);

        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldData != null) {
            onReleaseResources(oldData);
        }
    }


    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (list != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(list);
        }


        // Has something interesting in the configuration changed since we
        // last built the app list?
        // boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());

        if (takeContentChanged() || list == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Product> data) {
        super.onCanceled(data);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (list != null) {
            onReleaseResources(list);
            list = null;
        }

    }


    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<Product> apps) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }


    @Override
    public void getValue(String textSearch) {
        this.textSearch = textSearch;
    }
}
