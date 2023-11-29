package com.example.laundirii.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Washer;

public class Connect extends SQLiteOpenHelper {
    /* Fields on tables*/

    //Client
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String CLIENT_USERNAME = "CLIENT_USERNAME";
    public static final String CLIENT_PASSWORD = "CLIENT_PASSWORD";
    public static final String CLIENT_NAME = "CLIENT_NAME";
    public static final String CLIENT_CONTACT_NO = "CLIENT_CONTACT_NO";
    public static final String CLIENT_ADDRESS = "CLIENT_ADDRESS";
    public static final String PAYMENT_INFO = "PAYMENT_INFO";

    //Courier
    public static final String COURIER_ID = "COURIER_ID";
    public static final String COURIER_USERNAME = "COURIER_USERNAME";
    public static final String COURIER_PASSWORD = "COURIER_PASSWORD";
    public static final String COURIER_NAME = "COURIER_NAME";
    public static final String COURIER_CONTACT_NO = "COURIER_CONTACT_NO";
    public static final String COURIER_PLATE_NO = "COURIER_PLATE_NO";
    public static final String COURIER_STATUS = "COURIER_STATUS";

    //Washer

    public static final String WASHER_ID = "WASHER_ID";
    public static final String WASHER_USERNAME = "WASHER_USERNAME";
    public static final String WASHER_PASSWORD = "WASHER_PASSWORD";
    public static final String SHOP_NAME = "SHOP_NAME";
    public static final String SHOP_LOCATION = "SHOP_LOCATION";
    public static final String WASHER_CONTACT_NO = "WASHER_CONTACT_NO";
    public static final String WASHER_STATUS = "WASHER_STATUS";

    // ORDER
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_CLIENT_ID = "ORDER_CLIENT_ID";
    public static final String ORDER_WASHER_ID = "ORDER_WASHER_ID";
    public static final String ORDER_COURIER1_ID = "ORDER_COURIER1_ID";
    public static final String TOTAL_COURIER1 = "TOTAL_COURIER1";
    public static final String DATE_COURIER1 = "DATE_COURIER1";
    public static final String ORDER_COURIER2_ID = "ORDER_COURIER2_ID";
    public static final String TOTAL_COURIER2 = "TOTAL_COURIER2";
    public static final String DATE_COURIER2 = "DATE_COURIER2";
    public static final String TOTAL_DUE = "TOTAL_DUE";
    public static final String TOTAL_PAID = "TOTAL_PAID";
    public static final String PAYMENT_STATUS = "PAYMENT_STATUS";
    public static final String GRAND_TOTAL = "GRAND_TOTAL";
    public static final String DATE_RECEIVED = "DATE_RECEIVED";

    // FEEDBACK
    public static final String FEEDBACK_ID = "FEEDBACK_ID";
    public static final String FEEDBACK_ORDER_ID = "FEEDBACK_ORDER_ID";
    public static final String COMMENT = "COMMENT";
    public static final String RATING = "RATING";

