package com.eurotech.samik.guesscapital.gameEngine;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eurotech.samik.guesscapital.R;


/**
 * Created by samik on 13.11.2016.
 */

public class StatisticActivity extends AppCompatActivity {
    private TextView result;
    private Button returnGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictic);

        result = (TextView)findViewById(R.id.sa_result);
        returnGame = (Button)findViewById(R.id.sa_return_game);

        Intent intent = getIntent();
        String str = intent.getStringExtra("tAnswer");
        result.setText("Верных ответов: " + str);

        returnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(StatisticActivity.this,GameActivity.class);
                startActivity(intent1);
            }
        });
    }
}
