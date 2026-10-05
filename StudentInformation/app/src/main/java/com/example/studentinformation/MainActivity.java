package com.example.studentinformation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextDno = findViewById(R.id.editTextDno);
        final EditText editTextDept = findViewById(R.id.editTextDept);
        final EditText editTextContact = findViewById(R.id.editTextContact);
        Button buttonOpenActivity2 = findViewById(R.id.buttonOpenActivity2);
        buttonOpenActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String dno = editTextDno.getText().toString();
                String department = editTextDept.getText().toString();
                String contact = editTextContact.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("NAME", name);
                intent.putExtra("DNO", dno);
                intent.putExtra("DEPARTMENT", department);
                intent.putExtra("CONTACT", contact);
                startActivity(intent);
            }
        });
    }
}