    public Connect(@Nullable Context context) {
        super(context,"laundiri.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createClientTableStatement = "CREATE TABLE CLIENT (CUSTOMER_ID INTEGER PRIMARY KEY, " +
                "CLIENT_USERNAME TEXT NOT NULL, CLIENT_PASSWORD TEXT NOT NULL, " +
                "CLIENT_NAME TEXT, CLIENT_CONTACT_NO TEXT, CLIENT_ADDRESS TEXT, PAYMENT_INFO INTEGER);";
        String createCourierTableStatement = "CREATE TABLE COURIER (COURIER_ID INTEGER PRIMARY KEY, " +
                "COURIER_USERNAME TEXT NOT NULL, COURIER_PASSWORD TEXT NOT NULL, COURIER_NAME TEXT, " +
                "COURIER_CONTACT_NO TEXT, COURIER_PLATE_NO TEXT, COURIER_STATUS INTEGER);";
        String createWasherTableStatement = "CREATE TABLE WASHER (WASHER_ID INTEGER PRIMARY KEY, " +
                "WASHER_USERNAME TEXT NOT NULL, WASHER_PASSWORD TEXT NOT NULL, SHOP_NAME TEXT, " +
                "SHOP_LOCATION TEXT, WASHER_CONTACT_NO TEXT, WASHER_STATUS INTEGER);";
        String createOrderTableStatement = "CREATE TABLE ORDER_TABLE (" +
                "ORDER_ID INTEGER PRIMARY KEY, " +
                "CLIENT_ID INTEGER, " +
                "WASHER_ID INTEGER, " +
                "COURIER1_ID INTEGER, " +
                "TOTAL_COURIER1 REAL, " +
                "DATE_COURIER1 TEXT, " +
                "COURIER2_ID INTEGER, " +
                "TOTAL_COURIER2 REAL, " +
                "DATE_COURIER2 TEXT, " +
                "TOTAL_DUE REAL, " +
                "TOTAL_PAID REAL, " +
                "PAYMENT_STATUS INTEGER, " +
                "GRAND_TOTAL REAL, " +
                "DATE_RECEIVED TEXT" +
                ");";
        String createFeedbackTableStatement = "CREATE TABLE FEEDBACK (" +
                "FEEDBACK_ID INTEGER PRIMARY KEY, " +
                "ORDER_ID INTEGER, " +
                "COMMENT TEXT, " +
                "RATING INTEGER" +
                ");";
        db.execSQL(createFeedbackTableStatement);
        db.execSQL(createOrderTableStatement);
        db.execSQL(createClientTableStatement);
        db.execSQL(createCourierTableStatement);
        db.execSQL(createWasherTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CLIENT");
        db.execSQL("DROP TABLE IF EXISTS COURIER");
        db.execSQL("DROP TABLE IF EXISTS WASHER");
        db.execSQL("DROP TABLE IF EXISTS ORDER_TABLE");
        db.execSQL("DROP TABLE IF EXISTS FEEDBACK");
        onCreate(db);
    }

    // LOG-IN CLIENT
    public boolean loginClient(String username, String password) {
        boolean isValid = false;
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Client table
        Cursor clientCursor = db.rawQuery("SELECT * FROM CLIENT WHERE CLIENT_USERNAME = ?", new String[]{username});

        if (clientCursor.moveToFirst()) {
            // Check if the CLIENT_PASSWORD column exists
            int passwordColumnIndex = clientCursor.getColumnIndex(CLIENT_PASSWORD);
            if (passwordColumnIndex != -1) {
                // Retrieve the stored password
                String storedPassword = clientCursor.getString(passwordColumnIndex);

                // Verify the password
                isValid = password.equals(storedPassword);
            }
        }

        clientCursor.close();
        db.close();
        return isValid;
    }

    // LOG-IN COURIER
    public boolean loginCourier(String username, String password) {
        boolean isValid = false;
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Client table
        Cursor courierCursor = db.rawQuery("SELECT * FROM COURIER WHERE COURIER_USERNAME = ?", new String[]{username});

        if (courierCursor.moveToFirst()) {
            // Check if the COURIER_USERNAME column exists
            int passwordColumnIndex = courierCursor.getColumnIndex(COURIER_PASSWORD);
            if (passwordColumnIndex != -1) {
                // Retrieve the stored password
                String storedPassword = courierCursor.getString(passwordColumnIndex);

                // Verify the password
                isValid = password.equals(storedPassword);
            }
        }

        courierCursor.close();
        db.close();
        return isValid;
    }

    //LOG-IN WASHER
    public boolean loginWasher(String username, String password) {
        boolean isValid = false;
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Washer table
        Cursor washerCursor = db.rawQuery("SELECT * FROM WASHER WHERE WASHER_USERNAME = ?", new String[]{username});

        if (washerCursor.moveToFirst()) {
            // Check if the WASHER_PASSWORD column exists
            int passwordColumnIndex = washerCursor.getColumnIndex(WASHER_PASSWORD);
            if (passwordColumnIndex != -1) {
                // Retrieve the stored password
                String storedPassword = washerCursor.getString(passwordColumnIndex);

                // Verify the password
                isValid = password.equals(storedPassword);
            }
        }

        washerCursor.close();
        db.close();
        return isValid;
    }

    // INSERT CLIENT (REGISTER)

    public boolean insertClient(String username, String password, String name, String contactNo, String address, int paymentInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isInserted = false;

        try {
            ContentValues values = new ContentValues();
            values.put(CLIENT_USERNAME, username);
            values.put(CLIENT_PASSWORD, password);
            values.put(CLIENT_NAME, name);
            values.put(CLIENT_CONTACT_NO, contactNo);
            values.put(CLIENT_ADDRESS, address);
            values.put(PAYMENT_INFO, paymentInfo);

            long newRowId = db.insert("CLIENT", null, values);

            // Check if the insertion was successful
            isInserted = newRowId != -1;
        } catch (Exception e) {
            Log.e("Database", "Error inserting client: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return isInserted;
    }

    // INSERT COURIER (REGISTER)

    public boolean insertCourier(String username, String password, String name, String contactNo, String plateNo, int courierStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isInserted = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COURIER_USERNAME, username);
            values.put(COURIER_PASSWORD, password);
            values.put(COURIER_NAME, name);
            values.put(COURIER_CONTACT_NO, contactNo);
            values.put(COURIER_PLATE_NO, plateNo);
            values.put(COURIER_STATUS, courierStatus);

            long newRowId = db.insert("COURIER", null, values);

            // Check if the insertion was successful
            isInserted = newRowId != -1;
        } catch (Exception e) {
            Log.e("Database", "Error inserting courier: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return isInserted;
    }

    // INSERT WASHER (REGISTER)
    public boolean insertWasher(String username, String password, String shopName, String shopLocation, String contactNo, int washerStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isInserted = false;

        try {
            ContentValues values = new ContentValues();
            values.put(WASHER_USERNAME, username);
            values.put(WASHER_PASSWORD, password);
            values.put(SHOP_NAME, shopName);
            values.put(SHOP_LOCATION, shopLocation);
            values.put(WASHER_CONTACT_NO, contactNo);
            values.put(WASHER_STATUS, washerStatus);

            long newRowId = db.insert("WASHER", null, values);

            // Check if the insertion was successful
            isInserted = newRowId != -1;
        } catch (Exception e) {
            Log.e("Database", "Error inserting washer: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return isInserted;
    }

    public boolean insertOrder(String orderID, Client client, Washer washer, Courier courier1,
                               double totalCourier1, String dateCourier1, Courier courier2,
                               double totalCourier2, String dateCourier2, double totalDue,
                               double totalPaid, boolean paymentStatus, double grandTotal,
                               String dateReceived)
    {
        boolean isValid = false;
        return isValid;
    }


    public boolean checkClientByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Client table
        Cursor clientCursor = db.rawQuery("SELECT * FROM CLIENT WHERE CLIENT_USERNAME = ?", new String[]{username});
        boolean clientExists = clientCursor.moveToFirst();
        clientCursor.close();
        db.close();

        return clientExists;
    }

    public boolean checkCourierByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Courier table
        Cursor courierCursor = db.rawQuery("SELECT * FROM COURIER WHERE COURIER_USERNAME = ?", new String[]{username});
        boolean courierExists = courierCursor.moveToFirst();
        courierCursor.close();

        return courierExists;
    }

    public boolean checkWasherByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Washer table
        Cursor washerCursor = db.rawQuery("SELECT * FROM WASHER WHERE WASHER_USERNAME = ?", new String[]{username});
        boolean washerExists = washerCursor.moveToFirst();
        washerCursor.close();

        return washerExists;
    }


}
