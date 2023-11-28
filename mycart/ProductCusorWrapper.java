package edu.hanu.mycart;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

class ProductCursorWrapper extends CursorWrapper {

    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        int id = getInt(getColumnIndex("id"));
        String thumbnail = getString(getColumnIndex(DbSchema.ProductsTable.Cols.THUMBNAIL));
        String name = getString(getColumnIndex(DbSchema.ProductsTable.Cols.NAME));
        String unitPrice = getString(getColumnIndex(DbSchema.ProductsTable.Cols.UNIT_PRICE));
        int quantity = getInt(getColumnIndex(DbSchema.ProductsTable.Cols.QUANTITY));

        Product product = new Product(thumbnail, name, unitPrice, quantity);
        return product;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        moveToFirst();
        while (!isAfterLast()) {
            Product product = getProduct();
            products.add(product);
            moveToNext();
        }
        return products;
    }

    public Product getProductById(){
        Product product = null;

        moveToFirst();
        if (!isAfterLast()){
            product = getProduct();
        }
        return product;
    }

    public Product getProductByName(){
        Product product = null;

        moveToFirst();
        if (!isAfterLast()){
            product = getProduct();
        }
        return product;
    }

}