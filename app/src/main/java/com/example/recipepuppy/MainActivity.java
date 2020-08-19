package com.example.recipepuppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                //Valida que no se introduzca un texto vacio
                //El api retorna un listado aleatorio en dado caso
                if (textSearch.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.empty_query), Toast.LENGTH_LONG).show();
                    return;
                }


                Intent myIntent = new Intent(v.getContext(), ResultsActivity.class);
                myIntent.putExtra("searchString", textSearch);
                startActivity(myIntent);

            }
        });
    }
}