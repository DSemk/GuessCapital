package com.eurotech.samik.guesscapital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.eurotech.samik.guesscapital.DataBase.BaseDataManager;
import com.eurotech.samik.guesscapital.DataBase.Country;
import com.eurotech.samik.guesscapital.DataBase.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by samik on 09.11.2016.
 */

public class LoadingActivity extends AppCompatActivity {
    BaseDataManager baseDataManager;
    Intent intent;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);
        intent = new Intent(LoadingActivity.this, MainActivity.class);
        getServerData();

        subscription = Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(intent);
                    }
                });

//        initializeDB(); TODO
    }

//    private void initializeDB() {
//        intent = new Intent(this, MainActivity.class);
//
//        baseDataManager = BaseDataManager.getDataManager(this);
//        if (baseDataManager.bdCreatOrNo()) {
//            startActivity(intent);
//        } else {
//            fillDB();
//        }
//    }
//
//    private void fillDB() {
//
//        Retrofit.getContries(new Callback<List<Country>>() {
//            @Override
//            public void success(List<Country> countries, Response response) {
//                for (Country c : countries) {
//                    baseDataManager.insertCountry(c.name, c.capital, c.population, c.area);
//                }
//                Log.d("AL", "fillDB() - done;");
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(LoadingActivity.this, "ошибка при получении данных с сервера", Toast.LENGTH_SHORT).show();
//            }
//        });
//        startActivity(intent);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void getServerData() {
        Retrofit.getContries(new Callback<List<Country>>() {
            @Override
            public void success(List<Country> countries, Response response) {
                SingletonBD.getInstance().setList(createTempList(countries));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private ArrayList<HashMap<String, String>> createTempList(List<Country> list) {
        ArrayList<HashMap<String, String>> myList = new ArrayList<>();
        for (Country c : list) {
            HashMap<String,String> countryEqals = new HashMap<>();
            countryEqals.put("name",c.name);
            countryEqals.put("area",c.area);
            countryEqals.put("population",c.population);
            countryEqals.put("capital",c.capital);
            myList.add(countryEqals);
        }
        return myList;
    }
}
