package com.example.topquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mNameQuestionText;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private final QuestionBank mQuestionBank = generateQuestionBank();
    private int mRemainingQuestionCount;
    private Question mCurrentQuestion;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String TAG = "Vincent";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";

    private boolean mEnableTouchEvents;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mNameQuestionText = findViewById(R.id.game_activity_textview_question);
        mButton1 = findViewById(R.id.game_activity_button_1);
        mButton2 = findViewById(R.id.game_activity_button_2);
        mButton3 = findViewById(R.id.game_activity_button_3);
        mButton4 = findViewById(R.id.game_activity_button_4);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mCurrentQuestion = mQuestionBank.getQuestion();
        } else {
            mScore = 0;
            mRemainingQuestionCount = 4;
            mCurrentQuestion = mQuestionBank.getNextQuestion();
        }
        displayQuestion(mCurrentQuestion);
        mEnableTouchEvents = true;
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }


    QuestionBank generateQuestionBank(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        Question question4 = new Question(
                "Quel est le résultat de 4+4 ?",
                Arrays.asList(
                        "8",
                        "6",
                        "10",
                        "12"
                ),
                0
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3,question4));
    }

    private void displayQuestion(final Question question) {
        String questionText = question.getmQuestion();
        mNameQuestionText.setText(questionText);
        List<String> choiceList =  question.getmChoiceList();

        mButton1.setText(choiceList.get(0));
        mButton2.setText(choiceList.get(1));
        mButton3.setText(choiceList.get(2));
        mButton4.setText(choiceList.get(3));


    }

    private void endGame(){

        Intent intent = new Intent();
        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
        setResult(RESULT_OK, intent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .create()
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        mEnableTouchEvents = false;
        int index;
        if (v == mButton1) {
            index = 0;
        } else if (v == mButton2) {
            index = 1;
        } else if (v == mButton3) {
            index = 2;
        } else if (v == mButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
        int correctAnswerIndex = mCurrentQuestion.getmAnswerIndex();
        if (index == correctAnswerIndex) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        }else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        mRemainingQuestionCount--;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mRemainingQuestionCount > 0) {
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                } else {
                    //finish();
                    endGame();
                }
                mEnableTouchEvents = true;
            }
        }, 2_000); // LENGTH_SHORT is usually 2 second long

    }
}