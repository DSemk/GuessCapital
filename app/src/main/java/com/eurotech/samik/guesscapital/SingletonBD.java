package com.eurotech.samik.guesscapital;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samik on 13.11.2016.
 */
public class SingletonBD {
    private static SingletonBD ourInstance = new SingletonBD();

    public static SingletonBD getInstance() {
        return ourInstance;
    }

    private SingletonBD() {
    }

    private ArrayList<HashMap<String,String>> localCountryList = new ArrayList<>();

    public  ArrayList<HashMap<String,String>> getList(){
        return localCountryList;
    }

    public void setList(ArrayList<HashMap<String,String>> list){
        localCountryList = list;
    }

}
