package com.example.topquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.model.User;

public class MainActivity extends AppCompatActivity {
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser = new User();
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_BEST_SCORE = "SHARED_PREF_USER_INFO_BEST_SCORE";
    //SharedPreferences preferences = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            saveBestScoreInPreferenceFile(score);
            displayWelcomeText();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_push_me);
        mPlayButton.setEnabled(false);

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mUser.setmFirstName("Vincent ");
                String firstName = mNameEditText.getText().toString();
                mUser.setmFirstName(firstName);

                saveUserNameInPreferenceFile(firstName);

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                //startActivity(gameActivityIntent);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
        displayWelcomeText();
    }

    void saveUserNameInPreferenceFile(String firstName){
        getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_USER_INFO_NAME, firstName)
                .apply();
    }

    void saveBestScoreInPreferenceFile(int score){
        getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putInt(SHARED_PREF_USER_INFO_BEST_SCORE, score)
                .apply();
    }

    String getUserNameInPreferenceFile(){
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        return firstName;
    }

    int getBestScoreInPreferenceFile(){
        int bestScore = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_BEST_SCORE, -1);
        return bestScore;
    }

    void displayWelcomeText(){
        String welcomeString;
        String firstName = getUserNameInPreferenceFile();
        if (firstName != null){
            int bestScore = getBestScoreInPreferenceFile();
            welcomeString = "Re-bonjour" + firstName + " ! Ton dernier score est de " + bestScore;
            mNameEditText.setText(firstName);
            mNameEditText.setSelection(mNameEditText.getText().length());
        } else {
            welcomeString = "Bienvenue dans Top Quiz, quel est votre prénom ?";
        }
        mGreetingTextView.setText(welcomeString);
    }

}