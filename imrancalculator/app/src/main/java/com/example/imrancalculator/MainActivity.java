package com.example.imrancalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText11;
    EditText editText22;
    EditText editText33;

    Button addButtonn;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editText11 = findViewById(R.id.editText11);
        editText22 = findViewById(R.id.editText22);
        editText33 = findViewById(R.id.editText33);

        addButtonn = findViewById(R.id.addButtonn);
        button1 = findViewById(R.id.button1);

        // ADD
        addButtonn.setOnClickListener(v -> {

            String first = editText11.getText().toString().trim();
            String second = editText22.getText().toString().trim();

            if (first.isEmpty() || second.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Please enter both numbers",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int number1 = Integer.parseInt(first);
            int number2 = Integer.parseInt(second);

            int answer = number1 + number2;

            editText33.setText(String.valueOf(answer));
        });

        // SUBTRACT
        button1.setOnClickListener(v -> {

            String first = editText11.getText().toString().trim();
            String second = editText22.getText().toString().trim();

            if (first.isEmpty() || second.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Please enter both numbers",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int number1 = Integer.parseInt(first);
            int number2 = Integer.parseInt(second);

            int answer = number1 - number2;

            editText33.setText(String.valueOf(answer));
        });
    }
}
