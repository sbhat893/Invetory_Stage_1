package com.examples.android.inventory;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.examples.android.inventory.data.ElectronicsContract;
import com.examples.android.inventory.data.ElectronicsDbHelper;

import android.support.design.widget.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    private ElectronicsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new ElectronicsDbHelper(this);
    }

    // After the user has clicked Save in the Activity
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the inventory database.
     */
    @SuppressLint("SetTextI18n")
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        ElectronicsDbHelper mDbHelper = new ElectronicsDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define my projection (column names)
        String[] projection = {
                ElectronicsContract.ElectronicsEntry._ID,
                ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_NAME,
                ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_MODEL,
                ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_BRAND,
                ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_NAME,
                ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_PHONE,
                ElectronicsContract.ElectronicsEntry.COLUMN_QUANTITY,
                ElectronicsContract.ElectronicsEntry.COLUMN_PRICE
        };

        // Perform an SQLite Query "SELECT * FROM Books"
        // to get a Cursor that contains all rows from the ElectronicsEntry table.
        Cursor cursor = db.query(
                ElectronicsContract.ElectronicsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        // Display and log the table info e.g.: "The table contains 2 products."
        TextView displayView = findViewById(R.id.text_view_electronics);
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String cursorText = getString(R.string.cursor_message, cursor.getCount());
        Log.v("CatalogActivity", cursorText);
        displayView.setText(cursorText);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The table contains <number of rows in Cursor> electronics.
            // _id - name - model - brand - supplier - phone - quantity - price
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Books table contains " + cursor.getCount() + " " +
                    "products.\n\n");
            displayView.append("\n\n" + ElectronicsContract.ElectronicsEntry._ID + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_NAME + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_BRAND + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_MODEL + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_NAME + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_PHONE + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_QUANTITY + " - " +
                    ElectronicsContract.ElectronicsEntry.COLUMN_PRICE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_NAME);
            int modelColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_MODEL);
            int brandColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_BRAND);
            int supplierColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_NAME);
            int phoneColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_PHONE);
            int weightColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(ElectronicsContract.ElectronicsEntry.COLUMN_PRICE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentModel = cursor.getString(modelColumnIndex);
                String currentBrand = cursor.getString(brandColumnIndex);
                int currentSupplier = cursor.getInt(supplierColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentModel + " - " +
                        currentBrand + " - " +
                        convertSupplier(currentSupplier) + " - " +
                        currentPhone + " - " +
                        currentWeight + " - " +
                        currentPrice));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertProduct() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_NAME, "Phone");
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_MODEL, "J8");
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_BRAND, "Samsung");
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_NAME, ElectronicsContract.ElectronicsEntry.SUPPLIER_1);
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_PHONE, "555-777-7788");
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_QUANTITY, 3);
        values.put(ElectronicsContract.ElectronicsEntry.COLUMN_PRICE, 17000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method that converts Supplier's ID to its string value
     *
     * @param supplierId is the ID from the Supplier spinner
     * @return Supplier's full name
     */
    public String convertSupplier(int supplierId) {

        if (supplierId != 0) {
            if (supplierId == 1) {
                return getString(R.string.supplier_1);
            } else if (supplierId == 2) {
                return getString(R.string.supplier_2);
            } else if (supplierId == 3) {
                return getString(R.string.supplier_3);
            } else if (supplierId == 4) {
                return getString(R.string.supplier_4);
            } else {
                return getString(R.string.supplier_unknown);
            }
        } else {
            return getString(R.string.supplier_unknown);
        }
    }
}
