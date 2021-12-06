package com.curso.eventsregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NamePlace extends AppCompatActivity {

    EditText nameET;
    EditText placeET;
    TextView nameTV;
    TextView placeTV;
    Button addBT;
    String name = "";
    String place = "";
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_place);

        nameET = findViewById(R.id.nameET);
        placeET = findViewById(R.id.placeET);
        nameTV = findViewById(R.id.nameTV);
        placeTV = findViewById(R.id.placeTV);
        addBT = findViewById(R.id.addBT);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");


        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameET.getText().toString();
                place = placeET.getText().toString();

                Toast.makeText(NamePlace.this, "¡Evento Añadido!", Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(NamePlace.this, MapsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("place", place);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                startActivityForResult(intent, 3);
                finish();
            }
        });


    }
}
