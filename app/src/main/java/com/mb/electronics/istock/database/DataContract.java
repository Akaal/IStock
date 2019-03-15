package com.mb.electronics.istock.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.electronics.mb.istock.authorities";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)

    public static final String PATH_PRODUCT = "product";

    /* Inner class that defines the table contents of the product table */
    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        public static final String TABLE_NAME = "product";

        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_ADDED_ON = "added_on";
        //    public static final String COLUMN_IMAGE_URL = "image_url";

        public static final String COLUMN_QUANTITY = "quantity";

        public static final String COLUMN_MRP = "mrp";
        public static final String COLUMN_OUR_SELLING_PRICE = "our_selling_price";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_STORE_ID = "store_id";
        public static final String COLUMN_SKU_ID = "sku_id";
        public static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";

        public static Uri buildCartUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAllCartUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }


    }


}
