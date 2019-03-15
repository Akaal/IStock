package com.mb.electronics.istock.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mb.electronics.istock.Product;
import com.mb.electronics.istock.R;
import com.mb.electronics.istock.adapters.SearchListLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class  MiscActivity extends AppCompatActivity implements View.OnClickListener {
    private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private Document document;
    private Toolbar mToolbar;
    private Button btn_export;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        setupIds();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        tv_title.setText("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupListeners();
    }


    private void setupIds() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_export = (Button) findViewById(R.id.btn_export);
    }

    private void setupListeners() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_export.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_export:

                new GeneratePdfTask().execute();
                break;
        }

    }


    private class GeneratePdfTask extends AsyncTask<Object, Void, Void> {

        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MiscActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {
            try {
                generatePdf();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), "Pdf exported successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void generatePdf() {
        try {
            document = new Document();
            File root = new File(Environment.getExternalStorageDirectory(), "i_stock");
            if (!root.exists()) {
                root.mkdirs();
            }

            File outputFile = new File(root, "product_data" + ".pdf");

            PdfWriter.getInstance(document, new FileOutputStream(outputFile));

            document.open();
            SearchListLoader mSearchListLoader = new SearchListLoader(MiscActivity.this);
            mSearchListLoader.getValue("");
            ArrayList<Product> mProducts = (ArrayList<Product>) mSearchListLoader.loadInBackground();
            addTableData(mProducts);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addTableData(ArrayList<Product> mProducts) throws DocumentException {
        Paragraph customer_detail = new Paragraph();
        customer_detail.add(new Paragraph("MB Electronics", normalFont));
        customer_detail.add(new Paragraph("Baba Deep Singh Market", normalFont));
        customer_detail.add(new Paragraph("Hall Bazar", normalFont));
        customer_detail.add(new Paragraph("Amritsar", normalFont));
        addEmptyLine(customer_detail, 3);
        document.add(customer_detail);

        PdfPTable table = new PdfPTable(4);
        table.addCell("Sr. No.");
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Quantity");

        for (Product mProduct : mProducts) {
            table.addCell(mProduct.getARTICLE_NUMBER());
            table.addCell(mProduct.getARTICLE_NAME());
            table.addCell(mProduct.getSelling_Price());
            table.addCell(mProduct.getQuantity());

        }
        document.add(table);
        document.addCreationDate();

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
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

}
