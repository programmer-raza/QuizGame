package com.prac.quizgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Mix extends AppCompatActivity {
    TextView questionTextView, answerview;
    EditText answerEditText;
    Button submitButton, backButton; // Add a new button for going back

    String question;
    int num1, num2, num, correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        questionTextView = findViewById(R.id.question);
        answerview = findViewById(R.id.answercheck);
        answerEditText = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        backButton = findViewById(R.id.backButton); // Initialize the back button

        generateQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event for the back button
                navigateToMainActivity();
            }
        });
    }

    private void navigateToMainActivity() {
        // Create an Intent to start the main activity
        Intent intent = new Intent(this, MainActivity.class);

        // Start the activity
        startActivity(intent);

        // Finish the current activity
        finish();
    }

    private void generateQuestion() {
        Random random = new Random();
        num1 = random.nextInt(50); // Change the range as needed
        num2 = random.nextInt(50);
        num = random.nextInt(4);

        // Ensure num2 is not zero for division
        if (num == 3) {
            while (num2 == 0) {
                num2 = random.nextInt(50);
            }
        }

        switch (num) {
            case 0:
                question = num1 + "+" + num2 + " = ?";
                correctAnswer = num1 + num2;
                break;
            case 1:
                question = num1 + "-" + num2 + " = ?";
                correctAnswer = num1 - num2;
                break;
            case 2:
                question = num1 + "*" + num2 + " = ?";
                correctAnswer = num1 * num2;
                break;
            case 3:
                question = num1 + "/" + num2 + " = ?";
                correctAnswer = num1 / num2;
                break;
            default:
                questionTextView.setText("No question");
        }

        questionTextView.setText(question);
    }

    // Rest of the code...


    private void checkAnswer() {
        String userAnswerStr = answerEditText.getText().toString().trim();

        if (!userAnswerStr.isEmpty()) {
            int userAnswer = Integer.parseInt(userAnswerStr);

            if (userAnswer == correctAnswer) {
                answerview.setText("Correct!");
            } else {

                answerview.setText("Wrong! The correct answer is " + correctAnswer);
            }

            // Generate a new question after checking the answer
            generateQuestion();
            // Clear the answer field for the next question
            answerEditText.setText("");
        } else {
            showToast("Please enter an answer.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}