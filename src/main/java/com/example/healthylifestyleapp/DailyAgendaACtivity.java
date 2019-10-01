package com.example.healthylifestyleapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class DailyAgendaACtivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RelativeLayout rlHeaderDailyAgenda;
    FloatingActionButton fab;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    TextView Selecteddate;
    Button addEventButton;
    Context context=this;
    ImageView ImageViewMenuActivity,ImageViewHome,ImageViewActivity,ImageViewSettings;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_agenda_activity);
        initUi();

      fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(DailyAgendaACtivity.this, AddNewActivity.class);
                startActivity(intent);
            }
        });



        ImageViewHome.setOnClickListener(this);
        ImageViewMenuActivity.setOnClickListener(this);
        ImageViewActivity.setOnClickListener(this);
        ImageViewSettings.setOnClickListener(this);
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



    private void initUi() {
        fab = findViewById(R.id.fab);

        viewPager=findViewById(R.id.viewPager);
        sliderDotspanel=findViewById(R.id.SliderDots);
        SlidingImage_Adapter viewPagerAdapter = new SlidingImage_Adapter(this);
        rlHeaderDailyAgenda =findViewById(R.id.rlHeaderDailyAgenda);
        Selecteddate=findViewById(R.id.Selecteddate);
        addEventButton=findViewById(R.id.addEventButton);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        ImageViewHome=findViewById(R.id.ImageViewHome);
        ImageViewActivity=findViewById(R.id.ImageViewActivity);
        ImageViewSettings=findViewById(R.id.ImageViewSettings);
        ImageViewMenuActivity=findViewById(R.id.ImageViewMenuActivity);

    }

    @Override
    public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.ImageViewSettings:
                    Intent settingIntent= new Intent(this, SettingsActivity.class);
                    startActivity(settingIntent);
                    break;
                case R.id.ImageViewHome:
                    Intent HomeIntent= new Intent(this, UserProfileActivity.class);
                    startActivity(HomeIntent);
                    break;
                case R.id.ImageViewActivity:
                    Intent ActivityIntent= new Intent(this, DailyAgendaACtivity.class);
                    startActivity(ActivityIntent);
                    break;
                case R.id.ImageViewMenuActivity:
                Intent MenuIntent= new Intent (this,UserProfileActivity.class);
                startActivity(MenuIntent);
                break;
            }

    }


    /*private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Selecteddate.setText(sdf.format(calendar.getTime()));

    }*/


}
