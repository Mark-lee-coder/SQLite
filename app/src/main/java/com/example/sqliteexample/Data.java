package com.example.sqliteexample;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Data extends AppCompatActivity {
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        tvData = findViewById(R.id.tvData);

        try {
            ContactsDB contactsDB = new ContactsDB((View.OnClickListener) this);
            contactsDB.open();
            tvData.setText(contactsDB.getData());
            contactsDB.close();
        }

        catch (SQLException exception) {
            Toast.makeText(Data.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}