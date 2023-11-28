package edu.hanu.mycart;

public class DbSchema {

    public final class ProductsTable {
        public static final String NAME = "products";

        public final class Cols {
            public static final String THUMBNAIL = "thumbnail";
            public static final String NAME = "name";
            public static final String UNIT_PRICE = "unitPrice";
            public static final String QUANTITY = "quantity";
        }
    }
}
