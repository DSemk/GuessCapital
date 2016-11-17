package com.eurotech.samik.guesscapital.gameEngine;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eurotech.samik.guesscapital.DataBase.BaseDataManager;
import com.eurotech.samik.guesscapital.MainActivity;
import com.eurotech.samik.guesscapital.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by samik on 13.11.2016.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageOne;
    private ImageView imageTwo;
    private ImageView imageThrie;
    private ImageView imageFour;
    private ImageView imageFive;
    private ImageView imageSix;
    private ImageView imageSeven;
    private ImageView imageEit;
    private ImageView imageNine;
    private ImageView imageTen;

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
                    setProgress(true);
                }else {
                    setProgress(false);
                }
                startGame();
                break;
            case TYPE_SECOND:
                if (s.equals(gameList.get(0).get("population"))) {
                    trueAnswers++;
                    setProgress(true);
                }else {
                    setProgress(false);
                }
                startGame();
                break;
            case TYPE_THIRD:
                if (s.equals(gameList.get(0).get("area"))) {
                    trueAnswers++;
                    setProgress(true);
                }else {
                    setProgress(false);
                }
                startGame();
                break;
            case TYPE_FOUR:
                if (s.equals(gameList.get(0).get("name"))) {
                    trueAnswers++;
                    setProgress(true);
                }else {
                    setProgress(false);
                }
                startGame();
                break;
        }

    }

    private void answersCapText() {
        questNumbers.setText("Отвечено на " + allAnswer + " вопросов из " + MAX_ANSWERS);
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
    private void setProgress(boolean answer){
        int imageT = R.drawable.true_image;

        if(answer == false){
           imageT = R.drawable.false_image;
        }
        switch (allAnswer){
            case 1:
                imageOne.setImageResource(imageT);
                break;
            case 2:
                imageTwo.setImageResource(imageT);
                break;
            case 3:
                imageThrie.setImageResource(imageT);
                break;
            case 4:
                imageFour.setImageResource(imageT);
                break;
            case 5:
                imageFive.setImageResource(imageT);
                break;
            case 6:
                imageSix.setImageResource(imageT);
                break;
            case 7:
                imageSeven.setImageResource(imageT);
                break;
            case 8:
                imageEit.setImageResource(imageT);
                break;
            case 9:
                imageNine.setImageResource(imageT);
                break;
            case 10:
                imageTen.setImageResource(imageT);
                break;
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

        imageOne = (ImageView)findViewById(R.id.image_answer_1);
        imageTwo = (ImageView)findViewById(R.id.image_answer_2);
        imageThrie = (ImageView)findViewById(R.id.image_answer_3);
        imageFour = (ImageView)findViewById(R.id.image_answer_4);
        imageFive = (ImageView)findViewById(R.id.image_answer_5);
        imageSix = (ImageView)findViewById(R.id.image_answer_6);
        imageSeven = (ImageView)findViewById(R.id.image_answer_7);
        imageEit = (ImageView)findViewById(R.id.image_answer_8);
        imageNine = (ImageView)findViewById(R.id.image_answer_9);
        imageTen = (ImageView)findViewById(R.id.image_answer_10);
    }
}
