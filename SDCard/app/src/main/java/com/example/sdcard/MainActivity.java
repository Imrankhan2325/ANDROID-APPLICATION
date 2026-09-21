package com.example.sdcard;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editTextData;
    Button btnSave;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editTextData = findViewById(R.id.editTextData);
        btnSave = findViewById(R.id.btnSave);
        tvStatus = findViewById(R.id.tvStatus);

        btnSave.setOnClickListener(v -> {

            String data = editTextData.getText().toString();

            if (data.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Please enter some data",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            writeData(data);
        });
    }

    private void writeData(String data) {

        try {

            File folder = getExternalFilesDir(null);

            File file = new File(
                    folder,
                    "MyData.txt"
            );

            FileOutputStream outputStream =
                    new FileOutputStream(file);

            outputStream.write(data.getBytes());

            outputStream.close();

            tvStatus.setText(
                    "Data saved successfully!\n\nFile Path:\n"
                            + file.getAbsolutePath()
            );

            Toast.makeText(
                    this,
                    "Data Written Successfully",
                    Toast.LENGTH_LONG
            ).show();

            editTextData.setText("");

        } catch (Exception e) {

            tvStatus.setText(
                    "Error: " + e.getMessage()
            );

            Toast.makeText(
                    this,
                    "Failed to write data",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
