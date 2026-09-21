package com.example.database;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etRegisterNo, etDepartment;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    TextView tvResult;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etRegisterNo = findViewById(R.id.etRegisterNo);
        etDepartment = findViewById(R.id.etDepartment);

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        tvResult = findViewById(R.id.tvResult);

        databaseHelper = new DatabaseHelper(this);

        // ADD
        btnAdd.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String regno = etRegisterNo.getText().toString();
            String department = etDepartment.getText().toString();

            if (name.isEmpty() || regno.isEmpty() || department.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            boolean result = databaseHelper.addStudent(
                    name,
                    regno,
                    department
            );

            if (result) {
                Toast.makeText(
                        this,
                        "Student Added Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                clearFields();

            } else {
                Toast.makeText(
                        this,
                        "Register Number already exists",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // VIEW
        btnView.setOnClickListener(v -> {

            Cursor cursor = databaseHelper.getStudents();

            StringBuilder data = new StringBuilder();

            if (cursor.getCount() == 0) {

                tvResult.setText("No student records found");

            } else {

                while (cursor.moveToNext()) {

                    data.append("ID: ")
                            .append(cursor.getInt(0))
                            .append("\n");

                    data.append("Name: ")
                            .append(cursor.getString(1))
                            .append("\n");

                    data.append("Register No: ")
                            .append(cursor.getString(2))
                            .append("\n");

                    data.append("Department: ")
                            .append(cursor.getString(3))
                            .append("\n");

                    data.append("----------------------\n");
                }

                tvResult.setText(data.toString());
            }

            cursor.close();
        });

        // UPDATE
        btnUpdate.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String regno = etRegisterNo.getText().toString();
            String department = etDepartment.getText().toString();

            boolean result = databaseHelper.updateStudent(
                    name,
                    regno,
                    department
            );

            if (result) {

                Toast.makeText(
                        this,
                        "Student Updated Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                clearFields();

            } else {

                Toast.makeText(
                        this,
                        "Student Not Found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // DELETE
        btnDelete.setOnClickListener(v -> {

            String regno = etRegisterNo.getText().toString();

            boolean result = databaseHelper.deleteStudent(regno);

            if (result) {

                Toast.makeText(
                        this,
                        "Student Deleted Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                clearFields();

            } else {

                Toast.makeText(
                        this,
                        "Student Not Found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void clearFields() {

        etName.setText("");
        etRegisterNo.setText("");
        etDepartment.setText("");
    }
}
