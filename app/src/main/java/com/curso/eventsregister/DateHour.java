package com.curso.eventsregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class DateHour extends AppCompatActivity {


    TextView dateET;
    TextView timeET;
    Button buttonEvent;
    Button buttonDate;
    Button buttonTime;
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_hour);


        dateET = findViewById(R.id.dateET);
        timeET = findViewById(R.id.timeET);
        buttonDate = findViewById(R.id.dateBT);
        buttonEvent = findViewById(R.id.eventBT);
        buttonTime = findViewById(R.id.timeBT);


        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd = new DatePickerDialog(DateHour.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                date = dayOfMonth + "/" + (month + 1) + "/" + (year);
                                dateET.setText(date);
                            }
                        }, year, month, day);
                dpd.show();
            }
        });


        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarhour = Calendar.getInstance();
                int hour = calendarhour.get(Calendar.HOUR_OF_DAY);
                int minute = calendarhour.get(Calendar.MINUTE);


                TimePickerDialog tmd = new TimePickerDialog(DateHour.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time = String.format("%02d:%02d", hourOfDay, minute);
                                timeET.setText(time);

                            }
                        }, hour, minute, false);
                tmd.show();
            }
        });

        buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NamePlace.class);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                startActivityForResult(intent, 2);
                finish();
            }
        });
    }
}
