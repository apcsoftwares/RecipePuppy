package com.example.recipepuppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerBtn();
    }

    public void addListenerBtn() {
        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.editInput);
                String textSearch = editText.getText().toString();

                Intent myIntent = new Intent(v.getContext(), ResultsActivity.class);
                myIntent.putExtra("searchString", textSearch);
                startActivity(myIntent);

            }
        });
    }
}