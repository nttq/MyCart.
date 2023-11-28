package edu.hanu.mycart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbSchema.ProductsTable.NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSchema.ProductsTable.Cols.THUMBNAIL + " TEXT, " +
                DbSchema.ProductsTable.Cols.NAME + " TEXT, " +
                DbSchema.ProductsTable.Cols.UNIT_PRICE + " TEXT, " +
                DbSchema.ProductsTable.Cols.QUANTITY + " TEXT" + ")");
        // other tables here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("My Products", "My Products: upgrading DB; dropping/recreating tables.");
        // drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ProductsTable.NAME);
// other tables here
        onCreate(db);
    }
}
