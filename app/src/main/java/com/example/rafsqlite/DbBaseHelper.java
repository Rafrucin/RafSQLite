package com.example.rafsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbBaseHelper extends SQLiteOpenHelper {


    public static final String CUSTOMERS_TABLE = "customers_table";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_AGE = "customer_age";
    public static final String IS_ACTIVE = "is_active";

    public DbBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "create table " + CUSTOMERS_TABLE + " (id integer primary key autoincrement, " +
                CUSTOMER_NAME + " text, " + CUSTOMER_AGE + " int, " + IS_ACTIVE + " bool) ";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean deleteOne(CustomerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "delete from " + CUSTOMERS_TABLE + " where id = " + customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public  boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUSTOMER_AGE, customerModel.getAge());
        cv.put(CUSTOMER_NAME, customerModel.getName());
        cv.put(IS_ACTIVE, customerModel.isAactive());

        long insert = db.insert(CUSTOMERS_TABLE, null, cv);
        if(insert==-1){
            return false;
        }
        db.close();
        return true;
    }

    public List<CustomerModel> getCustomers(){
        List<CustomerModel> output = new ArrayList<>();

        String queryString = "select * from " + CUSTOMERS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean isActive =  cursor.getInt(3) == 1? true : false;

                CustomerModel newCustomer = new CustomerModel(customerId, customerName, customerAge, isActive);
                output.add(newCustomer);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return output;
    }
}
