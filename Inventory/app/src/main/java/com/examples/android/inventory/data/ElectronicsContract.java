package com.examples.android.inventory.data;

import android.provider.BaseColumns;

public final class ElectronicsContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ElectronicsContract() {
    }

    public static final class ElectronicsEntry implements BaseColumns {

        public final static String TABLE_NAME = "electronics";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product_name";
        public final static String COLUMN_PRODUCT_MODEL = "product_model";
        public final static String COLUMN_PRODUCT_BRAND = "product_brand";
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_PRICE = "price";

        public static final int SUPPLIER_SELECT = 0;
        public static final int SUPPLIER_1 = 1;
        public static final int SUPPLIER_2 = 2;
        public static final int SUPPLIER_3 = 3;
        public static final int SUPPLIER_4 = 4;
    }

}
