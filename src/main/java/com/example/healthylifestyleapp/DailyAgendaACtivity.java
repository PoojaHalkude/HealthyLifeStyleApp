package com.example.healthylifestyleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DailyAgendaACtivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RelativeLayout rlHeaderDailyAgenda;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    TextView Selecteddate;
    Button addEventButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_agenda_activity);
        viewPager=findViewById(R.id.viewPager);
        sliderDotspanel=findViewById(R.id.SliderDots);
        SlidingImage_Adapter viewPagerAdapter = new SlidingImage_Adapter(this);
        rlHeaderDailyAgenda =findViewById(R.id.rlHeaderDailyAgenda);
        Selecteddate=findViewById(R.id.Selecteddate);
        addEventButton=findViewById(R.id.addEventButton);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        addEventButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  Intent intent = new Intent(Intent.ACTION_INSERT);
                                                  intent.setType("vnd.android.cursor.item/event");

                                                  Calendar cal = Calendar.getInstance();
                                                  long startTime = cal.getTimeInMillis();
                                                  long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

                                                  intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                                                  intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                                                  intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                                                  intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
                                                  intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
                                                  intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
                                                  intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                                                  startActivity(intent);

                                              }
                                          });

       /* rlHeaderDailyAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(DailyAgendaACtivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Selecteddate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                //updateLabel();
            }
        });*/
        Selecteddate.setOnClickListener(this);

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        
    }

    @Override
    public void onClick(View v) {

    }


    /*private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Selecteddate.setText(sdf.format(calendar.getTime()));

    }*/


}