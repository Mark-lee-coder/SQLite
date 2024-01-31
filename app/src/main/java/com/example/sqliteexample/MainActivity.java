package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etName, etPhone;
    Button btnSubmit, btnShowData, btnEditData, btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etPhone = findViewById(R.id.editTextPhone);

        btnSubmit = findViewById(R.id.buttonSubmit);
        btnShowData = findViewById(R.id.buttonShowData);
        btnEditData = findViewById(R.id.buttonEditData);
        btnDeleteData = findViewById(R.id.buttonDeleteData);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                try {
                    ContactsDB contactsDB = new ContactsDB(this);
                    contactsDB.open();
                    contactsDB.createEntry(name, phone);
                    contactsDB.close();

                    Toast.makeText(MainActivity.this, "Successfully saved!!", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etPhone.setText("");
                }

                catch (SQLException exception) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Data.class);
                startActivity(intent);
            }
        });

        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ContactsDB contactsDB = new ContactsDB(this);
                    contactsDB.open();
                    contactsDB.updateEntry("1", "Leon Maina", "254720107257");
                    contactsDB.close();

                    Toast.makeText(MainActivity.this, "Successfully updated!!", Toast.LENGTH_SHORT).show();
                }

                catch (SQLException exception) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ContactsDB contactsDB = new ContactsDB(this);
                    contactsDB.open();
                    contactsDB.deleteEntry("1");
                    contactsDB.close();

                    Toast.makeText(MainActivity.this, "Successfully deleted!!", Toast.LENGTH_SHORT).show();
                }

                catch (SQLException exception) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}