package com.example.studentinformation;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewDno = findViewById(R.id.textViewDno);
        TextView textViewDept = findViewById(R.id.textViewDept);
        TextView textViewContact = findViewById(R.id.textViewContact);
        String name = getIntent().getStringExtra("NAME");
        String dno = getIntent().getStringExtra("DNO");
        String department = getIntent().getStringExtra("DEPARTMENT");
        String contact = getIntent().getStringExtra("CONTACT");
        textViewName.setText("Name: " + name);
        textViewDno.setText("D.NO: " + dno);
        textViewDept.setText("Department: " + department);
        textViewContact.setText("ContactNO: " + contact);
    }
}