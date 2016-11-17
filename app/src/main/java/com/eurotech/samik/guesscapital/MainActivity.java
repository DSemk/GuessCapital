package com.eurotech.samik.guesscapital;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eurotech.samik.guesscapital.DataBase.BaseDataManager;
import com.eurotech.samik.guesscapital.DataBase.Country;
import com.eurotech.samik.guesscapital.DataBase.Retrofit;
import com.eurotech.samik.guesscapital.gameEngine.GameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    Button startGame;
    Button exit;
    Button aboutAs;
    Button statistic;

    private boolean dataComplete = false;

    /**
     * @param {boolean} dataComplete  - символизирует готовность бд к работе или нет
     */
    private void dbChecker() {
        BaseDataManager dataManager = BaseDataManager.getDataManager(MainActivity.this);
        if (!dataManager.bdCreateOrNo()) {
            getServerData();
        } else dataComplete = true;

    }


    /**
     * @metod dbChecker() - проверяет готовность бд к родоте
     * @metod initialize() - инициализируем все View элементы
     * @metod createButtonListener() - create all button's listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbChecker();
        initialise();
        createButtonListener();
    }

    private void initialise() {
        statistic = (Button) findViewById(R.id.ma_statistic_btn);
        startGame = (Button) findViewById(R.id.ma_start_game_btn);
        aboutAs = (Button) findViewById(R.id.ma_about_btn);
        exit = (Button) findViewById(R.id.ma_exit_btn);

    }


    private void createButtonListener() {
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                if (dataComplete) {
                    startActivity(intent);
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        // // TODO: 13.11.2016
        aboutAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "В разработке", Toast.LENGTH_SHORT).show();
            }
        });
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "В разработке", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
      * Получает данные с сервера, и вызывает
     * @metod createTempList () - заполняет бд данными и передает список данный в Singleton.class
      */
    private void getServerData() {
        Retrofit.getContries(new Callback<List<Country>>() {
            @Override
            public void success(List<Country> countries, Response response) {
                SingletonBD.getInstance().setList(createTempList(countries));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Проверьте ваше интернет соединение!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<HashMap<String, String>> createTempList(List<Country> list) {
        ArrayList<HashMap<String, String>> myList = new ArrayList<>();
        BaseDataManager dataManager = BaseDataManager.getDataManager(MainActivity.this);

        for (Country c : list) {
            HashMap<String, String> countryEqals = new HashMap<>();
            countryEqals.put("name", c.name);
            countryEqals.put("area", c.area);
            countryEqals.put("population", c.population);
            countryEqals.put("capital", c.capital);
            myList.add(countryEqals);
        }
        int count = 0;

        for (int i = 0; i < myList.size(); i++) {
            HashMap<String, String> h = myList.get(i);
            dataManager.insertCountry(
                    h.get("name"),
                    h.get("capital"),
                    h.get("population"),
                    h.get("area"));
            count++;
        }
        Log.d("MA", "dbChecker() - create :" + count);

        dataComplete = true;
        return myList;
    }
}
