package edu.hanu.mycart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import edu.hanu.mycart.Product;


public class ProductManager {

    // singleton
    private static ProductManager instance;
    private static final String INSERT_STMT =
            "INSERT INTO " + DbSchema.ProductsTable.NAME + "(thumbnail, name, unitPrice, quantity) VALUES (?, ?, ?, ?)";
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public static ProductManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProductManager(context);
        }
        return instance;
    }
    private ProductManager(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase(); // trigger onCreate if it not exist / onUpgrade
    }

    public List<Product> all() {
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME;
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);
        return cursorWrapper.getProducts();
    }

    public List<Product> allInCart() {
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME + " WHERE quantity > 0";
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);
        return cursorWrapper.getProducts();
    }

    public Product findById(int id){
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME + " WHERE id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id+""});

        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);

        return cursorWrapper.getProductById();
    }

    public Product findByName(String name){
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME + " WHERE name = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});

        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);

        return cursorWrapper.getProductByName();
    }

    /**
     * @modifies product
     */
    public boolean add(Product product) {
        SQLiteStatement statement = db.compileStatement(INSERT_STMT);
        statement.bindString(1, product.getThumbnail());
        statement.bindString(2, product.getName());
        statement.bindString(3, product.getUnitPrice());
        statement.bindLong(4, product.getQuantity());

        long id = statement.executeInsert(); // auto generated id
        if (id > 0) { // insert success
            product.setId((int) id);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        int result = db.delete(DbSchema.ProductsTable.NAME, "id = ?", new String[]{ id+"" });
        return result > 0;
    }

    public boolean update(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("thumbnail", product.getThumbnail());
        contentValues.put("name", product.getName());
        contentValues.put("unitPrice", product.getUnitPrice());
        contentValues.put("quantity", product.getQuantity());

        int result = db.update(DbSchema.ProductsTable.NAME, contentValues, "id = ?", new String[]{product.getId()+""});
        return result > 0;
    }

}
