package com.prac.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Division extends AppCompatActivity {

    TextView questionTextView, answerview,Timertext,lifeLine,ScoreTxt;
    Button  backButton;

    int num1, num2, correctAnswer,score =0;
    int TotallifeLine = 5;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 15000;

    Button  option1, option2, option3, option4;
    Random random;
    private boolean isGameOver = false;

    ArrayList<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        ScoreTxt = findViewById(R.id.Score);
        lifeLine = findViewById(R.id.lifeline);
        Timertext = findViewById(R.id.timertxt);
        questionTextView = findViewById(R.id.question);
        answerview = findViewById(R.id.answercheck);
        backButton = findViewById(R.id.backButton); // Initialize the back button


        buttons = new ArrayList<>();

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        buttons.add(option1);
        buttons.add(option2);
        buttons.add(option3);
        buttons.add(option4);
        random = new Random();

        lifeLine.setText("Life Lines: "+TotallifeLine);
        generateQuestion();
        startTimer();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onQuitDialog();
            }
        });
    }
    public void onQuitDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Division.this);
        alertDialog.setTitle("Quit Game")
                .setMessage("Are you Sure you want to Quit ")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (countDownTimer != null) {
                        countDownTimer.cancel(); // Ensure timer is canceled before showing dialog
                    }
                    finish();
                })
                .setNegativeButton("No",(dialog, which) -> {
                })
                .show();
    }
    public void option1AnswerCheck(View view) {
        checkAnswer(option1);
    }
    public void option2AnswerCheck(View view) {
        checkAnswer(option2);
    }
    public void option3AnswerCheck(View view) {
        checkAnswer(option3);
    }
    public void option4AnswerCheck(View view) {
        checkAnswer(option4);
    }

    public void assignCorrectAnswer(String correctAns) {
        int correctIndex = random.nextInt(4);
        buttons.get(correctIndex).setText(correctAns);

        ArrayList<Integer> usedAnswers = new ArrayList<>();
        usedAnswers.add(Integer.parseInt(correctAns));

        for (int i = 0; i < 4; i++) {
            if (i == correctIndex) continue;

            int wrongAnswer;
            do {
                wrongAnswer = correctAnswer + random.nextInt(11) - 5; // ±5 range
            } while (usedAnswers.contains(wrongAnswer));

            usedAnswers.add(wrongAnswer);
            buttons.get(i).setText(String.valueOf(wrongAnswer));
        }
    }


    private void generateQuestion() {
        Random random = new Random();
        num1 = random.nextInt(50); // Change the range as needed

        // Generate num2 until it is not zero
        do {
            num2 = random.nextInt(50);
        } while (num2 == 0);

        correctAnswer = num1 / num2;
        assignCorrectAnswer(Integer.toString(correctAnswer));
        String question = num1 + "/" + num2 + " = ?";
        questionTextView.setText(question);
    }
    private void setButtonsEnabled(boolean enabled) {
        option1.setEnabled(enabled);
        option2.setEnabled(enabled);
        option3.setEnabled(enabled);
        option4.setEnabled(enabled);
    }

    private void checkAnswer(Button optionbutton) {
        if (isGameOver) return;

        int userAnswer = Integer.parseInt(optionbutton.getText().toString());

        if (userAnswer == correctAnswer) {
            answerview.setText("Correct!");
            score++;
            ScoreTxt.setText("Score: "+score);
        } else {
            TotallifeLine--;
            if (TotallifeLine < 0) TotallifeLine = 0; // prevent -1
            lifeLine.setText("Life Lines: " + TotallifeLine);

            answerview.setText("Wrong! The correct answer is " + correctAnswer);

            if (TotallifeLine == 0) {
                countDownTimer.cancel();
                GameOverDilog();
                return;
            }
        }

        generateQuestion();
        resetTimer();
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the current timer
        }
        timeLeftInMillis = 15000;
        startTimer(); // Restart the timer
    }
    private void saveHighScore() {
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        int highScore = prefs.getInt("HighScore", 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HighScore", score);
            editor.apply();
        }
    }

    private int getHighScore() {
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        return prefs.getInt("HighScore", 0);
    }

    public void GameOverDilog() {
        if (isGameOver) return;
        isGameOver = true;
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Ensure timer is canceled before showing dialog
        }
        setButtonsEnabled(false);

        saveHighScore(); // Save high score before showing dialog
        int highScore = getHighScore();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Division.this);
        alertDialog.setTitle("Game Over")
                .setMessage("Your Score: " + score + "\n\nHigh Score: " + highScore)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("Retry", (dialog, which) -> {
                    resetGame();
                })
                .show();
    }
    @Override
    public void onBackPressed() {
      onQuitDialog();

    }
    private void resetGame() {
        // Reset score and life lines
        score = 0;
        TotallifeLine = 5;
        isGameOver = false;
        setButtonsEnabled(true);

        lifeLine.setText("Life Lines: " + TotallifeLine);
        ScoreTxt.setText("Score: " + score);

        // Generate a new question
        generateQuestion();

        // Reset the timer and start again
        resetTimer();
    }
    private void startTimer() {
        countDownTimer =  new CountDownTimer(timeLeftInMillis, 1000) { // 10 seconds, tick every second
            @Override
            public void onTick(long millisUntilFinished) {
                Timertext.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                Timertext.setText("Time's up!");
                GameOverDilog();
            }
        }.start();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel timer when the activity is destroyed
        }
    }
}