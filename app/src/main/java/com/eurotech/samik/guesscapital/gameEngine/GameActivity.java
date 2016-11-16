package com.eurotech.samik.guesscapital.gameEngine;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eurotech.samik.guesscapital.DataBase.BaseDataManager;
import com.eurotech.samik.guesscapital.MainActivity;
import com.eurotech.samik.guesscapital.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samik on 13.11.2016.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button answerOne;
    private Button answerSec;
    private Button answerThird;
    private Button answerFour;
    private TextView questNumbers;
    private TextView question;

    private ArrayList<HashMap<String, String>> gameList;
    private int allAnswer = 0;
    private int trueAnswers = 0;
    private final int MAX_ANSWERS = 10;

    private final String TYPE_ONE = "country_name";
    private final String TYPE_SECOND = "country_capital";
    private final String TYPE_THIRD = "country_population";
    private final String TYPE_FOUR = "country_area";
    private String chosenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialize();
        startGame();
    }

    private void startGame() {
        answersCapText();
        gameList = getGameList();
        if (allAnswer < MAX_ANSWERS) {
            if (gameList.size() != 0) {
                allAnswer++;
                createQuestion();
            } else Log.d("_> GA", "gameList is null");
        } else gameEnd();

    }

    private void gameEnd() {
        allAnswer = 0;
        Intent intent = new Intent(GameActivity.this, StatisticActivity.class);
        intent.putExtra("tAnswer", String.valueOf(trueAnswers));
        startActivity(intent);
    }

    private void createQuestion() {
        setGameType();
    }

    private ArrayList<HashMap<String, String>> getGameList() {
        return BaseDataManager.getDataManager(GameActivity.this).getCountry();
    }

    private void setGameType() {
        int type = (int) (Math.random() * 3);
        switch (type) {
            case 0:
                сountryNameType();
                break;
            case 1:
                countryPopulation();
                break;
            case 2:
                countryArea();
                break;
            case 3:
                countryCapital();
                break;
            default:
                Log.d("GA", "setGameType()");
        }

    }

    private void initialize() {
        answerOne = (Button) findViewById(R.id.ga_first_q_btn);
        answerSec = (Button) findViewById(R.id.ga_second_q_btn);
        answerThird = (Button) findViewById(R.id.ga_third_q_btn);
        answerFour = (Button) findViewById(R.id.ga_four_q_btn);
        questNumbers = (TextView) findViewById(R.id.ga_question_number);
        question = (TextView) findViewById(R.id.ga_question);

        answerFour.setOnClickListener(this);
        answerThird.setOnClickListener(this);
        answerSec.setOnClickListener(this);
        answerOne.setOnClickListener(this);
    }


    public void onClick(View v) {
        if (v == null || !(v instanceof TextView)) {
            return;
        }
        String text;
        switch (v.getId()) {
            case (R.id.ga_first_q_btn):
                text = ((TextView) v).getText().toString();
                checkAnswer(text);
                break;
            case (R.id.ga_second_q_btn):
                text = ((TextView) v).getText().toString();
                checkAnswer(text);
                break;
            case (R.id.ga_third_q_btn):
                text = ((TextView) v).getText().toString();
                checkAnswer(text);
                break;
            case (R.id.ga_four_q_btn):
                text = ((TextView) v).getText().toString();
                checkAnswer(text);
                break;
        }
    }

    private void checkAnswer(String s) {
        switch (chosenType) {
            case TYPE_ONE:
                if (s.equals(gameList.get(0).get("capital"))) {
                    trueAnswers++;
                }
                startGame();
                break;
            case TYPE_SECOND:
                if (s.equals(gameList.get(0).get("population"))) {
                    trueAnswers++;
                }
                startGame();
                break;
            case TYPE_THIRD:
                if (s.equals(gameList.get(0).get("area"))) {
                    trueAnswers++;
                }
                startGame();
                break;
            case TYPE_FOUR:
                if (s.equals(gameList.get(0).get("name"))) {
                    trueAnswers++;
                }
                startGame();
                break;
        }

    }

    private void answersCapText() {
        questNumbers.setText("Вопрос " + allAnswer + " из " + MAX_ANSWERS);
    }

    /**
     * if select TYPE_ONE
     */
    private void сountryNameType() {
        chosenType = TYPE_ONE;

        String trueName = gameList.get(0).get("name");
        String questionStr = "Какая столица страны : " + trueName + " ?";
        question.setText(questionStr);

        answerOne.setText(gameList.get(3).get("capital"));
        answerSec.setText(gameList.get(2).get("capital"));
        answerThird.setText(gameList.get(0).get("capital"));
        answerFour.setText(gameList.get(1).get("capital"));

    }

    /**
     * if select TYPE_SECOND
     */
    private void countryPopulation() {
        chosenType = TYPE_SECOND;

        String trueName = gameList.get(0).get("name");
        String questionStr = "Какое население страны : " + trueName + " в млн. ?";
        question.setText(questionStr);

        answerOne.setText(String.valueOf(gameList.get(0).get("population")));
        answerSec.setText(String.valueOf(gameList.get(2).get("population")));
        answerThird.setText(String.valueOf(gameList.get(3).get("population")));
        answerFour.setText(String.valueOf(gameList.get(1).get("population")));
    }

    /**
     * if select TYPE_THIRD
     */
    private void countryArea() {
        chosenType = TYPE_THIRD;

        String trueName = gameList.get(0).get("name");
        String questionStr = "Какая площадь страны : " + trueName + " в кв.км. ?";
        question.setText(questionStr);

        answerOne.setText(gameList.get(3).get("area"));
        answerSec.setText(gameList.get(2).get("area"));
        answerThird.setText(gameList.get(1).get("area"));
        answerFour.setText(gameList.get(0).get("area"));
    }

    /**
     * if select TYPE_FOUR
     */
    private void countryCapital() {
        chosenType = TYPE_FOUR;

        String trueName = gameList.get(0).get("capital");
        String questionStr = "Какой стране принадлежит столица: " + trueName + " ?";
        question.setText(questionStr);

        answerOne.setText(gameList.get(3).get("name"));
        answerSec.setText(gameList.get(0).get("name"));
        answerThird.setText(gameList.get(2).get("name"));
        answerFour.setText(gameList.get(1).get("name"));
    }
}
