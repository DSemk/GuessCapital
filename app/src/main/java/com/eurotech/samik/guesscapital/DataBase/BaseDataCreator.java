package com.eurotech.samik.guesscapital.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by samik on 09.11.2016.
 */

public class BaseDataCreator extends SQLiteOpenHelper {
//    public static final String DB_NAME_USER = "UserBD";
    public static final String DB_NAME_COUNTRY = "CountryBD";
//    public static final int DB_USER_VERSION = 1;
    public static final int DB_COUNTRY_VERSION = 1;

    public BaseDataCreator(Context context) {
        super(context, DB_NAME_COUNTRY, null, DB_COUNTRY_VERSION);
    }

    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "User_table";
        public static final String USER_NAME = "user_name";
    }

//    static String SCRIPT_CREATE_USER_TBL_MAIN = " CREATE TABLE " +
//            UserTable.TABLE_NAME + " ( " +
//            UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            UserTable.USER_NAME + " TEXT " + " ); ";

    public static class CountryTable implements BaseColumns {
        public static final String TABLE_NAME = "Country_table";
        public static final String COUNTRY_NAME = "name";
        public static final String COUNTRY_CAPITAL = "capital";
        public static final String COUNTRY_POPULATION = "population";
        public static final String COUNTRY_AREA = "area";
    }

    static String SCRIPT_CREATE_COUNTRY_TBL_MAIN = " CREATE TABLE " +
            CountryTable.TABLE_NAME + " ( " +
            CountryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CountryTable.COUNTRY_NAME + " TEXT," +
            CountryTable.COUNTRY_CAPITAL + " TEXT, " +
            CountryTable.COUNTRY_AREA + " TEXT, " +
            CountryTable.COUNTRY_POPULATION + " TEXT" + " );";

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SCRIPT_CREATE_USER_TBL_MAIN);
        db.execSQL(SCRIPT_CREATE_COUNTRY_TBL_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + CountryTable.TABLE_NAME);
        onCreate(db);
    }
}
