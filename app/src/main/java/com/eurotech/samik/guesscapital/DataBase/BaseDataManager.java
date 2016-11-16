package com.eurotech.samik.guesscapital.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.eurotech.samik.guesscapital.SingletonBD;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samik on 09.11.2016.
 */

public class BaseDataManager {
    private SQLiteDatabase country_dataBase;
    private BaseDataCreator dbCreator;

    private ArrayList<HashMap<String, String>> countryList;

    private static BaseDataManager dataManager;

    private BaseDataManager(Context context) {
        dbCreator = new BaseDataCreator(context);
        if (country_dataBase == null || !country_dataBase.isOpen()) {
            country_dataBase = dbCreator.getWritableDatabase();
        }
    }

    public static BaseDataManager getDataManager(Context context) {
        if (dataManager == null) {
            dataManager = new BaseDataManager(context);
        }
        return dataManager;
    }

    public long insertCountry(String countryName, String countryCapital, String countryPopulation,
                              String countryArea) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BaseDataCreator.CountryTable.COUNTRY_NAME, countryName);
        contentValues.put(BaseDataCreator.CountryTable.COUNTRY_CAPITAL, countryCapital);
        contentValues.put(BaseDataCreator.CountryTable.COUNTRY_POPULATION, countryPopulation);
        contentValues.put(BaseDataCreator.CountryTable.COUNTRY_AREA, countryArea);

        return country_dataBase.insert(BaseDataCreator.CountryTable.TABLE_NAME, null, contentValues);
    }


    public ArrayList<HashMap<String, String>> getCountry() {
        boolean checkValidRandom = true;
        // Будет хранить 4 разных ID стран
        ArrayList<Integer> countryID = new ArrayList<>();
        // Сам список выбраных 4 стран
        ArrayList<HashMap<String, String>> chosenCountry = new ArrayList<>();
        countryList = SingletonBD.getInstance().getList();
        // Выдираем список стран из БД елси это неободимо
        if (countryList.size() == 0) {

            String query = "SELECT " +
                    BaseDataCreator.CountryTable.COUNTRY_NAME + " , " +
                    BaseDataCreator.CountryTable.COUNTRY_CAPITAL + " , " +
                    BaseDataCreator.CountryTable.COUNTRY_POPULATION + " , " +
                    BaseDataCreator.CountryTable.COUNTRY_AREA + " FROM " + BaseDataCreator.CountryTable.TABLE_NAME;

            Cursor cursor = country_dataBase.rawQuery(query, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("name", cursor.getString(0));
                temp.put("capital", cursor.getString(1));
                temp.put("population", cursor.getString(2));
                temp.put("area", cursor.getString(3));

                countryList.add(temp);
                cursor.moveToNext();
            }
            cursor.close();

            if (countryList.size() != 0) {
                Log.d("BDM", "getCountry -> countryList is done");
            } else
                return null;

        }

        /**
         * Получаем 4 случайныйх ID стран
         * Выполняем пока не получим 4 разных ID
         */
        while (checkValidRandom) {

            countryID = getRandom();

            if (countryID != null) {
                if (checkRandom(countryID)) {
                    checkValidRandom = false;
                }
            } else {
                Log.d("BDM", "getCountry - null");
                break;
            }
        }

        for (int i = 0; i < countryID.size(); i++) {
            chosenCountry.add(countryList.get(countryID.get(i)));
        }
        // собственно наши 4 страны
        return chosenCountry;
    }

    private int getCountryCount() {
        return countryList.size();
    }

    // Получаем четыре случайных ID
    private ArrayList<Integer> getRandom() {
        int countryCount = getCountryCount();
        final int count = 4;
        ArrayList<Integer> random = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int temp = (int) (Math.random() * countryCount - 1);
            random.add(temp);
        }
        return random;
    }

    // Проверяем нет ли совпадений между ID
    private boolean checkRandom(ArrayList<Integer> country) {

        for (int i = 0; i < country.size(); i++) {
            for (int j = 0; j < country.size(); j++) {
                if (i != j) {
                    if (country.get(i) == country.get(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean bdCreateOrNo() {
        Cursor cursor = country_dataBase.query(BaseDataCreator.CountryTable.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }
}
