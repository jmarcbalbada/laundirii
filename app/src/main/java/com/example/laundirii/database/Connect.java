package com.example.laundirii.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Notification;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.model.Washer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
    public static final String COURIER_OVERALL_RATING = "COURIER_OVERALL_RATING";

    //Washer

    public static final String WASHER_ID = "WASHER_ID";
    public static final String WASHER_USERNAME = "WASHER_USERNAME";
    public static final String WASHER_PASSWORD = "WASHER_PASSWORD";
    public static final String SHOP_NAME = "SHOP_NAME";
    public static final String SHOP_LOCATION = "SHOP_LOCATION";
    public static final String WASHER_CONTACT_NO = "WASHER_CONTACT_NO";
    public static final String WASHER_STATUS = "WASHER_STATUS";
    public static final String RATE_PER_KG = "RATE_PER_KG";
    public static final String WASHER_OVERALL_RATING = "WASHER_OVERALL_RATING";

    // ORDER
//    public static final String ORDER_ID = "ORDER_ID";
//    public static final String ORDER_CLIENT_ID = "ORDER_CLIENT_ID";
//    public static final String ORDER_WASHER_ID = "ORDER_WASHER_ID";
//    public static final String ORDER_COURIER1_ID = "ORDER_COURIER1_ID";
//    public static final String TOTAL_COURIER1 = "TOTAL_COURIER1";
//    public static final String DATE_COURIER1 = "DATE_COURIER1";
//    public static final String ORDER_COURIER2_ID = "ORDER_COURIER2_ID";
//    public static final String TOTAL_COURIER2 = "TOTAL_COURIER2";
//    public static final String DATE_COURIER2 = "DATE_COURIER2";
//    public static final String TOTAL_DUE = "TOTAL_DUE";
//    public static final String TOTAL_PAID = "TOTAL_PAID";
//    public static final String PAYMENT_STATUS = "PAYMENT_STATUS";
//    public static final String GRAND_TOTAL = "GRAND_TOTAL";
//    public static final String DATE_RECEIVED = "DATE_RECEIVED";

    // NOTIFICATION
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
    public static final String NOTIFICATION_IS_READ = "NOTIFICATION_IS_READ";
    public static final String NOTIFICATION_CLIENT_ID = "NOTIFICATION_CLIENT_ID";
    public static final String NOTIFICATION_COURIER_ID = "NOTIFICATION_COURIER_ID";
    public static final String NOTIFICATION_WASHER_ID = "NOTIFICATION_WASHER_ID";
    public static final String NOTIFICATION_DATETIME = "NOTIFICATION_DATETIME";


    // PHASE 1 ORDER
    public static final String PHASE1_ORDER_ID = "PHASE1_ORDER_ID";
    public static final String PHASE1_ORDER_CLIENT_ID = "PHASE1_ORDER_CLIENT_ID";
    public static final String PHASE1_ORDER_WASHER_ID = "PHASE1_ORDER_WASHER_ID";
    public static final String PHASE1_ORDER_COURIER_ID = "PHASE1_ORDER_COURIER_ID";
    public static final String PHASE1_COURIER_STATUS = "PHASE1_COURIER_STATUS";
    public static final String PHASE1_TOTAL_COURIER_AMOUNT = "PHASE1_TOTAL_COURIER_AMOUNT";
    public static final String PHASE1_DATE_COURIER = "PHASE1_DATE_COURIER";
    public static final String PHASE1_TOTAL_DUE = "PHASE1_TOTAL_DUE";
    public static final String PHASE1_TOTAL_PAID = "PHASE1_TOTAL_PAID";
    public static final String PHASE1_PAYMENT_STATUS = "PHASE1_PAYMENT_STATUS";
    public static final String PHASE1_DATE_RECEIVED = "PHASE1_DATE_RECEIVED";
    public static final String PHASE1_INITIAL_LOAD = "PHASE1_INITIAL_LOAD";
    public static final String PHASE1_ORDER_STATUS = "PHASE1_ORDER_STATUS";
    public static final String PHASE1_DATE_PLACED = "PHASE1_DATE_PLACED";


    // PHASE 2 ORDER
    public static final String PHASE2_ORDER_ID = "PHASE2_ORDER_ID";
    public static final String PHASE2_ORDER_CLIENT_ID = "PHASE2_ORDER_CLIENT_ID";
    public static final String PHASE2_ORDER_WASHER_ID = "PHASE2_ORDER_WASHER_ID";
    public static final String PHASE2_ORDER_COURIER_ID = "PHASE2_ORDER_COURIER_ID";
    public static final String PHASE2_COURIER_STATUS = "PHASE2_COURIER_STATUS";
    public static final String PHASE2_TOTAL_COURIER_AMOUNT = "PHASE2_TOTAL_COURIER_AMOUNT";
    public static final String PHASE2_DATE_COURIER = "PHASE2_DATE_COURIER";
    public static final String PHASE2_TOTAL_DUE = "PHASE2_TOTAL_DUE";
    public static final String PHASE2_TOTAL_PAID = "PHASE2_TOTAL_PAID";
    public static final String PHASE2_PAYMENT_STATUS = "PHASE2_PAYMENT_STATUS";
    public static final String PHASE2_DATE_RECEIVED = "PHASE2_DATE_RECEIVED";
    public static final String PHASE2_ORDER_STATUS = "PHASE2_ORDER_STATUS";
    public static final String PHASE2_REFERENCE_NO = "PHASE2_REFERENCE_NO";
    public static final String PHASE2_DATE_PLACED = "PHASE2_DATE_PLACED";
    public static final String PHASE2_PHASE1_ORDER_ID = "PHASE2_PHASE1_ORDER_ID";

    // FEEDBACK
    public static final String FEEDBACK_ID = "FEEDBACK_ID";
    public static final String FEEDBACK_COMMENT = "FEEDBACK_COMMENT";
    public static final String RATING = "RATING";
    public static final String FEEDBACK_CLIENT_ID = "FEEDBACK_CLIENT_ID";
    public static final String FEEDBACK_COURIER_ID = "FEEDBACK_COURIER_ID";
    public static final String FEEDBACK_WASHER_ID = "FEEDBACK_WASHER_ID";
    public static final String FEEDBACK_PHASE1_ID = "FEEDBACK_WASHER_ID";
    public static final String FEEDBACK_PHASE2_ID = "FEEDBACK_PHASE2_ID";
    public static final String FEEDBACK_HAS_RATED = "FEEDBACK_HAS_RATED";

    public Connect(@Nullable Context context) {
        super(context, "laundiri.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createClientTableStatement = "CREATE TABLE CLIENT (CUSTOMER_ID INTEGER PRIMARY KEY, " +
                "CLIENT_USERNAME TEXT NOT NULL, CLIENT_PASSWORD TEXT NOT NULL, " +
                "CLIENT_NAME TEXT, CLIENT_CONTACT_NO TEXT, CLIENT_ADDRESS TEXT, PAYMENT_INFO INTEGER);";
        String createCourierTableStatement = "CREATE TABLE COURIER (COURIER_ID INTEGER PRIMARY KEY, " +
                "COURIER_USERNAME TEXT NOT NULL, COURIER_PASSWORD TEXT NOT NULL, COURIER_NAME TEXT, " +
                "COURIER_CONTACT_NO TEXT, COURIER_PLATE_NO TEXT, COURIER_STATUS INTEGER, COURIER_OVERALL_RATING INTEGER);";
        String createWasherTableStatement = "CREATE TABLE WASHER (WASHER_ID INTEGER PRIMARY KEY, " +
                "WASHER_USERNAME TEXT NOT NULL, WASHER_PASSWORD TEXT NOT NULL, SHOP_NAME TEXT, " +
                "SHOP_LOCATION TEXT, WASHER_CONTACT_NO TEXT, WASHER_STATUS INTEGER, RATE_PER_KG REAL, WASHER_OVERALL_RATING INTEGER);";
//        String createOrderTableStatement = "CREATE TABLE ORDER_TABLE (" +
//                "ORDER_ID INTEGER PRIMARY KEY, " +
//                "ORDER_CLIENT_ID INTEGER, " +
//                "ORDER_WASHER_ID INTEGER, " +
//                "ORDER_COURIER1_ID INTEGER, " +
//                "TOTAL_COURIER1 REAL, " +
//                "DATE_COURIER1 TEXT, " +
//                "ORDER_COURIER2_ID INTEGER, " +
//                "TOTAL_COURIER2 REAL, " +
//                "DATE_COURIER2 TEXT, " +
//                "TOTAL_DUE REAL, " +
//                "TOTAL_PAID REAL, " +
//                "PAYMENT_STATUS INTEGER, " +
//                "GRAND_TOTAL REAL, " +
//                "DATE_RECEIVED TEXT" +
//                ");";
        String createPhase1OrderTableStatement = "CREATE TABLE PHASE1_ORDER (" +
                "PHASE1_ORDER_ID INTEGER PRIMARY KEY, " +
                "PHASE1_ORDER_CLIENT_ID INTEGER, " +
                "PHASE1_ORDER_WASHER_ID INTEGER, " +
                "PHASE1_ORDER_COURIER_ID INTEGER, " +
                "PHASE1_COURIER_STATUS INTEGER, " +
                "PHASE1_TOTAL_COURIER_AMOUNT REAL, " +
                "PHASE1_DATE_COURIER TEXT, " +
                "PHASE1_TOTAL_DUE REAL, " +
                "PHASE1_TOTAL_PAID REAL, " +
                "PHASE1_PAYMENT_STATUS INTEGER, " +
                "PHASE1_DATE_RECEIVED TEXT," +
                "PHASE1_INITIAL_LOAD INTEGER," +
                "PHASE1_ORDER_STATUS INTEGER," +
                "PHASE1_DATE_PLACED TEXT" +
                ");";

        String createNotificationTableStatement = "CREATE TABLE NOTIFICATION (" +
                "NOTIFICATION_ID INTEGER PRIMARY KEY, " +
                "NOTIFICATION_TITLE TEXT, " +
                "NOTIFICATION_MESSAGE TEXT, " +
                "NOTIFICATION_IS_READ INTEGER, " +  // Use 0 for false, 1 for true
                "NOTIFICATION_CLIENT_ID INTEGER, " +
                "NOTIFICATION_COURIER_ID INTEGER, " +
                "NOTIFICATION_WASHER_ID INTEGER, " +
                "NOTIFICATION_DATETIME TEXT" +
                ");";

        String createPhase2OrderTableStatement = "CREATE TABLE PHASE2_ORDER (" +
                "PHASE2_ORDER_ID INTEGER PRIMARY KEY, " +
                "PHASE2_ORDER_CLIENT_ID INTEGER, " +
                "PHASE2_ORDER_WASHER_ID INTEGER, " +
                "PHASE2_ORDER_COURIER_ID INTEGER, " +
                "PHASE2_COURIER_STATUS INTEGER, " +
                "PHASE2_TOTAL_COURIER_AMOUNT REAL, " +
                "PHASE2_DATE_COURIER TEXT, " +
                "PHASE2_TOTAL_DUE REAL, " +
                "PHASE2_TOTAL_PAID REAL, " +
                "PHASE2_PAYMENT_STATUS INTEGER, " +
                "PHASE2_DATE_RECEIVED TEXT," +
                "PHASE2_ORDER_STATUS INTEGER," +
                "PHASE2_REFERENCE_NO TEXT," +
                "PHASE2_DATE_PLACED TEXT," +
                "PHASE2_PHASE1_ORDER_ID INTEGER" +
                ");";
        String createFeedbackTableStatement = "CREATE TABLE FEEDBACK (" +
                "FEEDBACK_ID INTEGER PRIMARY KEY, " +
                "FEEDBACK_COMMENT TEXT, " +
                "RATING INTEGER, " +
                "FEEDBACK_CLIENT_ID INTEGER," +
                "FEEDBACK_COURIER_ID INTEGER," +
                "FEEDBACK_WASHER_ID INTEGER," +
                "FEEDBACK_PHASE1_ID INTEGER," +
                "FEEDBACK_PHASE2_ID INTEGER," +
                "FEEDBACK_HAS_RATED INTEGER" +
                ");";
        db.execSQL(createFeedbackTableStatement);
        db.execSQL(createPhase1OrderTableStatement);
        db.execSQL(createPhase2OrderTableStatement);
        db.execSQL(createNotificationTableStatement);
//        db.execSQL(createOrderTableStatement);
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
        db.execSQL("DROP TABLE IF EXISTS PHASE1_ORDER");
        db.execSQL("DROP TABLE IF EXISTS PHASE2_ORDER");
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


//    public boolean insertNotificationOnClient(int clientID, String title, String message) {
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();  // Assuming 'this' refers to the Connect instance
//
//            ContentValues values = new ContentValues();
//            values.put("NOTIFICATION_CLIENT_ID", clientID);
//            values.put("NOTIFICATION_TITLE", title);
//            values.put("NOTIFICATION_MESSAGE", message);
//            values.put("NOTIFICATION_IS_READ", 0);  // Assuming the notification is initially unread
//
//            // Get current date and time in the specified format
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault());
//            String dateTime = sdf.format(new Date());
//            values.put("NOTIFICATION_DATETIME", dateTime);
//
//            // Insert the values into the NOTIFICATION table
//            long result = db.insert("NOTIFICATION", null, values);
//
//            // Close the database to avoid memory leaks
//            db.close();
//
//            // Return true if the insertion was successful, false otherwise
//            return result != -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Return false if an exception occurred during the insertion
//            return false;
//        }
//    }

    public int getUnreadNotificationCount(int ID, int typeOfUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String query = "";
        // 0 - client , 1 - courier, 2 - washer
        switch(typeOfUser)
        {
            case 0:
                query = "SELECT COUNT(*) FROM NOTIFICATION " +
                        "WHERE NOTIFICATION_IS_READ = 0 AND NOTIFICATION_CLIENT_ID = ?";
                break;
            case 1:
                query = "SELECT COUNT(*) FROM NOTIFICATION " +
                        "WHERE NOTIFICATION_IS_READ = 0 AND NOTIFICATION_COURIER_ID = ?";
                break;
            case 2:
                query = "SELECT COUNT(*) FROM NOTIFICATION " +
                        "WHERE NOTIFICATION_IS_READ = 0 AND NOTIFICATION_WASHER_ID = ?";
                break;
        }

        try {
            String[] selectionArgs = {String.valueOf(ID)};
            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
                cursor.close();
            }
        } catch (SQLException e) {
            // Handle exceptions as needed
            Log.e("NotificationCountError", e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return count;
    }

    public void markNotificationsAsRead(int ID, int typeOfUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";
        // 0 - client , 1 - courier, 2 - washer
        switch(typeOfUser)
        {
            case 0:
                query = "UPDATE NOTIFICATION SET NOTIFICATION_IS_READ = 1 WHERE NOTIFICATION_CLIENT_ID = ?";
                break;
            case 1:
                query = "UPDATE NOTIFICATION SET NOTIFICATION_IS_READ = 1 WHERE NOTIFICATION_COURIER_ID = ?";
                break;
            case 2:
                query = "UPDATE NOTIFICATION SET NOTIFICATION_IS_READ = 1 WHERE NOTIFICATION_WASHER_ID = ?";
                break;
        }
        try {
            String[] selectionArgs = {String.valueOf(ID)};
            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null) {
                cursor.moveToFirst();
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e("Mark Notifications as Read Error", e.getMessage());
        } finally {
            db.close();
        }
    }


    public List<Notification> getNotificationOnClient(int clientID)
    {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM NOTIFICATION WHERE NOTIFICATION_CLIENT_ID = ? " +
                "ORDER BY NOTIFICATION_DATETIME DESC";

        String[] selectionArgs = {String.valueOf(clientID)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try
        {
            while(cursor.moveToNext())
            {
                int notificationIdIndex = cursor.getColumnIndex(NOTIFICATION_ID);
                int notificationTitleIndex = cursor.getColumnIndex(NOTIFICATION_TITLE);
                int notificationMessageIndex = cursor.getColumnIndex(NOTIFICATION_MESSAGE);
                int notificationIsReadIndex = cursor.getColumnIndex(NOTIFICATION_IS_READ);
                int notificationClientIdIndex = cursor.getColumnIndex(NOTIFICATION_CLIENT_ID);
                int notificationCourierIdIndex = cursor.getColumnIndex(NOTIFICATION_COURIER_ID);
                int notificationWasherIdIndex = cursor.getColumnIndex(NOTIFICATION_WASHER_ID);
                int notificationDateTimeIndex = cursor.getColumnIndex(NOTIFICATION_DATETIME);

                if(notificationIdIndex != -1 && notificationTitleIndex != -1 &&
                        notificationMessageIndex != -1 && notificationIsReadIndex != -1 &&
                        notificationClientIdIndex != -1 && notificationClientIdIndex != 1 &&
                        notificationCourierIdIndex != -1 && notificationWasherIdIndex != -1 &&
                        notificationDateTimeIndex != -1)
                {
                    boolean isRead = cursor.getInt(notificationIsReadIndex) == 1;
                    Notification notification = new Notification(
                            cursor.getInt(notificationIdIndex),
                            cursor.getString(notificationTitleIndex),
                            cursor.getString(notificationMessageIndex),
                            isRead,
                            getClient(cursor.getInt(notificationClientIdIndex)),
                            new Courier(),
                            new Washer(),
                            cursor.getString(notificationDateTimeIndex)
                    );

                    notifications.add(notification);
                }
                else
                {
                    Log.e("Notification Error DB", "one is -1");
                }
            }

        }catch(Exception e)
        {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        }finally
        {
            cursor.close();
        }

        return notifications;
    }

    public List<Notification> getNotificationOnCourier(int courierID)
    {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM NOTIFICATION WHERE NOTIFICATION_COURIER_ID = ? " +
                "ORDER BY NOTIFICATION_DATETIME DESC";

        String[] selectionArgs = {String.valueOf(courierID)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try
        {
            while(cursor.moveToNext())
            {
                int notificationIdIndex = cursor.getColumnIndex(NOTIFICATION_ID);
                int notificationTitleIndex = cursor.getColumnIndex(NOTIFICATION_TITLE);
                int notificationMessageIndex = cursor.getColumnIndex(NOTIFICATION_MESSAGE);
                int notificationIsReadIndex = cursor.getColumnIndex(NOTIFICATION_IS_READ);
                int notificationClientIdIndex = cursor.getColumnIndex(NOTIFICATION_CLIENT_ID);
                int notificationCourierIdIndex = cursor.getColumnIndex(NOTIFICATION_COURIER_ID);
                int notificationWasherIdIndex = cursor.getColumnIndex(NOTIFICATION_WASHER_ID);
                int notificationDateTimeIndex = cursor.getColumnIndex(NOTIFICATION_DATETIME);

                if(notificationIdIndex != -1 && notificationTitleIndex != -1 &&
                        notificationMessageIndex != -1 && notificationIsReadIndex != -1 &&
                        notificationClientIdIndex != -1 && notificationClientIdIndex != 1 &&
                        notificationCourierIdIndex != -1 && notificationWasherIdIndex != -1 &&
                        notificationDateTimeIndex != -1)
                {
                    boolean isRead = cursor.getInt(notificationIsReadIndex) == 1;
                    Notification notification = new Notification(
                            cursor.getInt(notificationIdIndex),
                            cursor.getString(notificationTitleIndex),
                            cursor.getString(notificationMessageIndex),
                            isRead,
                            new Client(),
                            getCourier(cursor.getInt(notificationCourierIdIndex)),
                            new Washer(),
                            cursor.getString(notificationDateTimeIndex)
                    );

                    notifications.add(notification);
                }
                else
                {
                    Log.e("Notification Error DB", "one is -1");
                }
            }

        }catch(Exception e)
        {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        }finally
        {
            cursor.close();
        }

        return notifications;
    }


//    public void insertNotification(int washerID, String title, String message) {
//        SQLiteDatabase db = this.getWritableDatabase();  // Assuming 'this' refers to the Connect instance
//
//        ContentValues values = new ContentValues();
//        values.put("NOTIFICATION_WASHER_ID", washerID);
//        values.put("NOTIFICATION_TITLE", title);
//        values.put("NOTIFICATION_MESSAGE", message);
//        values.put("NOTIFICATION_IS_READ", 0);  // Assuming the notification is initially unread
//
//        // Get current date and time in the specified format
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
//        String dateTime = sdf.format(new Date());
//        values.put("NOTIFICATION_DATETIME", dateTime);
//
//        // Insert the values into the NOTIFICATION table
//        db.insert("NOTIFICATION", null, values);
//
//        // Close the database to avoid memory leaks
//        db.close();
//    }


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

    public boolean insertCourier(String username, String password, String name, String contactNo, String plateNo, int courierStatus, int overAllRating) {
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
            values.put(COURIER_OVERALL_RATING, overAllRating);

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
    public boolean insertWasher(String username, String password, String shopName, String shopLocation, String contactNo, int washerStatus, double ratePerKg, int overAllRating) {
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
            values.put(RATE_PER_KG, ratePerKg);
            values.put(WASHER_OVERALL_RATING, overAllRating);

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

    public boolean insertOrder(int orderID, int clientID, int washerID, int courier1ID,
                               double totalCourier1, String dateCourier1, int courier2ID,
                               double totalCourier2, String dateCourier2, double totalDue,
                               double totalPaid, boolean paymentStatus, double grandTotal,
                               String dateReceived) {
        boolean isInserted = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("ORDER_ID", orderID);
            values.put("CLIENT_ID", clientID);
            values.put("WASHER_ID", washerID);
            values.put("COURIER1_ID", courier1ID);
            values.put("TOTAL_COURIER1", totalCourier1);
            values.put("DATE_COURIER1", dateCourier1);
            values.put("COURIER2_ID", courier2ID);
            values.put("TOTAL_COURIER2", totalCourier2);
            values.put("DATE_COURIER2", dateCourier2);
            values.put("TOTAL_DUE", totalDue);
            values.put("TOTAL_PAID", totalPaid);
            values.put("PAYMENT_STATUS", paymentStatus ? 1 : 0); // 1 for true, 0 for false
            values.put("GRAND_TOTAL", grandTotal);
            values.put("DATE_RECEIVED", dateReceived);

            long newRowId = db.insert("ORDER_TABLE", null, values);

            // Check if the insertion was successful
            isInserted = newRowId != -1;
        } catch (Exception e) {
            Log.e("Database", "Error inserting order: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return isInserted;
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

    // Fetching client by ClientID

    public Client getClient(int clientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Client client = null;

        // Query the Client table
        Cursor cursor = db.rawQuery("SELECT * FROM CLIENT WHERE CUSTOMER_ID = ?", new String[]{String.valueOf(clientID)});

        if (cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(CLIENT_USERNAME);
            int passwordColumnIndex = cursor.getColumnIndex(CLIENT_PASSWORD);
            int nameColumnIndex = cursor.getColumnIndex(CLIENT_NAME);
            int contactNoColumnIndex = cursor.getColumnIndex(CLIENT_CONTACT_NO);
            int addressColumnIndex = cursor.getColumnIndex(CLIENT_ADDRESS);
            int paymentInfoColumnIndex = cursor.getColumnIndex(PAYMENT_INFO);

            // Check if the column indexes are valid
            if (usernameColumnIndex != -1 && passwordColumnIndex != -1 &&
                    nameColumnIndex != -1 && contactNoColumnIndex != -1 &&
                    addressColumnIndex != -1 && paymentInfoColumnIndex != -1) {

                // Create a new Client instance using the constructor
                client = new Client(
                        clientID,
                        cursor.getString(usernameColumnIndex),
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getString(addressColumnIndex),
                        cursor.getInt(paymentInfoColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return client;
    }

    // Fetching client by username
    public Client getClient(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Client client = null;

        // Query the Client table
        Cursor cursor = db.rawQuery("SELECT * FROM CLIENT WHERE CLIENT_USERNAME = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            int clientIDColumnIndex = cursor.getColumnIndex(CUSTOMER_ID);
            int passwordColumnIndex = cursor.getColumnIndex(CLIENT_PASSWORD);
            int nameColumnIndex = cursor.getColumnIndex(CLIENT_NAME);
            int contactNoColumnIndex = cursor.getColumnIndex(CLIENT_CONTACT_NO);
            int addressColumnIndex = cursor.getColumnIndex(CLIENT_ADDRESS);
            int paymentInfoColumnIndex = cursor.getColumnIndex(PAYMENT_INFO);

            // Check if the column indexes are valid
            if (clientIDColumnIndex != -1 && passwordColumnIndex != -1 &&
                    nameColumnIndex != -1 && contactNoColumnIndex != -1 &&
                    addressColumnIndex != -1 && paymentInfoColumnIndex != -1) {

                // Create a new Client instance using the constructor
                client = new Client(
                        cursor.getInt(clientIDColumnIndex),
                        username,
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getString(addressColumnIndex),
                        cursor.getInt(paymentInfoColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return client;
    }

    // Fetching courier by username
    public Courier getCourier(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Courier courier = null;

        // Query the Courier table
        Cursor cursor = db.rawQuery("SELECT * FROM COURIER WHERE COURIER_USERNAME = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            int courierIDColumnIndex = cursor.getColumnIndex(COURIER_ID);
            int usernameColumnIndex = cursor.getColumnIndex(COURIER_USERNAME);
            int passwordColumnIndex = cursor.getColumnIndex(COURIER_PASSWORD);
            int nameColumnIndex = cursor.getColumnIndex(COURIER_NAME);
            int contactNoColumnIndex = cursor.getColumnIndex(COURIER_CONTACT_NO);
            int plateNoColumnIndex = cursor.getColumnIndex(COURIER_PLATE_NO);
            int statusColumnIndex = cursor.getColumnIndex(COURIER_STATUS);
            int overAllRatingColumnIndex = cursor.getColumnIndex(COURIER_OVERALL_RATING);

            // Check if the column indexes are valid
            if (courierIDColumnIndex != -1 && usernameColumnIndex != -1 && passwordColumnIndex != -1 &&
                    nameColumnIndex != -1 && contactNoColumnIndex != -1 &&
                    plateNoColumnIndex != -1 && statusColumnIndex != -1) {

                // Create a new Courier instance using the constructor
                courier = new Courier(
                        cursor.getInt(courierIDColumnIndex),
                        cursor.getString(usernameColumnIndex),
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getString(plateNoColumnIndex),
                        cursor.getInt(statusColumnIndex) != 0,
                        cursor.getInt(overAllRatingColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return courier;
    }

    // Fetching courier by courierID
    public Courier getCourier(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Courier courier = null;

        // Query the Courier table
        Cursor cursor = db.rawQuery("SELECT * FROM COURIER WHERE COURIER_ID = ?", new String[]{String.valueOf(courierID)});

        if (cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(COURIER_USERNAME);
            int passwordColumnIndex = cursor.getColumnIndex(COURIER_PASSWORD);
            int nameColumnIndex = cursor.getColumnIndex(COURIER_NAME);
            int contactNoColumnIndex = cursor.getColumnIndex(COURIER_CONTACT_NO);
            int plateNoColumnIndex = cursor.getColumnIndex(COURIER_PLATE_NO);
            int statusColumnIndex = cursor.getColumnIndex(COURIER_STATUS);
            int overAllRatingColumnIndex = cursor.getColumnIndex(COURIER_OVERALL_RATING);


            // Check if the column indexes are valid
            if (usernameColumnIndex != -1 && passwordColumnIndex != -1 &&
                    nameColumnIndex != -1 && contactNoColumnIndex != -1 &&
                    plateNoColumnIndex != -1 && statusColumnIndex != -1) {

                // Create a new Courier instance using the constructor
                courier = new Courier(
                        courierID,
                        cursor.getString(usernameColumnIndex),
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getString(plateNoColumnIndex),
                        cursor.getInt(statusColumnIndex) != 0,
                        cursor.getInt(overAllRatingColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return courier;
    }

    // Fetching washer by washerID

    public Washer getWasher(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Washer washer = null;

        // Query the Washer table
        Cursor cursor = db.rawQuery("SELECT * FROM WASHER WHERE WASHER_ID = ?", new String[]{String.valueOf(washerID)});

        if (cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(WASHER_USERNAME);
            int passwordColumnIndex = cursor.getColumnIndex(WASHER_PASSWORD);
            int shopNameColumnIndex = cursor.getColumnIndex(SHOP_NAME);
            int shopLocationColumnIndex = cursor.getColumnIndex(SHOP_LOCATION);
            int contactNoColumnIndex = cursor.getColumnIndex(WASHER_CONTACT_NO);
            int ratePerKgColumnIndex = cursor.getColumnIndex(RATE_PER_KG);
            int statusColumnIndex = cursor.getColumnIndex(WASHER_STATUS);
            int overAllRatingColumnIndex = cursor.getColumnIndex(WASHER_OVERALL_RATING);

            // Check if the column indexes are valid
            if (usernameColumnIndex != -1 && passwordColumnIndex != -1 &&
                    shopNameColumnIndex != -1 && shopLocationColumnIndex != -1 &&
                    contactNoColumnIndex != -1 && ratePerKgColumnIndex != -1 &&
                    statusColumnIndex != -1) {

                // Create a new Washer instance using the constructor
                washer = new Washer(
                        washerID,
                        cursor.getString(usernameColumnIndex),
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(shopNameColumnIndex),
                        cursor.getString(shopLocationColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getDouble(ratePerKgColumnIndex),
                        cursor.getInt(statusColumnIndex) != 0,
                        cursor.getInt(overAllRatingColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return washer;
    }

    // Fetching washer using username
    public Washer getWasher(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Washer washer = null;

        // Query the Washer table
        Cursor cursor = db.rawQuery("SELECT * FROM WASHER WHERE WASHER_USERNAME = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            int washerIDColumnIndex = cursor.getColumnIndex(WASHER_ID);
            int passwordColumnIndex = cursor.getColumnIndex(WASHER_PASSWORD);
            int shopNameColumnIndex = cursor.getColumnIndex(SHOP_NAME);
            int shopLocationColumnIndex = cursor.getColumnIndex(SHOP_LOCATION);
            int contactNoColumnIndex = cursor.getColumnIndex(WASHER_CONTACT_NO);
            int ratePerKgColumnIndex = cursor.getColumnIndex(RATE_PER_KG);
            int statusColumnIndex = cursor.getColumnIndex(WASHER_STATUS);
            int overAllRatingColumnIndex = cursor.getColumnIndex(WASHER_OVERALL_RATING);

            // Check if the column indexes are valid
            if (washerIDColumnIndex != -1 && passwordColumnIndex != -1 &&
                    shopNameColumnIndex != -1 && shopLocationColumnIndex != -1 &&
                    contactNoColumnIndex != -1 && ratePerKgColumnIndex != -1 &&
                    statusColumnIndex != -1) {

                // Create a new Washer instance using the constructor
                washer = new Washer(
                        cursor.getInt(washerIDColumnIndex),
                        username,
                        cursor.getString(passwordColumnIndex),
                        cursor.getString(shopNameColumnIndex),
                        cursor.getString(shopLocationColumnIndex),
                        cursor.getString(contactNoColumnIndex),
                        cursor.getDouble(ratePerKgColumnIndex),
                        cursor.getInt(statusColumnIndex) != 0,
                        cursor.getInt(overAllRatingColumnIndex)
                );
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return washer;
    }

    public boolean hasActiveTransactionOnPhase1Order(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM PHASE1_ORDER " +
                "WHERE " + PHASE1_ORDER_COURIER_ID + " = ? AND (" +
                PHASE1_ORDER_STATUS + " IN (1, 2, 3) OR PHASE1_COURIER_STATUS = 0)";  // Assuming 1, 2, 3 represent active transaction statuses

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courierID)});
        if (cursor != null) {
            cursor.moveToFirst();
            int rowCount = cursor.getInt(0);
            cursor.close();
            return rowCount > 0;
        }

        return false;
    }

    public boolean hasActiveTransactionOnPhase2Order(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM PHASE2_ORDER " +
                "WHERE " + PHASE2_ORDER_COURIER_ID + " = ? AND (" +
                PHASE2_ORDER_STATUS + " IN (11,12,13,14,15) OR PHASE2_COURIER_STATUS = 0)";  // Assuming 1, 2, 3 represent active transaction statuses

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courierID)});
        if (cursor != null) {
            cursor.moveToFirst();
            int rowCount = cursor.getInt(0);
            cursor.close();
            return rowCount > 0;
        }

        return false;
    }

    public boolean hasCourierAlreadyReceivedPaymentPhase1(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM PHASE1_ORDER " +
                "WHERE " + PHASE1_ORDER_COURIER_ID + " = ? AND " +
                PHASE1_COURIER_STATUS + " = 1";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courierID)});
        if (cursor != null) {
            cursor.moveToFirst();
            int rowCount = cursor.getInt(0);
            cursor.close();
            return rowCount > 0;
        }

        return false;
    }

    public boolean setCourierStatusOnDatabase(int courierID, boolean newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("COURIER_STATUS", newStatus ? 1 : 0);  // Assuming 1 represents ON and 0 represents OFF

        int rowsAffected = db.update("COURIER", values, "COURIER_ID = ?", new String[]{String.valueOf(courierID)});

        return rowsAffected > 0;
    }

    public boolean setCourierStatusPhase1OrderOnDatabase(int courierID, boolean newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PHASE1_COURIER_STATUS", newStatus ? 1 : 0);  // Assuming 1 represents ON and 0 represents OFF

        int rowsAffected = db.update("PHASE1_ORDER", values, "PHASE1_ORDER_COURIER_ID = ?", new String[]{String.valueOf(courierID)});

        return rowsAffected > 0;
    }

    public boolean setCourierStatusPhase2OrderOnDatabase(int courierID, boolean newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PHASE2_COURIER_STATUS", newStatus ? 1 : 0);  // Assuming 1 represents ON and 0 represents OFF

        int rowsAffected = db.update("PHASE2_ORDER", values, "PHASE2_ORDER_COURIER_ID = ?", new String[]{String.valueOf(courierID)});

        return rowsAffected > 0;
    }

    public boolean updatePhase1OrderStatusOnDb(int courierID, int newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PHASE1_ORDER_STATUS", newStatus);

        int rowsAffected = db.update("PHASE1_ORDER", values, "PHASE1_ORDER_COURIER_ID = ?", new String[]{String.valueOf(courierID)});

        return rowsAffected > 0;
    }



    // pending client

    public List<Phase1Order> getPendingDeliveryOnClient(int clientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Phase1Order> pendingDeliveries = new ArrayList<>();
//
//        // Query the PHASE1_ORDER table for pending deliveries for the specified client
//        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? " +
//                "AND (PHASE1_ORDER_STATUS != -1)";

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? AND " +
                "(PHASE1_ORDER_STATUS != -1 AND PHASE1_ORDER_STATUS != 7) ORDER BY PHASE1_DATE_PLACED DESC";


//        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? " +
//                "AND PHASE1_COURIER_STATUS = 0";

        String[] selectionArgs = {String.valueOf(clientID)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try {

            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIdIndex = cursor.getColumnIndex(PHASE1_ORDER_ID);
                int washerIdIndex = cursor.getColumnIndex(PHASE1_ORDER_WASHER_ID);
                int courierIdIndex = cursor.getColumnIndex(PHASE1_ORDER_COURIER_ID);
                Courier courier;
                if (cursor.getInt(courierIdIndex) == -1) {
                    courier = new Courier();
                } else {
                    courier = getCourier(cursor.getInt(courierIdIndex));
                }

                //Log.e("COURIERINDEX", cursor.getInt(courierIdIndex) + "");
                int courierStatusIndex = cursor.getColumnIndex(PHASE1_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE1_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE1_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE1_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE1_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE1_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE1_DATE_RECEIVED);
                int initialLoadIndex = cursor.getColumnIndex(PHASE1_INITIAL_LOAD);
                int orderStatusIndex = cursor.getColumnIndex(PHASE1_ORDER_STATUS);
                int datePlacedIndex = cursor.getColumnIndex(PHASE1_DATE_PLACED);

                // Check if the column indexes are valid
                if (orderIdIndex != -1 && washerIdIndex != -1 && courierIdIndex != -1 &&
                        courierStatusIndex != -1 && totalCourierAmountIndex != -1 &&
                        dateCourierIndex != -1 && totalDueIndex != -1 &&
                        totalPaidIndex != -1 && paymentStatusIndex != -1 &&
                        dateReceivedIndex != -1) {

                    // Create a new Phase1Order instance using the constructor
                    Phase1Order pendingOrder = new Phase1Order(
                            cursor.getInt(orderIdIndex),
                            getClient(clientID),
                            getWasher(cursor.getInt(washerIdIndex)),
                            courier,
//                            getCourier(cursor.getInt(courierIdIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(initialLoadIndex),
                            cursor.getInt(orderStatusIndex),
                            cursor.getString(datePlacedIndex)
                    );

                    if(courier != null)
                    {
                        //Log.e("InsidePending", pendingOrder.getCourier().toString());
                    }

                    // Add the order to the list of pending deliveries
                    pendingDeliveries.add(pendingOrder);
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        } finally {
            // Close the cursor and database
            cursor.close();
            db.close();
        }
        return pendingDeliveries;
    }

    public List<Phase2Order> getPendingCollectOnClient(int clientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Phase2Order> pendingCollects = new ArrayList<>();

        // Query the PHASE2_ORDER table for pending collections for the specified client
        String query = "SELECT * FROM PHASE2_ORDER" +
                " WHERE " + PHASE2_ORDER_CLIENT_ID + " = ? AND " +
                "(" + PHASE2_ORDER_STATUS + " NOT IN (-1,16,22)) ORDER BY " + PHASE2_DATE_PLACED + " DESC";

        String[] selectionArgs = {String.valueOf(clientID)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        try {
            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIdIndex = cursor.getColumnIndex(PHASE2_ORDER_ID);
                int washerIdIndex = cursor.getColumnIndex(PHASE2_ORDER_WASHER_ID);
                int courierIdIndex = cursor.getColumnIndex(PHASE2_ORDER_COURIER_ID);
                int courierStatusIndex = cursor.getColumnIndex(PHASE2_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE2_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE2_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE2_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE2_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE2_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE2_DATE_RECEIVED);
                int orderStatusIndex = cursor.getColumnIndex(PHASE2_ORDER_STATUS);
                int refNoIndex = cursor.getColumnIndex(PHASE2_REFERENCE_NO);
                int datePlacedIndex = cursor.getColumnIndex(PHASE2_DATE_PLACED);
                int phase2Phase1OrderIndex = cursor.getColumnIndex(PHASE2_PHASE1_ORDER_ID);

                // Check if the column indexes are valid
                if (orderIdIndex != -1 && washerIdIndex != -1 && courierIdIndex != -1 &&
                        courierStatusIndex != -1 && totalCourierAmountIndex != -1 &&
                        dateCourierIndex != -1 && totalDueIndex != -1 &&
                        totalPaidIndex != -1 && paymentStatusIndex != -1 &&
                        dateReceivedIndex != -1 && orderStatusIndex != -1 &&
                        refNoIndex != -1 && datePlacedIndex != -1 && phase2Phase1OrderIndex != -1) {
                    Courier courier;

                    if(getCourier(cursor.getInt(courierIdIndex)) == null){
                        courier = new Courier();
                    }
                    else
                    {
                        courier = getCourier(cursor.getInt(courierIdIndex));
                    }

                    // Create a new Phase2Order instance using the constructor
                    Phase2Order pendingCollect = new Phase2Order(
                            cursor.getInt(orderIdIndex),
                            getClient(clientID),
                            getWasher(cursor.getInt(washerIdIndex)),
                            courier,
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(orderStatusIndex),
                            cursor.getString(refNoIndex),
                            cursor.getString(datePlacedIndex),
                            cursor.getInt(phase2Phase1OrderIndex)
                    );

                    pendingCollects.add(pendingCollect);
                } else {
                    // Handle invalid column indexes
                }
            }
        } catch (Exception e) {
            Log.e("PHASE 2 QUERY ERROR", e.getMessage());
        } finally {
            // Close the cursor and database
            cursor.close();
            db.close();
        }
        return pendingCollects;
    }



    // can be used with Client, Courier and Washer as History

    public List<Phase1Order> getHistoryList(String username) {
        List<Phase1Order> pendingDeliveries = new ArrayList<>();

        // generalized getHistory for all Actor
        // 0 - Client , 1 - Courier , 2 - Washer
        int typeOfUser = 0; // by default
        int ID = 0;
        Log.e("INSIDE HISTORY", "USER" + typeOfUser + "" + "ID" + ID);
        if (checkClientByUsername(username)) {
            typeOfUser = 0;
            Client client = getClient(username);
            ID = client.getCustomerID();
        } else if (checkCourierByUsername(username)) {
            typeOfUser = 1;
            Courier courier = getCourier(username);
            ID = courier.getCourierID();
        } else if (checkWasherByUsername(username)) {
            typeOfUser = 2;
            Washer washer = getWasher(username);
            ID = washer.getWasherID();
        } else {
            typeOfUser = -1;
        }

        Log.e("INSIDE HISTORY", "USER" + typeOfUser + "" + "ID" + ID);

        String query = "";
        switch (typeOfUser) {
            // Query Client
            case 0:
                query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? AND (PHASE1_ORDER_STATUS IN (-1,7)) ORDER BY PHASE1_DATE_PLACED DESC";
                break;
            // Query Courier
            case 1:
                query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_COURIER_ID = ? AND (PHASE1_ORDER_STATUS IN (-1,7)) ORDER BY PHASE1_DATE_PLACED DESC";
                break;
            // Query Washer
            case 2:
                query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND (PHASE1_ORDER_STATUS IN (-1,7)) ORDER BY PHASE1_DATE_PLACED DESC";
                break;
        }

        Log.e("INSIDE HISTORY QUERY", query);
//
//        // Query the PHASE1_ORDER table for pending deliveries for the specified client
        //String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? ";

        String[] selectionArgs = {String.valueOf(ID)};
        Log.e("SELECTIONARGS", selectionArgs[0]);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try {
            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIdIndex = cursor.getColumnIndex(PHASE1_ORDER_ID);
                int clientIdIndex = cursor.getColumnIndex(PHASE1_ORDER_CLIENT_ID);
                int washerIdIndex = cursor.getColumnIndex(PHASE1_ORDER_WASHER_ID);
                int courierIdIndex = cursor.getColumnIndex(PHASE1_ORDER_COURIER_ID);
                int courierStatusIndex = cursor.getColumnIndex(PHASE1_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE1_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE1_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE1_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE1_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE1_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE1_DATE_RECEIVED);
                int initialLoadIndex = cursor.getColumnIndex(PHASE1_INITIAL_LOAD);
                int orderStatusIndex = cursor.getColumnIndex(PHASE1_ORDER_STATUS);
                int datePlacedIndex = cursor.getColumnIndex(PHASE1_DATE_PLACED);

                // Check if the column indexes are valid
                if (orderIdIndex != -1 && washerIdIndex != -1 && courierIdIndex != -1 &&
                        courierStatusIndex != -1 && totalCourierAmountIndex != -1 &&
                        dateCourierIndex != -1 && totalDueIndex != -1 &&
                        totalPaidIndex != -1 && paymentStatusIndex != -1 &&
                        dateReceivedIndex != -1) {

                    // Create a new Phase1Order instance using the constructo
                    Phase1Order pendingOrder = new Phase1Order(
                            cursor.getInt(orderIdIndex),
                            getClient(cursor.getInt(clientIdIndex)),
                            getWasher(cursor.getInt(washerIdIndex)),
                            getCourier(cursor.getInt(courierIdIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(initialLoadIndex),
                            cursor.getInt(orderStatusIndex),
                            cursor.getString(datePlacedIndex)
                    );
                    Log.e("DATABASE PHASE1ORDER", pendingOrder.toString());

                    // Add the order to the list of pending deliveries
                    pendingDeliveries.add(pendingOrder);
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        } finally {
            // Close the cursor and database
            cursor.close();
        }

        return pendingDeliveries;
    }

//    public boolean insertFeedback(int clientID, String comment, int rating, int courierID,
//                                  int washerID, int orderID, int typeOfOrder) {
//        Log.e("INSIDE INSERTFBCK", "");
//        Log.e("CLIENTID", "" + clientID);
//        Log.e("commentStr", "" + comment);
//        Log.e("roundedRating", "" + rating);
//        Log.e("GetCourierID", "" + courierID);
//        Log.e("WasherID", "" + washerID);
//        Log.e("phase1Order.getOrderID()", "" + orderID);
//        Log.e("typeOfOrder", "" + typeOfOrder);
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues feedbackValues = new ContentValues();
//
//        // Set common values for both Phase1Order and Phase2Order
//        feedbackValues.put(FEEDBACK_COMMENT, comment);
//        feedbackValues.put(RATING, rating);
//        feedbackValues.put(FEEDBACK_CLIENT_ID, clientID);
//        feedbackValues.put(FEEDBACK_COURIER_ID, courierID);
//        feedbackValues.put(FEEDBACK_WASHER_ID, washerID);
//
//        // Set values specific to Phase1Order or Phase2Order
//        if(typeOfOrder == 1)
//        {
//            feedbackValues.put(FEEDBACK_PHASE1_ID, orderID);
//        }
//
//        if(typeOfOrder == 2)
//        {
//            feedbackValues.put(FEEDBACK_PHASE2_ID, orderID);
//        }
//
//        feedbackValues.put(FEEDBACK_HAS_RATED, 1);
//
//        // Insert the feedback into the database
//        long feedbackInsertResult = db.insert("FEEDBACK", null, feedbackValues);
//
//        return feedbackInsertResult != -1;
//    }

    public boolean insertFeedback(int clientID, String comment, int rating, int courierID,
                                  int washerID, int orderID, int typeOfOrder) {
        SQLiteDatabase db = this.getWritableDatabase();

        String tableName = "FEEDBACK";
        String orderIdColumn = "";

        switch (typeOfOrder) {
            case 1:
                orderIdColumn = "FEEDBACK_PHASE1_ID";
                break;
            case 2:
                orderIdColumn = "FEEDBACK_PHASE2_ID";
                break;
            default:
                return false;
        }

        String query = "INSERT INTO " + tableName + " (" +
                "FEEDBACK_COMMENT, RATING, FEEDBACK_CLIENT_ID, FEEDBACK_COURIER_ID, " +
                "FEEDBACK_WASHER_ID, " + orderIdColumn + ", FEEDBACK_HAS_RATED) " +
                "VALUES (?, ?, ?, ?, ?, ?, 1)";

        try {
            db.execSQL(query, new Object[]{comment, rating, clientID, courierID, washerID, orderID});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public void updateWasherOverallRating(int washerID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Calculate the average rating from the feedback table
        String avgRatingQuery = "SELECT AVG(" + RATING + ") AS avgRating FROM " + "FEEDBACK" +
                " WHERE " + FEEDBACK_WASHER_ID + " = ?";
        Cursor cursor = db.rawQuery(avgRatingQuery, new String[]{String.valueOf(washerID)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double averageRating = cursor.getDouble(cursor.getColumnIndex("avgRating"));

            // Round up the average rating using Math.ceil and cast to int
            int roundedAverageRating = (int) Math.ceil(averageRating);

            // Update the WASHER_OVERALL_RATING in the WASHER table
            ContentValues values = new ContentValues();
            values.put(WASHER_OVERALL_RATING, roundedAverageRating);

            db.update("WASHER", values, WASHER_ID + " = ?", new String[]{String.valueOf(washerID)});
        }

        cursor.close();
        db.close();
    }

    public void updateCourierOverallRating(int courierID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Calculate the average rating from the feedback table for the courier
        String avgRatingQuery = "SELECT AVG(" + RATING + ") AS avgRating FROM " + "FEEDBACK" +
                " WHERE " + FEEDBACK_COURIER_ID + " = ?";
        Cursor cursor = db.rawQuery(avgRatingQuery, new String[]{String.valueOf(courierID)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double averageRating = cursor.getDouble(cursor.getColumnIndex("avgRating"));

            // Round up the average rating using Math.ceil and cast to int
            int roundedAverageRating = (int) Math.ceil(averageRating);

            // Update the COURIER_OVERALL_RATING in the COURIER table
            ContentValues values = new ContentValues();
            values.put(COURIER_OVERALL_RATING, roundedAverageRating);

            db.update("COURIER", values, COURIER_ID + " = ?", new String[]{String.valueOf(courierID)});
        }

        cursor.close();
        db.close();
    }


    public List<Phase1Order> getToFeedbackListOnPhase1(int clientID) {
        List<Phase1Order> pendingFeedback = new ArrayList<>();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_CLIENT_ID = ? AND (PHASE1_ORDER_STATUS IN (-1,7)) ORDER BY PHASE1_DATE_PLACED DESC";

        String[] selectionArgs = {String.valueOf(clientID)};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try {
            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIdIndex = cursor.getColumnIndex(PHASE1_ORDER_ID);
                int clientIdIndex = cursor.getColumnIndex(PHASE1_ORDER_CLIENT_ID);
                int washerIdIndex = cursor.getColumnIndex(PHASE1_ORDER_WASHER_ID);
                int courierIdIndex = cursor.getColumnIndex(PHASE1_ORDER_COURIER_ID);
                int courierStatusIndex = cursor.getColumnIndex(PHASE1_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE1_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE1_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE1_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE1_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE1_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE1_DATE_RECEIVED);
                int initialLoadIndex = cursor.getColumnIndex(PHASE1_INITIAL_LOAD);
                int orderStatusIndex = cursor.getColumnIndex(PHASE1_ORDER_STATUS);
                int datePlacedIndex = cursor.getColumnIndex(PHASE1_DATE_PLACED);

                // Check if the column indexes are valid
                if (orderIdIndex != -1 && washerIdIndex != -1 && courierIdIndex != -1 &&
                        courierStatusIndex != -1 && totalCourierAmountIndex != -1 &&
                        dateCourierIndex != -1 && totalDueIndex != -1 &&
                        totalPaidIndex != -1 && paymentStatusIndex != -1 &&
                        dateReceivedIndex != -1) {

                    // Create a new Phase1Order instance using the constructo
                    Phase1Order pendingOrder = new Phase1Order(
                            cursor.getInt(orderIdIndex),
                            getClient(cursor.getInt(clientIdIndex)),
                            getWasher(cursor.getInt(washerIdIndex)),
                            getCourier(cursor.getInt(courierIdIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(initialLoadIndex),
                            cursor.getInt(orderStatusIndex),
                            cursor.getString(datePlacedIndex)
                    );

                    if(!hasAlreadyRatedBefore(clientID,pendingOrder.getOrderID(),1))
                    {
                        pendingFeedback.add(pendingOrder);
                    }
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        } finally {
            // Close the cursor and database
            cursor.close();
        }

        return pendingFeedback;
    }

    public List<Phase2Order> getToFeedbackListOnPhase2(int clientID) {
        List<Phase2Order> pendingFeedback = new ArrayList<>();

        String query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_CLIENT_ID = ? AND (PHASE2_ORDER_STATUS IN (-1,16,22)) ORDER BY PHASE2_DATE_PLACED DESC";

        String[] selectionArgs = {String.valueOf(clientID)};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try {
            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIDIndex = cursor.getColumnIndex(PHASE2_ORDER_ID);
                int clientIDIndex = cursor.getColumnIndex(PHASE2_ORDER_CLIENT_ID);
                int washerIDIndex = cursor.getColumnIndex(PHASE2_ORDER_WASHER_ID);
                int courierIDIndex = cursor.getColumnIndex(PHASE2_ORDER_COURIER_ID);
                int courierStatusIndex = cursor.getColumnIndex(PHASE2_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE2_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE2_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE2_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE2_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE2_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE2_DATE_RECEIVED);
                int phase2OrderStatusIndex = cursor.getColumnIndex(PHASE2_ORDER_STATUS);
                int referenceNoIndex = cursor.getColumnIndex(PHASE2_REFERENCE_NO);
                int datePlacedIndex = cursor.getColumnIndex(PHASE2_DATE_PLACED);
                int phase1OrderIDIndex = cursor.getColumnIndex(PHASE2_PHASE1_ORDER_ID);

                // Check if the column indexes are valid
                if (orderIDIndex != -1 && clientIDIndex != -1 && washerIDIndex != -1 &&
                        courierIDIndex != -1 && courierStatusIndex != -1 &&
                        totalCourierAmountIndex != -1 && dateCourierIndex != -1 &&
                        totalDueIndex != -1 && totalPaidIndex != -1 &&
                        paymentStatusIndex != -1 && dateReceivedIndex != -1 &&
                        phase2OrderStatusIndex != -1 && referenceNoIndex != -1 &&
                        datePlacedIndex != -1 && phase1OrderIDIndex != -1) {

                    // Create a new Phase2Order instance using the constructor
                    Phase2Order feedbackOrder = new Phase2Order(
                            cursor.getInt(orderIDIndex),
                            getClient(cursor.getInt(clientIDIndex)),
                            getWasher(cursor.getInt(washerIDIndex)),
                            getCourier(cursor.getInt(courierIDIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(phase2OrderStatusIndex),
                            cursor.getString(referenceNoIndex),
                            cursor.getString(datePlacedIndex),
                            cursor.getInt(phase1OrderIDIndex)
                    );

                    Log.e("feedback Order", feedbackOrder + "");
                    if(!hasAlreadyRatedBefore(clientID,feedbackOrder.getOrderID(),2))
                    {
                        boolean hasAlreadyRated = hasAlreadyRatedBefore(clientID,feedbackOrder.getOrderID(),2);
                        Log.e("Already Rated", hasAlreadyRated + "");
                        pendingFeedback.add(feedbackOrder);
                    }
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        } finally {
            // Close the cursor and database
            cursor.close();
        }

        Log.e("pendingFeedback size", pendingFeedback.size() + "");

        return pendingFeedback;
    }

    public boolean hasAlreadyRatedBefore(int clientID, int orderID, int typeOfOrder) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        String tableName = "";
        String orderIdColumnName = "";

        switch (typeOfOrder) {
            case 1:
                tableName = "PHASE1_ORDER";
                orderIdColumnName = "PHASE1_ORDER_ID";
                break;
            case 2:
                tableName = "PHASE2_ORDER";
                orderIdColumnName = "PHASE2_ORDER_ID";
                break;
        }

        // Check if the order exists in the specified table for the given client
        String orderQuery = "SELECT * FROM " + tableName + " WHERE " +
                (typeOfOrder == 1 ? "PHASE1_ORDER_CLIENT_ID" : "PHASE2_ORDER_CLIENT_ID") + " = ? AND " +
                orderIdColumnName + " = ?";

        Cursor orderCursor = db.rawQuery(orderQuery, new String[]{String.valueOf(clientID), String.valueOf(orderID)});

        if (orderCursor.moveToFirst()) {
            // Order exists, check if the client has rated it
            int orderIndex = orderCursor.getColumnIndex(orderIdColumnName);
            int orderIDFromDB = orderCursor.getInt(orderIndex);

            String feedbackQuery = "SELECT * FROM FEEDBACK WHERE FEEDBACK_CLIENT_ID = ? AND " + (typeOfOrder == 1 ? "FEEDBACK_PHASE1_ID" : "FEEDBACK_PHASE2_ID") + " = ?";
            Cursor feedbackCursor = db.rawQuery(feedbackQuery, new String[]{String.valueOf(clientID), String.valueOf(orderIDFromDB)});

            if (feedbackCursor.moveToFirst()) {
                // Client has already rated this order
                int hasRatedIndex = feedbackCursor.getColumnIndex("FEEDBACK_HAS_RATED");
                int hasRated = feedbackCursor.getInt(hasRatedIndex);
                return hasRated == 1;
            }
        }

        return false;
    }


    public boolean acceptPendingRequestOnCourier(int courierID, int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE PHASE1_ORDER SET PHASE1_ORDER_COURIER_ID = " + courierID +
                " WHERE PHASE1_ORDER_ID = " + orderID;
        try {
            db.execSQL(query);
            return true;  // Operation successful
        } catch (SQLException e) {
            Log.e("AcceptPendingRequest", "Error updating database: " + e.getMessage());
            return false;  // Operation failed
        } finally {
            db.close();
        }
    }

    public List<Phase2Order> getHistoryListOnPhase2Order(String username) {
        List<Phase2Order> historyList = new ArrayList<>();

        // generalized getHistory for all Actor on Phase 2
        // 0 - Client , 1 - Courier , 2 - Washer
        int typeOfUser = 0; // by default
        int ID = 0;
        Log.e("INSIDE HISTORY", "USER" + typeOfUser + "" + "ID" + ID);
        if (checkClientByUsername(username)) {
            typeOfUser = 0;
            Client client = getClient(username);
            ID = client.getCustomerID();
        } else if (checkCourierByUsername(username)) {
            typeOfUser = 1;
            Courier courier = getCourier(username);
            ID = courier.getCourierID();
        } else if (checkWasherByUsername(username)) {
            typeOfUser = 2;
            Washer washer = getWasher(username);
            ID = washer.getWasherID();
        } else {
            typeOfUser = -1;
        }

        String query = "";
        switch (typeOfUser) {
            // Query Client
            case 0:
                query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_CLIENT_ID = ? AND (PHASE2_ORDER_STATUS IN (-1,16,22)) ORDER BY PHASE2_DATE_PLACED DESC";
                break;
            // Query Courier
            case 1:
                query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_COURIER_ID = ? AND (PHASE2_ORDER_STATUS IN (-1,16,22)) ORDER BY PHASE2_DATE_PLACED DESC";
                break;
            // Query Washer
            case 2:
                query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_WASHER_ID = ? AND (PHASE2_ORDER_STATUS IN (-1,16,22)) ORDER BY PHASE2_DATE_PLACED DESC";
                break;
        }

        String[] selectionArgs = {String.valueOf(ID)};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);

        try {
            while (cursor.moveToNext()) {
                // Extract column indexes for Phase2Order
                int orderIDIndex = cursor.getColumnIndex(PHASE2_ORDER_ID);
                int clientIDIndex = cursor.getColumnIndex(PHASE2_ORDER_CLIENT_ID);
                int washerIDIndex = cursor.getColumnIndex(PHASE2_ORDER_WASHER_ID);
                int courierIDIndex = cursor.getColumnIndex(PHASE2_ORDER_COURIER_ID);
                int courierStatusIndex = cursor.getColumnIndex(PHASE2_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE2_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE2_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE2_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE2_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE2_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE2_DATE_RECEIVED);
                int phase2OrderStatusIndex = cursor.getColumnIndex(PHASE2_ORDER_STATUS);
                int referenceNoIndex = cursor.getColumnIndex(PHASE2_REFERENCE_NO);
                int datePlacedIndex = cursor.getColumnIndex(PHASE2_DATE_PLACED);
                int phase1OrderIDIndex = cursor.getColumnIndex(PHASE2_PHASE1_ORDER_ID);

                // Check if the column indexes are valid
                if (orderIDIndex != -1 && clientIDIndex != -1 && washerIDIndex != -1 &&
                        courierIDIndex != -1 && courierStatusIndex != -1 &&
                        totalCourierAmountIndex != -1 && dateCourierIndex != -1 &&
                        totalDueIndex != -1 && totalPaidIndex != -1 &&
                        paymentStatusIndex != -1 && dateReceivedIndex != -1 &&
                        phase2OrderStatusIndex != -1 && referenceNoIndex != -1 &&
                        datePlacedIndex != -1 && phase1OrderIDIndex != -1) {

                    // Create a new Phase2Order instance using the constructor
                    Phase2Order historyOrder = new Phase2Order(
                            cursor.getInt(orderIDIndex),
                            getClient(cursor.getInt(clientIDIndex)),
                            getWasher(cursor.getInt(washerIDIndex)),
                            getCourier(cursor.getInt(courierIDIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(phase2OrderStatusIndex),
                            cursor.getString(referenceNoIndex),
                            cursor.getString(datePlacedIndex),
                            cursor.getInt(phase1OrderIDIndex)
                    );

                    Log.e("DATABASE PHASE2ORDER", historyOrder.toString());

                    // Add the order to the history list
                    historyList.add(historyOrder);
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage());
        } finally {
            // Close the cursor and database
            cursor.close();
        }

        return historyList;
    }

    public int getCourierStatus(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COURIER_STATUS FROM COURIER WHERE COURIER_ID = " + courierID;
        Cursor cursor = db.rawQuery(query, null);

        try {
            int courierIdIndex = cursor.getColumnIndex(COURIER_ID);
            if (cursor.moveToFirst()) {
                if (cursor.getInt(courierIdIndex) != -1) {
                    return cursor.getInt(courierIdIndex);
                }
            }
        } catch (SQLException e) {
            Log.e("getCourierStatus", e.getMessage().toString());
        } finally {
            db.close();
        }

        return -1;
    }

    public boolean updateCourierStatus(int courierID, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE COURIER SET COURIER_STATUS = " + status + " WHERE COURIER_ID = " + courierID;
        try {
            db.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("updateCourierStatus", "Error updating database: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public List<Phase1Order> getPendingRequestOnCourier() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Phase1Order> pendingDeliveries = new ArrayList<>();
//
//        // Query the PHASE1_ORDER table for pending request on courier
        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_COURIER_ID = -1";
        Cursor cursor = db.rawQuery(query, null);
        try {

            while (cursor.moveToNext()) {
                // Extract column indexes
                int orderIdIndex = cursor.getColumnIndex(PHASE1_ORDER_ID);
                int clientIdIndex = cursor.getColumnIndex(PHASE1_ORDER_CLIENT_ID);
                Log.e("CLIENTID", cursor.getInt(clientIdIndex) + "");
                int washerIdIndex = cursor.getColumnIndex(PHASE1_ORDER_WASHER_ID);
                Log.e("WASHERID", cursor.getInt(washerIdIndex) + "");
                int courierIdIndex = cursor.getColumnIndex(PHASE1_ORDER_COURIER_ID);
                Courier courier = new Courier();
                Log.e("COURIERID", cursor.getInt(courierIdIndex) + "");
                int courierStatusIndex = cursor.getColumnIndex(PHASE1_COURIER_STATUS);
                int totalCourierAmountIndex = cursor.getColumnIndex(PHASE1_TOTAL_COURIER_AMOUNT);
                int dateCourierIndex = cursor.getColumnIndex(PHASE1_DATE_COURIER);
                int totalDueIndex = cursor.getColumnIndex(PHASE1_TOTAL_DUE);
                int totalPaidIndex = cursor.getColumnIndex(PHASE1_TOTAL_PAID);
                int paymentStatusIndex = cursor.getColumnIndex(PHASE1_PAYMENT_STATUS);
                int dateReceivedIndex = cursor.getColumnIndex(PHASE1_DATE_RECEIVED);
                int initialLoadIndex = cursor.getColumnIndex(PHASE1_INITIAL_LOAD);
                int orderStatusIndex = cursor.getColumnIndex(PHASE1_ORDER_STATUS);
                int datePlacedIndex = cursor.getColumnIndex(PHASE1_DATE_PLACED);

                // Check if the column indexes are valid
                if (orderIdIndex != -1 && washerIdIndex != -1 && courierIdIndex != -1 &&
                        courierStatusIndex != -1 && totalCourierAmountIndex != -1 &&
                        dateCourierIndex != -1 && totalDueIndex != -1 &&
                        totalPaidIndex != -1 && paymentStatusIndex != -1 &&
                        dateReceivedIndex != -1) {

                    // Create a new Phase1Order instance using the constructor
                    Phase1Order pendingOrder = new Phase1Order(
                            cursor.getInt(orderIdIndex),
                            getClient(cursor.getInt(clientIdIndex)),
                            getWasher(cursor.getInt(washerIdIndex)),
                            courier,
//                            getCourier(cursor.getInt(courierIdIndex)),
                            cursor.getInt(courierStatusIndex),
                            cursor.getDouble(totalCourierAmountIndex),
                            cursor.getString(dateCourierIndex),
                            cursor.getDouble(totalDueIndex),
                            cursor.getDouble(totalPaidIndex),
                            cursor.getInt(paymentStatusIndex),
                            cursor.getString(dateReceivedIndex),
                            cursor.getInt(initialLoadIndex),
                            cursor.getInt(orderStatusIndex),
                            cursor.getString(datePlacedIndex)
                    );

                    Log.e("CourierInsidePending", pendingOrder.getCourier().toString());
                    Log.e("ClientInsidePending", pendingOrder.getClient().toString());

                    // Add the order to the list of pending deliveries
                    pendingDeliveries.add(pendingOrder);
                } else {
                    // Handle invalid column indexes, log an error, or throw an exception
                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR PENDING", e.getMessage().toString());
        } finally {
            // Close the cursor and database
            cursor.close();
            db.close();
        }
        return pendingDeliveries;
    }

    public Phase1Order getPendingDeliveryOnCourier(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Phase1Order pendingDelivery = null;

        // Query the PHASE1_ORDER table for pending deliveries for the specified courier
        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_COURIER_ID = ? AND (PHASE1_COURIER_STATUS = 0 OR PHASE1_DATE_RECEIVED = '')";
        String[] selectionArgs = {String.valueOf(courierID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            // Check if the column index is valid before accessing the cursor
            int orderIdIndex = cursor.getColumnIndex(PHASE1_ORDER_ID);
            int clientIdIndex = cursor.getColumnIndex(PHASE1_ORDER_CLIENT_ID);
            int washerIdIndex = cursor.getColumnIndex(PHASE1_ORDER_WASHER_ID);
            int courierIdIndex = cursor.getColumnIndex(PHASE1_ORDER_COURIER_ID);
            int courierStatusIndex = cursor.getColumnIndex(PHASE1_COURIER_STATUS);
            int totalCourierAmountIndex = cursor.getColumnIndex(PHASE1_TOTAL_COURIER_AMOUNT);
            int dateCourierIndex = cursor.getColumnIndex(PHASE1_DATE_COURIER);
            int totalDueIndex = cursor.getColumnIndex(PHASE1_TOTAL_DUE);
            int totalPaidIndex = cursor.getColumnIndex(PHASE1_TOTAL_PAID);
            int paymentStatusIndex = cursor.getColumnIndex(PHASE1_PAYMENT_STATUS);
            int dateReceivedIndex = cursor.getColumnIndex(PHASE1_DATE_RECEIVED);
            int initialLoadIndex = cursor.getColumnIndex(PHASE1_INITIAL_LOAD);
            int orderStatusIndex = cursor.getColumnIndex(PHASE1_ORDER_STATUS);
            int datePlacedIndex = cursor.getColumnIndex(PHASE1_DATE_PLACED);

            if (orderIdIndex != -1 && clientIdIndex != -1 && washerIdIndex != -1 &&
                    courierIdIndex != -1 && courierStatusIndex != -1 &&
                    totalCourierAmountIndex != -1 && dateCourierIndex != -1 &&
                    totalDueIndex != -1 && totalPaidIndex != -1 &&
                    paymentStatusIndex != -1 && dateReceivedIndex != -1) {

                pendingDelivery = new Phase1Order(
                        cursor.getInt(orderIdIndex),
                        getClient(cursor.getInt(clientIdIndex)),
                        getWasher(cursor.getInt(washerIdIndex)),
                        getCourier(cursor.getInt(courierIdIndex)),
                        cursor.getInt(courierStatusIndex),
                        cursor.getDouble(totalCourierAmountIndex),
                        cursor.getString(dateCourierIndex),
                        cursor.getDouble(totalDueIndex),
                        cursor.getDouble(totalPaidIndex),
                        cursor.getInt(paymentStatusIndex),
                        cursor.getString(dateReceivedIndex),
                        cursor.getInt(initialLoadIndex),
                        cursor.getInt(orderStatusIndex),
                        cursor.getString(datePlacedIndex)
                );

                Log.e("pendingDelivery", pendingDelivery.toString());
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return pendingDelivery;
    }


    public Phase2Order getPendingDeliveryOnCourierOnPhase2(int courierID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Phase2Order pendingDelivery = null;

        // Query the PHASE2_ORDER table for pending deliveries for the specified courier
        String query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_COURIER_ID = ? AND (PHASE2_COURIER_STATUS = 0 OR PHASE2_DATE_RECEIVED = '')";
        String[] selectionArgs = {String.valueOf(courierID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            // Check if the column index is valid before accessing the cursor
            int orderIdIndex = cursor.getColumnIndex(PHASE2_ORDER_ID);
            int clientIdIndex = cursor.getColumnIndex(PHASE2_ORDER_CLIENT_ID);
            int washerIdIndex = cursor.getColumnIndex(PHASE2_ORDER_WASHER_ID);
            int courierIdIndex = cursor.getColumnIndex(PHASE2_ORDER_COURIER_ID);
            int courierStatusIndex = cursor.getColumnIndex(PHASE2_COURIER_STATUS);
            int totalCourierAmountIndex = cursor.getColumnIndex(PHASE2_TOTAL_COURIER_AMOUNT);
            int dateCourierIndex = cursor.getColumnIndex(PHASE2_DATE_COURIER);
            int totalDueIndex = cursor.getColumnIndex(PHASE2_TOTAL_DUE);
            int totalPaidIndex = cursor.getColumnIndex(PHASE2_TOTAL_PAID);
            int paymentStatusIndex = cursor.getColumnIndex(PHASE2_PAYMENT_STATUS);
            int dateReceivedIndex = cursor.getColumnIndex(PHASE2_DATE_RECEIVED);
            int phase2OrderStatusIndex = cursor.getColumnIndex(PHASE2_ORDER_STATUS);
            int referenceNoIndex = cursor.getColumnIndex(PHASE2_REFERENCE_NO);
            int datePlacedIndex = cursor.getColumnIndex(PHASE2_DATE_PLACED);
            int phase1OrderIDIndex = cursor.getColumnIndex(PHASE2_PHASE1_ORDER_ID);

            if (orderIdIndex != -1 && clientIdIndex != -1 && washerIdIndex != -1 &&
                    courierIdIndex != -1 && courierStatusIndex != -1 &&
                    totalCourierAmountIndex != -1 && dateCourierIndex != -1 &&
                    totalDueIndex != -1 && totalPaidIndex != -1 &&
                    paymentStatusIndex != -1 && dateReceivedIndex != -1 &&
                    phase2OrderStatusIndex != -1 && referenceNoIndex != -1 &&
                    datePlacedIndex != -1 && phase1OrderIDIndex != -1) {

                pendingDelivery = new Phase2Order(
                        cursor.getInt(orderIdIndex),
                        getClient(cursor.getInt(clientIdIndex)),
                        getWasher(cursor.getInt(washerIdIndex)),
                        getCourier(cursor.getInt(courierIdIndex)),
                        cursor.getInt(courierStatusIndex),
                        cursor.getDouble(totalCourierAmountIndex),
                        cursor.getString(dateCourierIndex),
                        cursor.getDouble(totalDueIndex),
                        cursor.getDouble(totalPaidIndex),
                        cursor.getInt(paymentStatusIndex),
                        cursor.getString(dateReceivedIndex),
                        cursor.getInt(phase2OrderStatusIndex),
                        cursor.getString(referenceNoIndex),
                        cursor.getString(datePlacedIndex),
                        cursor.getInt(phase1OrderIDIndex)
                );

                Log.e("pendingDelivery", pendingDelivery.toString());
            } else {
                // Handle invalid column indexes, log an error, or throw an exception
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return pendingDelivery;
    }

    public List<Phase1Order> getWasherPhase1OrderHistory(int washerID, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN ( -1,7) ;";
        String[] selectionArgs = {String.valueOf(washerID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase1Order> OrderToReceiveList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phase1Order addOrder = new Phase1Order();
                addOrder.setOrderID(cursor.getInt(0));
                addOrder.setClient(this.getClient(cursor.getInt(1)));
                addOrder.setWasher(this.getWasher(cursor.getInt(2)));
                addOrder.setCourier(this.getCourier(cursor.getInt(3)));
                addOrder.setCourierStatus(cursor.getInt(4));
                addOrder.setTotalCourierAmount(cursor.getFloat(5));
                addOrder.setDateCourier(cursor.getString(6));
                addOrder.setTotalDue(cursor.getFloat(7));
                addOrder.setTotalPaid(cursor.getFloat(8));
                addOrder.setPaymentStatus(cursor.getInt(9));
                addOrder.setDateReceived(cursor.getString(10));
                addOrder.setInitialLoad(cursor.getInt(11));
                addOrder.setPhase1OrderStatus(cursor.getInt(12));
                addOrder.setDatePlaced(cursor.getString(13));

                OrderToReceiveList.add(addOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.getReadableDatabase().close();

        return OrderToReceiveList;
    }
    private boolean isFiveMinutesGreater(Date date1, Date date2) {
        long differenceInMillis = date2.getTime() - date1.getTime();
        long differenceInMinutes = differenceInMillis / (60 * 1000);

        // Check if the difference is greater than 5 minutes
        Log.e("5MINUTES",""+ differenceInMillis);
        return differenceInMinutes >= 1;
    }
    public List<Phase1Order> getPendingDeliveriesOnWasher(int washerID, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN (0,1,2,3) ORDER BY PHASE1_ORDER_STATUS ;";
        String[] selectionArgs = {String.valueOf(washerID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase1Order> OrderToReceiveList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                Date currentDate = new Date();
                String dateString = cursor.getString(13);

// Parse the date string into a Date object
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date formattedDate = null;
                try {
                    formattedDate = simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


// Check if the formatted date is 5 minutes or more greater than the current date
                boolean result = isFiveMinutesGreater( formattedDate,currentDate);

                // dont store if phase1order is greater than 1 minute
                if(result == true && cursor.getInt(12) == 0){
                    continue;
                }

                Phase1Order addOrder = new Phase1Order();
                addOrder.setOrderID(cursor.getInt(0));
                addOrder.setClient(this.getClient(cursor.getInt(1)));
                addOrder.setWasher(this.getWasher(cursor.getInt(2)));
                addOrder.setCourier(this.getCourier(cursor.getInt(3)));
                addOrder.setCourierStatus(cursor.getInt(4));
                addOrder.setTotalCourierAmount(cursor.getFloat(5));
                addOrder.setDateCourier(cursor.getString(6));
                addOrder.setTotalDue(cursor.getFloat(7));
                addOrder.setTotalPaid(cursor.getFloat(8));
                addOrder.setPaymentStatus(cursor.getInt(9));
                addOrder.setDateReceived(cursor.getString(10));
                addOrder.setInitialLoad(cursor.getInt(11));
                addOrder.setPhase1OrderStatus(cursor.getInt(12));
                addOrder.setDatePlaced(cursor.getString(13));

                OrderToReceiveList.add(addOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.getReadableDatabase().close();

        return OrderToReceiveList;
    }


    // Get all Washers in Book Service Query Washer
    public List<Washer> getAllWashers() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Washer> retrieveWashers = new ArrayList<>();

        String query = "SELECT * FROM WASHER WHERE WASHER_STATUS = 1";
        Cursor cursor = db.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                int washerIdIndex = cursor.getColumnIndex(WASHER_ID);
                int washerUsernameIndex = cursor.getColumnIndex(WASHER_USERNAME);
                int washerPasswordIndex = cursor.getColumnIndex(WASHER_PASSWORD);
                int washerShopNameIndex = cursor.getColumnIndex(SHOP_NAME);
                int washerShopLocationIndex = cursor.getColumnIndex(SHOP_LOCATION);
                int washerContactNoIndex = cursor.getColumnIndex(WASHER_CONTACT_NO);
                int washerStatusIndex = cursor.getColumnIndex(WASHER_STATUS);
                int washerRatePerKgIndex = cursor.getColumnIndex(RATE_PER_KG);

                // check if valid
                if (washerIdIndex != -1 && washerUsernameIndex != -1 && washerPasswordIndex != -1
                        && washerShopNameIndex != -1 && washerShopLocationIndex != -1 &&
                        washerContactNoIndex != -1 && washerStatusIndex != -1 &&
                        washerRatePerKgIndex != -1) {
                    Washer currWasher = getWasher(cursor.getInt(washerIdIndex));
                    Log.e("CURRWASHER", currWasher.toString());
                    retrieveWashers.add(currWasher);
                } else {

                }
            }
        } catch (Exception e) {
            Log.e("DATABASE ERROR", e.getMessage().toString());
        } finally {
            cursor.close();
            db.close();
        }

        return retrieveWashers;
    }

    public boolean insertPhase1Order(int clientID, int washerID, int initialLoad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PHASE1_ORDER_CLIENT_ID, clientID);
        values.put(PHASE1_ORDER_WASHER_ID, washerID);
        values.put(PHASE1_COURIER_STATUS, 0);
        values.put(PHASE1_TOTAL_COURIER_AMOUNT, 50.0);
        values.put(PHASE1_DATE_COURIER, "");
        values.put(PHASE1_TOTAL_DUE, 0.0);
        values.put(PHASE1_TOTAL_PAID, 0.0);
        values.put(PHASE1_PAYMENT_STATUS, 0);
        values.put(PHASE1_DATE_RECEIVED, "");
        values.put(PHASE1_INITIAL_LOAD, initialLoad);
        values.put(PHASE1_ORDER_STATUS, 0);

        // Set PHASE1_DATE_PLACED to the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        values.put(PHASE1_DATE_PLACED, currentDate);

        long result = db.insert("PHASE1_ORDER", null, values);

        db.close();
        return result != -1;
    }

    public boolean insertPhase2Order(int clientID, int washerID, double totalDue, int phase2_phase1OrderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PHASE2_ORDER_CLIENT_ID, clientID);
        values.put(PHASE2_ORDER_WASHER_ID, washerID);
        values.put(PHASE2_COURIER_STATUS, 0);
        values.put(PHASE2_TOTAL_COURIER_AMOUNT, 50.0);
        values.put(PHASE2_DATE_COURIER, "");
        values.put(PHASE2_TOTAL_DUE, totalDue + 50);
        values.put(PHASE2_TOTAL_PAID, 0.0);
        values.put(PHASE2_PAYMENT_STATUS, 0);
        values.put(PHASE2_DATE_RECEIVED, "");
        values.put(PHASE2_ORDER_STATUS, 0);
        values.put(PHASE2_REFERENCE_NO, "");
        values.put(PHASE2_PHASE1_ORDER_ID, phase2_phase1OrderID);


        // Set PHASE1_DATE_PLACED to the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        values.put(PHASE2_DATE_PLACED, currentDate);

        long result = db.insert("PHASE2_ORDER", null, values);

        db.close();
        return result != -1;
    }

    public boolean updateReferenceNo(int orderID, String newReferenceNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHASE2_REFERENCE_NO, newReferenceNo);

        String whereClause = PHASE2_ORDER_ID + " = ?";
        String[] whereArgs = {String.valueOf(orderID)};

        int rowsAffected = db.update("PHASE2_ORDER", values, whereClause, whereArgs);

        db.close();

        // Return true if at least one row was affected, indicating a successful update
        return rowsAffected > 0;
    }

    public boolean insertDummyPhase1Order() {
        SQLiteDatabase db = this.getWritableDatabase();
        Random rand = new Random();
        rand.ints(10);
        ContentValues values = new ContentValues();
        values.put(PHASE1_ORDER_ID, rand.nextInt(20));
        values.put(PHASE1_ORDER_CLIENT_ID, rand.nextInt(20));
        values.put(PHASE1_ORDER_WASHER_ID, 1);
        values.put(PHASE1_ORDER_COURIER_ID, rand.nextInt(20));
        values.put(PHASE1_COURIER_STATUS, 0);
        values.put(PHASE1_TOTAL_COURIER_AMOUNT, 50.0);
        values.put(PHASE1_DATE_COURIER, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        values.put(PHASE1_TOTAL_DUE, 0.0);
        values.put(PHASE1_TOTAL_PAID, 0.0);
        values.put(PHASE1_PAYMENT_STATUS, 0);
        values.put(PHASE1_DATE_RECEIVED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        long result = db.insert("PHASE1_ORDER", null, values);

        // Close the database
        db.close();

        // Check if the insertion was successful
        return result != -1;
    }

    public boolean insertDummyPhase2Order() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHASE2_ORDER_ID, 1);
        values.put(PHASE2_ORDER_CLIENT_ID, 1);
        values.put(PHASE2_ORDER_WASHER_ID, 1);
        values.put(PHASE2_ORDER_COURIER_ID, 1);
        values.put(PHASE2_COURIER_STATUS, 0);
        values.put(PHASE2_TOTAL_COURIER_AMOUNT, 50.0);
        values.put(PHASE2_DATE_COURIER, "2023-12-2023");
        values.put(PHASE2_TOTAL_DUE, 0.0);
        values.put(PHASE2_TOTAL_PAID, 0.0);
        values.put(PHASE2_PAYMENT_STATUS, 0);
        values.put(PHASE2_DATE_RECEIVED, "2023-13-2023");

        long result = db.insert("PHASE2COURIERPICKUP_ORDER", null, values);

        // Close the database
        db.close();

        // Check if the insertion was successful
        return result != -1;
    }

    public void setPhase1OrderStatus(int orderID, int phase1OrderStatus) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_ORDER_STATUS = ? WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, phase1OrderStatus);
        stmt.bindLong(2, orderID);

        int rowsAffected = stmt.executeUpdateDelete();

        // Return the number of rows affected
    }

    public boolean setReturnPhase1OrderStatus(int orderID, int phase1OrderStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Use update for update operation
        int rowsAffected = db.update("PHASE1_ORDER",
                getContentValues(phase1OrderStatus),
                "PHASE1_ORDER_ID = ?",
                new String[]{String.valueOf(orderID)});

        db.close();

        // Check if the update was successful
        return rowsAffected > 0;
    }

    // Helper method to create ContentValues
    private ContentValues getContentValues(int phase1OrderStatus) {
        ContentValues values = new ContentValues();
        values.put("PHASE1_ORDER_STATUS", phase1OrderStatus);
        return values;
    }


    public int availableCourier() {
        SQLiteDatabase db = this.getReadableDatabase();

//        Cursor availableCourier = db.rawQuery("SELECT DISTINCT C.COURIER_ID FROM COURIER C JOIN PHASE1_ORDER P ON C.COURIER_ID = P.PHASE1_ORDER_COURIER_ID WHERE P.PHASE1_ORDER_STATUS <> 1 LIMIT 1;", new String[]{});
        // select courier where courier status is available (1)
        Cursor availableCourier = db.rawQuery("SELECT COURIER_ID FROM COURIER WHERE COURIER_STATUS = 1", new String[]{});
        if( availableCourier.moveToFirst() == false){
            return -1;
        }
        String courierid = Integer.toString(availableCourier.getInt(0));

        // Use the correct database object (ddb) consistently
        SQLiteDatabase ddb = this.getWritableDatabase();

        // Use execSQL for UPDATE queries
        ddb.execSQL("UPDATE COURIER SET COURIER_STATUS = 0 WHERE COURIER_ID = ?", new Object[]{courierid});
        Log.e("Courier ID" ,"fuck");
        db.close();
        ddb.close();

        return availableCourier.getInt(0);
    }

    public int washerAcceptClientRequest(int phase1OrderID, int availableCourierID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_ORDER_STATUS = 1 WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, phase1OrderID);

        int rowsAffected = stmt.executeUpdateDelete();

        // Return the number of rows affected
        return rowsAffected;
    }

    public List<Phase1Order> getWasherReceivedClothes(int washerID, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN (4,5,6) ORDER BY PHASE1_ORDER_STATUS;";
        String[] selectionArgs = {String.valueOf(washerID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase1Order> OrderToReceiveList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phase1Order addOrder = new Phase1Order();
                addOrder.setOrderID(cursor.getInt(0));
                addOrder.setClient(this.getClient(cursor.getInt(1)));
                addOrder.setWasher(this.getWasher(cursor.getInt(2)));
                addOrder.setCourier(this.getCourier(cursor.getInt(3)));
                addOrder.setCourierStatus(cursor.getInt(4));
                addOrder.setTotalCourierAmount(cursor.getFloat(5));
                addOrder.setDateCourier(cursor.getString(6));
                addOrder.setTotalDue(cursor.getFloat(7));
                addOrder.setTotalPaid(cursor.getFloat(8));
                addOrder.setPaymentStatus(cursor.getInt(9));
                addOrder.setDateReceived(cursor.getString(10));
                addOrder.setInitialLoad(cursor.getInt(11));
                addOrder.setPhase1OrderStatus(cursor.getInt(12));
                addOrder.setDatePlaced(cursor.getString(13));

                OrderToReceiveList.add(addOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.getReadableDatabase().close();

        return OrderToReceiveList;
    }

    public int washerMarkedClothesAsReceived(int phase1OrderID, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_ORDER_STATUS = 4 WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, phase1OrderID);

        int rowsAffected = stmt.executeUpdateDelete();

        // Return the number of rows affected
        return rowsAffected;
    }

    public int washerUpdatePhase1OrderCourierIDAndCourierDate(int orderID, int availableCourierID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_ORDER_COURIER_ID = ?, PHASE1_DATE_COURIER = ? WHERE PHASE1_ORDER_ID = ?";

        // Get current date to set in the PHASE1_DATE_COURIER
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, availableCourierID);
        stmt.bindString(2, formattedDate);
        stmt.bindLong(3, orderID);

        int rowsAffected = stmt.executeUpdateDelete();

        // Return the number of rows affected
        return rowsAffected;
    }

    public void updatePhase1OrderDateReceivedToCurrentDate(int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_DATE_RECEIVED = ? WHERE PHASE1_ORDER_ID = ?";

        // Get current date to set in the PHASE1_DATE_COURIER
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindString(1, formattedDate);
        stmt.bindLong(2, orderID);

        stmt.execute();
        db.close();
    }

    public void updatePhase2OrderDateReceivedToCurrentDate(int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_DATE_RECEIVED = ? WHERE PHASE2_ORDER_ID = ?";

        // Get current date to set in the PHASE1_DATE_COURIER
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindString(1, formattedDate);
        stmt.bindLong(2, orderID);

        stmt.execute();
        db.close();
    }

    public void updatePhase1OrderTotalDue(int orderID, double totalDue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_TOTAL_DUE = ? WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindDouble(1, totalDue);
        stmt.bindLong(2, orderID);

        stmt.execute();
        db.close();

//        SQLiteDatabase dbb = this.getReadableDatabase();
//        Cursor duevalue = dbb.rawQuery("SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_ID =? ", new String[]{Integer.toString(orderID)});
//        if(duevalue.moveToFirst() ) {
//            int index = duevalue.getColumnIndex(PHASE1_TOTAL_DUE);
//            if (index != -1) {
//                double foq;
//                foq = duevalue.getDouble(index);
//                Log.e("TOTAL DUE WINSON","Value"+totalDue);
//            }
//        }


    }

    public void updatePhase1OrderInitialLoad(int orderID, int initialload) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_INITIAL_LOAD = ? WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindDouble(1, initialload);
        stmt.bindLong(2, orderID);

        stmt.execute();
        db.close();
    }

    @SuppressLint("Range")
    public List<Notification> getWasherNotification(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the Notification table
        Cursor cursor = db.rawQuery("SELECT * FROM NOTIFICATION WHERE NOTIFICATION_WASHER_ID = ? ORDER BY NOTIFICATION_DATETIME DESC", new String[]{Integer.toString(washerID)});

        List<Notification> notifications = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Notification notification = new Notification();
                    notification.setNotificationID(cursor.getInt(cursor.getColumnIndex("NOTIFICATION_ID")));
                    notification.setTitle(cursor.getString(cursor.getColumnIndex("NOTIFICATION_TITLE")));
                    notification.setMessage(cursor.getString(cursor.getColumnIndex("NOTIFICATION_MESSAGE")));
                    notification.setRead(cursor.getInt(cursor.getColumnIndex("NOTIFICATION_IS_READ")) == 1); // Assuming 1 represents true in your database
                    // You may need to adjust the above line based on how your boolean is stored in the database

                    // Populate client, courier, washer objects if needed
                    notification.setClient(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_CLIENT_ID)) != -1 ? this.getClient(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_CLIENT_ID))) : null);
                    notification.setCourier(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_COURIER_ID)) != -1 ? this.getCourier(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_COURIER_ID))) : null);
                    notification.setWasher(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_WASHER_ID)) != -1 ? this.getWasher(cursor.getInt(cursor.getColumnIndex(NOTIFICATION_WASHER_ID))) : null);


                    notification.setDateTime(cursor.getString(cursor.getColumnIndex("NOTIFICATION_DATETIME")));

                    notifications.add(notification);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return notifications;
    }

    public void sendNotifications(int washerID, int customerID, int courierID, String notificationTitle, String notificationMessage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOTIFICATION_TITLE", notificationTitle);
        values.put("NOTIFICATION_MESSAGE", notificationMessage);
        values.put("NOTIFICATION_IS_READ", 0); // Assuming the initial state is unread

        // set the value of ID to -1 if the value is 0

        if(customerID != 0){
            values.put("NOTIFICATION_CLIENT_ID", customerID );
        }
        if(courierID != 0){
            values.put("NOTIFICATION_COURIER_ID", courierID );
        }
        if(washerID != 0){
            values.put("NOTIFICATION_WASHER_ID", washerID );
        }

        // Get the current date and time
        String dateTime = getCurrentDateTime();
        values.put("NOTIFICATION_DATETIME", dateTime);

        // Insert the values into the table
        long newRowId = db.insert("NOTIFICATION", null, values);

        db.close();
    }

    private String getCurrentDateTime() {
        // Get the current date and time in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



    public List<Phase2Order> getWasherPhase2ClohtesToReturn(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        // TODO change the courrier Collect
        String query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_WASHER_ID = ? AND PHASE2_ORDER_STATUS IN (10,11,12,13,14,15) OR PHASE2_ORDER_STATUS IN (20,21) ORDER BY PHASE2_ORDER_STATUS ASC, PHASE2_DATE_COURIER ASC";
        String[] selectionArgs = {String.valueOf(washerID)};
//
        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase2Order> phase2OrderList = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Phase2Order phase2Order = new Phase2Order();
                    // Populate the Phase2Order object with data from the cursor

                    int orderIDIndex = cursor.getColumnIndex("PHASE2_ORDER_ID");
                    int clientIDIndex = cursor.getColumnIndex("PHASE2_ORDER_CLIENT_ID");
                    int washerIDIndex = cursor.getColumnIndex("PHASE2_ORDER_WASHER_ID");
                    int courierIDIndex = cursor.getColumnIndex("PHASE2_ORDER_COURIER_ID");
                    int courierStatusIndex = cursor.getColumnIndex("PHASE2_COURIER_STATUS");
                    int totalCourierAmountIndex = cursor.getColumnIndex("PHASE2_TOTAL_COURIER_AMOUNT");
                    int dateCourierIndex = cursor.getColumnIndex("PHASE2_DATE_COURIER");
                    int totalDueIndex = cursor.getColumnIndex("PHASE2_TOTAL_DUE");
                    int totalPaidIndex = cursor.getColumnIndex("PHASE2_TOTAL_PAID");
                    int paymentStatusIndex = cursor.getColumnIndex("PHASE2_PAYMENT_STATUS");
                    int dateReceivedIndex = cursor.getColumnIndex("PHASE2_DATE_RECEIVED");
                    int orderStatusIndex = cursor.getColumnIndex("PHASE2_ORDER_STATUS");
                    int referenceNoIndex = cursor.getColumnIndex("PHASE2_REFERENCE_NO");
                    int datePlaceOrderIndex = cursor.getColumnIndex("PHASE2_DATE_PLACED");
                    int phase2OrderPhase1OrderIDIndex = cursor.getColumnIndex("PHASE2_PHASE1_ORDER_ID");

                    // Check if the column exists in the cursor before extracting values

                    if(datePlaceOrderIndex != -1){
                        phase2Order.setDatePlaced((cursor.getString(datePlaceOrderIndex)));
                    }
                    if(phase2OrderPhase1OrderIDIndex != -1){
                        phase2Order.setPhase2_phase1OrderID(cursor.getInt(phase2OrderPhase1OrderIDIndex));
                    }
                    if (orderIDIndex != -1) {
                        phase2Order.setOrderID(cursor.getInt(orderIDIndex));
                    }

                    if (clientIDIndex != -1) {
                        phase2Order.setClient(this.getClient(cursor.getInt(clientIDIndex)));
                    }

                    if (washerIDIndex != -1) {
                        phase2Order.setWasher(this.getWasher(cursor.getInt(washerIDIndex)));
                    }

                    if (courierIDIndex != -1) {
                        phase2Order.setCourier(this.getCourier(cursor.getInt(courierIDIndex)));
                    }

                    if (courierStatusIndex != -1) {
                        phase2Order.setCourierStatus(cursor.getInt(courierStatusIndex));
                    }

                    if (totalCourierAmountIndex != -1) {
                        phase2Order.setTotalCourierAmount(cursor.getFloat(totalCourierAmountIndex));
                    }

                    if (dateCourierIndex != -1) {
                        phase2Order.setDateCourier(cursor.getString(dateCourierIndex));
                    }

                    if (totalDueIndex != -1) {
                        phase2Order.setTotalDue(cursor.getFloat(totalDueIndex));
                    }

                    if (totalPaidIndex != -1) {
                        phase2Order.setTotalPaid(cursor.getFloat(totalPaidIndex));
                    }

                    if (paymentStatusIndex != -1) {
                        phase2Order.setPaymentStatus(cursor.getInt(paymentStatusIndex));
                    }

                    if (dateReceivedIndex != -1) {
                        phase2Order.setDateReceived(cursor.getString(dateReceivedIndex));
                    }

                    if (orderStatusIndex != -1) {
                        phase2Order.setPhase2OrderStatus(cursor.getInt(orderStatusIndex));
                    }

                    if (referenceNoIndex != -1) {
                        phase2Order.setReferenceNo(cursor.getString(referenceNoIndex));
                    }

                    phase2OrderList.add(phase2Order);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return phase2OrderList;
    }



    public void updatePhase2OrderCourierID(int orderID, int courierID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_ORDER_COURIER_ID = ? WHERE PHASE2_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, courierID);
        stmt.bindLong(2, orderID);

        stmt.execute();
        db.close();
    }

    public void updatePhase2OrderStatus(int phase2OrderID, int phase2OrderStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_ORDER_STATUS = ? WHERE PHASE2_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, phase2OrderStatus);
        stmt.bindLong(2, phase2OrderID);

        stmt.execute();
        db.close();

    }

    public void updatePhase2OrderDateCourier(int phase2OrderID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_DATE_COURIER = ? WHERE PHASE2_ORDER_ID = ?";

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindString(1, formattedDate); // Use bindString for date values
        stmt.bindLong(2, phase2OrderID);

        stmt.execute();
        db.close();
    }

    public List<Phase2Order> getWasherPhase2ClohtesToReturns(int washerID) {
        List<Phase2Order> test = new ArrayList<Phase2Order>() ;
        for(int i = 0; i < 4; i ++){
            Phase2Order testing = new Phase2Order();
            test.add(testing);
        }
        return test;

    }

    public void updateWasherStatus(int washerID, int washerStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE WASHER SET WASHER_STATUS = ? WHERE WASHER_ID = ?";


        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, washerStatus); // Use bindString for date values
        stmt.bindLong(2, washerID);

        stmt.execute();
        db.close();
    }

    @SuppressLint("Range")
    public int getWasherStatus(int washerID) {
        int washerStatus = -1; // Default value or an appropriate default for your use case

        SQLiteDatabase db = this.getReadableDatabase();

        // Select statement
        String selectQuery = "SELECT WASHER_STATUS FROM WASHER WHERE WASHER_ID = ?";

        // Execute the select statement
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(washerID)});

        if (cursor.moveToFirst()) {
            washerStatus = cursor.getInt(cursor.getColumnIndex("WASHER_STATUS"));
        }

        // Close the cursor and the database
        cursor.close();
        db.close();

        return washerStatus;
    }

    public int updateWasherProfile(int washerID, String shopName, String shopLocation, String shopContact, Double shopRate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("SHOP_NAME", shopName);
        values.put("SHOP_LOCATION", shopLocation);
        values.put("WASHER_CONTACT_NO", shopContact);
        values.put("RATE_PER_KG", shopRate);

        String whereClause = "WASHER_ID = ?";
        String[] whereArgs = {String.valueOf(washerID)};

        int rowsAffected = db.update("WASHER", values, whereClause, whereArgs);

        db.close();

        return rowsAffected;
    }

    public List<Phase2Order> getWasherPhase2OrderHistory(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        // TODO change the history of courrier collect
        String query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_WASHER_ID = ? AND PHASE2_ORDER_STATUS IN (-1,16) OR PHASE2_ORDER_STATUS IN (22) ORDER BY PHASE2_ORDER_STATUS ASC, PHASE2_DATE_COURIER ASC";
        String[] selectionArgs = {String.valueOf(washerID)};
//
        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase2Order> phase2OrderList = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Phase2Order phase2Order = new Phase2Order();
                    // Populate the Phase2Order object with data from the cursor

                    int orderIDIndex = cursor.getColumnIndex("PHASE2_ORDER_ID");
                    int clientIDIndex = cursor.getColumnIndex("PHASE2_ORDER_CLIENT_ID");
                    int washerIDIndex = cursor.getColumnIndex("PHASE2_ORDER_WASHER_ID");
                    int courierIDIndex = cursor.getColumnIndex("PHASE2_ORDER_COURIER_ID");
                    int courierStatusIndex = cursor.getColumnIndex("PHASE2_COURIER_STATUS");
                    int totalCourierAmountIndex = cursor.getColumnIndex("PHASE2_TOTAL_COURIER_AMOUNT");
                    int dateCourierIndex = cursor.getColumnIndex("PHASE2_DATE_COURIER");
                    int totalDueIndex = cursor.getColumnIndex("PHASE2_TOTAL_DUE");
                    int totalPaidIndex = cursor.getColumnIndex("PHASE2_TOTAL_PAID");
                    int paymentStatusIndex = cursor.getColumnIndex("PHASE2_PAYMENT_STATUS");
                    int dateReceivedIndex = cursor.getColumnIndex("PHASE2_DATE_RECEIVED");
                    int orderStatusIndex = cursor.getColumnIndex("PHASE2_ORDER_STATUS");
                    int referenceNoIndex = cursor.getColumnIndex("PHASE2_REFERENCE_NO");
                    int datePlaceOrderIndex = cursor.getColumnIndex("PHASE2_DATE_PLACED");
                    int phase2OrderPhase1OrderIDIndex = cursor.getColumnIndex("PHASE2_PHASE1_ORDER_ID");

                    // Check if the column exists in the cursor before extracting values

                    if(datePlaceOrderIndex != -1){
                        phase2Order.setDatePlaced(cursor.getString(datePlaceOrderIndex));
                    }
                    if(phase2OrderPhase1OrderIDIndex != -1){
                        phase2Order.setPhase2_phase1OrderID(cursor.getInt(phase2OrderPhase1OrderIDIndex));
                    }

                    // Check if the column exists in the cursor before extracting values
                    if (orderIDIndex != -1) {
                        phase2Order.setOrderID(cursor.getInt(orderIDIndex));
                    }

                    if (clientIDIndex != -1) {
                        phase2Order.setClient(this.getClient(cursor.getInt(clientIDIndex)));
                    }

                    if (washerIDIndex != -1) {
                        phase2Order.setWasher(this.getWasher(cursor.getInt(washerIDIndex)));
                    }

                    if (courierIDIndex != -1) {
                        phase2Order.setCourier(this.getCourier(cursor.getInt(courierIDIndex)));
                    }

                    if (courierStatusIndex != -1) {
                        phase2Order.setCourierStatus(cursor.getInt(courierStatusIndex));
                    }

                    if (totalCourierAmountIndex != -1) {
                        phase2Order.setTotalCourierAmount(cursor.getFloat(totalCourierAmountIndex));
                    }

                    if (dateCourierIndex != -1) {
                        phase2Order.setDateCourier(cursor.getString(dateCourierIndex));
                    }

                    if (totalDueIndex != -1) {
                        phase2Order.setTotalDue(cursor.getFloat(totalDueIndex));
                    }

                    if (totalPaidIndex != -1) {
                        phase2Order.setTotalPaid(cursor.getFloat(totalPaidIndex));
                    }

                    if (paymentStatusIndex != -1) {
                        phase2Order.setPaymentStatus(cursor.getInt(paymentStatusIndex));
                    }

                    if (dateReceivedIndex != -1) {
                        phase2Order.setDateReceived(cursor.getString(dateReceivedIndex));
                    }

                    if (orderStatusIndex != -1) {
                        phase2Order.setPhase2OrderStatus(cursor.getInt(orderStatusIndex));
                    }

                    if (referenceNoIndex != -1) {
                        phase2Order.setReferenceNo(cursor.getString(referenceNoIndex));
                    }

                    phase2OrderList.add(phase2Order);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return phase2OrderList;
    }

    public void updatePhase2OrderPaymentStatus(int phase2OrderID, int paymentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_PAYMENT_STATUS = ? WHERE PHASE2_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindLong(1, paymentStatus); // Use bindString for date values
        stmt.bindLong(2, phase2OrderID);

        stmt.execute();
        db.close();

    }

    public void updatePhase2OrderTotalPaid(int phase2OrderID, double totalDue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE2_ORDER SET PHASE2_TOTAL_PAID = ? WHERE PHASE2_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindDouble(1, totalDue); // Use bindString for date values
        stmt.bindLong(2, phase2OrderID);

        stmt.execute();
        db.close();
    }

    public void updatePhase1OrderTotalPaid(int phase2Phase1OrderID, int totalPaid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update statement
        String updateQuery = "UPDATE PHASE1_ORDER SET PHASE1_TOTAL_PAID = ? WHERE PHASE1_ORDER_ID = ?";

        // Execute the update statement and get the number of rows affected
        SQLiteStatement stmt = db.compileStatement(updateQuery);
        stmt.bindDouble(1, totalPaid); // Use bindString for date values
        stmt.bindLong(2, phase2Phase1OrderID);

        stmt.execute();
        db.close();
    }


    public List<Phase1Order> getWasherPhase1StatusGetter(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN (0,1,2,3,4,5,6);";
//        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN (0,1,2,3,4,5,6);";
        String[] selectionArgs = {String.valueOf(washerID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase1Order> OrderToReceiveList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phase1Order addOrder = new Phase1Order();
                addOrder.setOrderID(cursor.getInt(0));
                addOrder.setClient(this.getClient(cursor.getInt(1)));
                addOrder.setWasher(this.getWasher(cursor.getInt(2)));
                addOrder.setCourier(this.getCourier(cursor.getInt(3)));
                addOrder.setCourierStatus(cursor.getInt(4));
                addOrder.setTotalCourierAmount(cursor.getFloat(5));
                addOrder.setDateCourier(cursor.getString(6));
                addOrder.setTotalDue(cursor.getFloat(7));
                addOrder.setTotalPaid(cursor.getFloat(8));
                addOrder.setPaymentStatus(cursor.getInt(9));
                addOrder.setDateReceived(cursor.getString(10));
                addOrder.setInitialLoad(cursor.getInt(11));
                addOrder.setPhase1OrderStatus(cursor.getInt(12));
                addOrder.setDatePlaced(cursor.getString(13));
                Log.e("PHASE1 LISTT",""+cursor.getInt(0));
                OrderToReceiveList.add(addOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return OrderToReceiveList;
    }

    public List<Phase2Order> getWasherPhase2StatusGetter(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        // TODO change the courrier Collect
        String query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_WASHER_ID = ? AND PHASE2_ORDER_STATUS IN (0,10,11,12,13,14,15) OR PHASE2_ORDER_STATUS IN (20,21) ORDER BY PHASE2_ORDER_STATUS ASC, PHASE2_DATE_COURIER ASC";
        String[] selectionArgs = {String.valueOf(washerID)};
//
        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase2Order> phase2OrderList = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Phase2Order phase2Order = new Phase2Order();
                    // Populate the Phase2Order object with data from the cursor

                    int orderIDIndex = cursor.getColumnIndex("PHASE2_ORDER_ID");
                    int clientIDIndex = cursor.getColumnIndex("PHASE2_ORDER_CLIENT_ID");
                    int washerIDIndex = cursor.getColumnIndex("PHASE2_ORDER_WASHER_ID");
                    int courierIDIndex = cursor.getColumnIndex("PHASE2_ORDER_COURIER_ID");
                    int courierStatusIndex = cursor.getColumnIndex("PHASE2_COURIER_STATUS");
                    int totalCourierAmountIndex = cursor.getColumnIndex("PHASE2_TOTAL_COURIER_AMOUNT");
                    int dateCourierIndex = cursor.getColumnIndex("PHASE2_DATE_COURIER");
                    int totalDueIndex = cursor.getColumnIndex("PHASE2_TOTAL_DUE");
                    int totalPaidIndex = cursor.getColumnIndex("PHASE2_TOTAL_PAID");
                    int paymentStatusIndex = cursor.getColumnIndex("PHASE2_PAYMENT_STATUS");
                    int dateReceivedIndex = cursor.getColumnIndex("PHASE2_DATE_RECEIVED");
                    int orderStatusIndex = cursor.getColumnIndex("PHASE2_ORDER_STATUS");
                    int referenceNoIndex = cursor.getColumnIndex("PHASE2_REFERENCE_NO");
                    int datePlaceOrderIndex = cursor.getColumnIndex("PHASE2_DATE_PLACED");
                    int phase2OrderPhase1OrderIDIndex = cursor.getColumnIndex("PHASE2_PHASE1_ORDER_ID");

                    // Check if the column exists in the cursor before extracting values

                    if(datePlaceOrderIndex != -1){
                        phase2Order.setDatePlaced(cursor.getString(datePlaceOrderIndex));
                    }
                    if(phase2OrderPhase1OrderIDIndex != -1){
                        phase2Order.setPhase2_phase1OrderID(cursor.getInt(phase2OrderPhase1OrderIDIndex));
                    }
                    if (orderIDIndex != -1) {
                        phase2Order.setOrderID(cursor.getInt(orderIDIndex));
                    }

                    if (clientIDIndex != -1) {
                        phase2Order.setClient(this.getClient(cursor.getInt(clientIDIndex)));
                    }

                    if (washerIDIndex != -1) {
                        phase2Order.setWasher(this.getWasher(cursor.getInt(washerIDIndex)));
                    }

                    if (courierIDIndex != -1) {
                        phase2Order.setCourier(this.getCourier(cursor.getInt(courierIDIndex)));
                    }

                    if (courierStatusIndex != -1) {
                        phase2Order.setCourierStatus(cursor.getInt(courierStatusIndex));
                    }

                    if (totalCourierAmountIndex != -1) {
                        phase2Order.setTotalCourierAmount(cursor.getFloat(totalCourierAmountIndex));
                    }

                    if (dateCourierIndex != -1) {
                        phase2Order.setDateCourier(cursor.getString(dateCourierIndex));
                    }

                    if (totalDueIndex != -1) {
                        phase2Order.setTotalDue(cursor.getFloat(totalDueIndex));
                    }

                    if (totalPaidIndex != -1) {
                        phase2Order.setTotalPaid(cursor.getFloat(totalPaidIndex));
                    }

                    if (paymentStatusIndex != -1) {
                        phase2Order.setPaymentStatus(cursor.getInt(paymentStatusIndex));
                    }

                    if (dateReceivedIndex != -1) {
                        phase2Order.setDateReceived(cursor.getString(dateReceivedIndex));
                    }

                    if (orderStatusIndex != -1) {
                        phase2Order.setPhase2OrderStatus(cursor.getInt(orderStatusIndex));
                    }

                    if (referenceNoIndex != -1) {
                        phase2Order.setReferenceNo(cursor.getString(referenceNoIndex));
                    }

                    phase2OrderList.add(phase2Order);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return phase2OrderList;
    }

    public int getWasherPhasePendingTransaction(int washerID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM PHASE1_ORDER WHERE PHASE1_ORDER_WASHER_ID = ? AND PHASE1_ORDER_STATUS IN (0,1,2,3,4,5,6) ORDER BY PHASE1_ORDER_STATUS ;";
        String[] selectionArgs = {String.valueOf(washerID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<Phase1Order> OrderToReceiveList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phase1Order addOrder = new Phase1Order();
                addOrder.setOrderID(cursor.getInt(0));
                addOrder.setClient(this.getClient(cursor.getInt(1)));
                addOrder.setWasher(this.getWasher(cursor.getInt(2)));
                addOrder.setCourier(this.getCourier(cursor.getInt(3)));
                addOrder.setCourierStatus(cursor.getInt(4));
                addOrder.setTotalCourierAmount(cursor.getFloat(5));
                addOrder.setDateCourier(cursor.getString(6));
                addOrder.setTotalDue(cursor.getFloat(7));
                addOrder.setTotalPaid(cursor.getFloat(8));
                addOrder.setPaymentStatus(cursor.getInt(9));
                addOrder.setDateReceived(cursor.getString(10));
                addOrder.setInitialLoad(cursor.getInt(11));
                addOrder.setPhase1OrderStatus(cursor.getInt(12));
                addOrder.setDatePlaced(cursor.getString(13));

                OrderToReceiveList.add(addOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        db = this.getReadableDatabase();
        // TODO change the courrier Collect
        query = "SELECT * FROM PHASE2_ORDER WHERE PHASE2_ORDER_WASHER_ID = ? AND PHASE2_ORDER_STATUS IN (0,10,11,12,13,14,15) OR PHASE2_ORDER_STATUS IN (20,21) ORDER BY PHASE2_ORDER_STATUS ASC, PHASE2_DATE_COURIER ASC";
        selectionArgs = new String[]{String.valueOf(washerID)};
//
        cursor = db.rawQuery(query, selectionArgs);
        List<Phase2Order> phase2OrderList = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Phase2Order phase2Order = new Phase2Order();
                    // Populate the Phase2Order object with data from the cursor

                    int orderIDIndex = cursor.getColumnIndex("PHASE2_ORDER_ID");
                    int clientIDIndex = cursor.getColumnIndex("PHASE2_ORDER_CLIENT_ID");
                    int washerIDIndex = cursor.getColumnIndex("PHASE2_ORDER_WASHER_ID");
                    int courierIDIndex = cursor.getColumnIndex("PHASE2_ORDER_COURIER_ID");
                    int courierStatusIndex = cursor.getColumnIndex("PHASE2_COURIER_STATUS");
                    int totalCourierAmountIndex = cursor.getColumnIndex("PHASE2_TOTAL_COURIER_AMOUNT");
                    int dateCourierIndex = cursor.getColumnIndex("PHASE2_DATE_COURIER");
                    int totalDueIndex = cursor.getColumnIndex("PHASE2_TOTAL_DUE");
                    int totalPaidIndex = cursor.getColumnIndex("PHASE2_TOTAL_PAID");
                    int paymentStatusIndex = cursor.getColumnIndex("PHASE2_PAYMENT_STATUS");
                    int dateReceivedIndex = cursor.getColumnIndex("PHASE2_DATE_RECEIVED");
                    int orderStatusIndex = cursor.getColumnIndex("PHASE2_ORDER_STATUS");
                    int referenceNoIndex = cursor.getColumnIndex("PHASE2_REFERENCE_NO");
                    int datePlaceOrderIndex = cursor.getColumnIndex("PHASE2_DATE_PLACED");
                    int phase2OrderPhase1OrderIDIndex = cursor.getColumnIndex("PHASE2_PHASE1_ORDER_ID");

                    // Check if the column exists in the cursor before extracting values

                    if(datePlaceOrderIndex != -1){
                        phase2Order.setDatePlaced(cursor.getString(datePlaceOrderIndex));
                    }
                    if(phase2OrderPhase1OrderIDIndex != -1){
                        phase2Order.setPhase2_phase1OrderID(cursor.getInt(phase2OrderPhase1OrderIDIndex));
                    }
                    if (orderIDIndex != -1) {
                        phase2Order.setOrderID(cursor.getInt(orderIDIndex));
                    }

                    if (clientIDIndex != -1) {
                        phase2Order.setClient(this.getClient(cursor.getInt(clientIDIndex)));
                    }

                    if (washerIDIndex != -1) {
                        phase2Order.setWasher(this.getWasher(cursor.getInt(washerIDIndex)));
                    }

                    if (courierIDIndex != -1) {
                        phase2Order.setCourier(this.getCourier(cursor.getInt(courierIDIndex)));
                    }

                    if (courierStatusIndex != -1) {
                        phase2Order.setCourierStatus(cursor.getInt(courierStatusIndex));
                    }

                    if (totalCourierAmountIndex != -1) {
                        phase2Order.setTotalCourierAmount(cursor.getFloat(totalCourierAmountIndex));
                    }

                    if (dateCourierIndex != -1) {
                        phase2Order.setDateCourier(cursor.getString(dateCourierIndex));
                    }

                    if (totalDueIndex != -1) {
                        phase2Order.setTotalDue(cursor.getFloat(totalDueIndex));
                    }

                    if (totalPaidIndex != -1) {
                        phase2Order.setTotalPaid(cursor.getFloat(totalPaidIndex));
                    }

                    if (paymentStatusIndex != -1) {
                        phase2Order.setPaymentStatus(cursor.getInt(paymentStatusIndex));
                    }

                    if (dateReceivedIndex != -1) {
                        phase2Order.setDateReceived(cursor.getString(dateReceivedIndex));
                    }

                    if (orderStatusIndex != -1) {
                        phase2Order.setPhase2OrderStatus(cursor.getInt(orderStatusIndex));
                    }

                    if (referenceNoIndex != -1) {
                        phase2Order.setReferenceNo(cursor.getString(referenceNoIndex));
                    }

                    phase2OrderList.add(phase2Order);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }


        return OrderToReceiveList.size() +phase2OrderList.size() ;
    }

    @SuppressLint("Range")
    public int getPhase1LaundryWeight(int phase1OrderID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int initialLoad = -1; // Default value if not found

        // Define the columns to be retrieved
        String[] projection = { "PHASE1_INITIAL_LOAD" };

        // Specify the WHERE clause
        String selection = "PHASE1_ORDER_ID = ?";
        String[] selectionArgs = { String.valueOf(phase1OrderID) };

        // Execute the query
        Cursor cursor = db.query("PHASE1_ORDER", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the initial load from the cursor
            initialLoad = cursor.getInt(cursor.getColumnIndex("PHASE1_INITIAL_LOAD"));

            // Close the cursor to avoid resource leaks
            cursor.close();
        }

        // Close the database connection
        db.close();

        return initialLoad;
    }


}
