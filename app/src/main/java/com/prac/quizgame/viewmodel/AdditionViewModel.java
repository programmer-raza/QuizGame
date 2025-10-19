package com.prac.quizgame.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AdditionViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> score = new MutableLiveData<>(0);
    public MutableLiveData<Integer> lifeLine = new MutableLiveData<>(3);
    public MutableLiveData<Integer> highScore = new MutableLiveData<>(0);

    private final SharedPreferences prefs;

    public AdditionViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences("QuizPrefs", Application.MODE_PRIVATE);
        highScore.setValue(prefs.getInt("HighScore_Addition", 0));
    }

    public void increaseScore() {
        score.setValue(score.getValue() + 1);
        updateHighScoreIfNeeded();
    }

    public void decreaseLifeLine() {
        int current = lifeLine.getValue() - 1;
        lifeLine.setValue(Math.max(current, 0));
    }

    private void updateHighScoreIfNeeded() {
        int currentScore = score.getValue();
        int savedHigh = prefs.getInt("HighScore_Addition", 0);
        if (currentScore > savedHigh) {
            prefs.edit().putInt("HighScore_Addition", currentScore).apply();
            highScore.setValue(currentScore);
        }
    }

    public void resetGame() {
        score.setValue(0);
        lifeLine.setValue(3);
    }
}
