package com.eurotech.samik.guesscapital;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eurotech.samik.guesscapital.gameEngine.GameActivity;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    Button startGame;
    Button exit;
    Button aboutAs;
    Button statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        createButtonListener();
    }

    private void initialise() {
        statistic = (Button)findViewById(R.id.ma_statistic_btn);
        startGame = (Button)findViewById(R.id.ma_start_game_btn);
        aboutAs = (Button)findViewById(R.id.ma_about_btn);
        exit = (Button)findViewById(R.id.ma_exit_btn);

    }


    private void createButtonListener() {
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
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
                Toast.makeText(getApplicationContext(),"В разработке",Toast.LENGTH_SHORT).show();
            }
        });
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"В разработке",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void finishApp(){
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
