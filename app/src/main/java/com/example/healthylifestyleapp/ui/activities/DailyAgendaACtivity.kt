package com.example.healthylifestyleapp.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.adapters.SlidingImage_Adapter

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_daily_agenda_activity.*

import java.util.Calendar

class DailyAgendaACtivity : AppCompatActivity(), View.OnClickListener {
    private var dotscount: Int = 0
    private var dots: Array<ImageView>? = null
    internal var datePickerDialog: DatePickerDialog? = null
    internal var year: Int = 0
    internal var month: Int = 0
    internal var dayOfMonth: Int = 0
    internal var calendar: Calendar? = null

    internal var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_agenda_activity)
        initUi()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            val intent = Intent(this@DailyAgendaACtivity, AddNewActivity::class.java)
            startActivity(intent)
        }



        ImageViewHome.setOnClickListener(this)
        ImageViewMenuActivity.setOnClickListener(this)
        ImageViewActivity.setOnClickListener(this)
        ImageViewSettings.setOnClickListener(this)
        addEventButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = "vnd.android.cursor.item/event"

            val cal = Calendar.getInstance()
            val startTime = cal.timeInMillis
            val endTime = cal.timeInMillis + 60 * 60 * 1000

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)

            intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday")
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description")
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House")
            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY")

            startActivity(intent)
        }

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
        Selecteddate.setOnClickListener(this)

        for (i in 0 until dotscount) {

            dots!![i] = ImageView(this)
            dots!![i].setImageDrawable(ContextCompat.getDrawable(applicationContext,
                R.drawable.nonactive_dot
            ))

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            params.setMargins(8, 0, 8, 0)

            SliderDots.addView(dots!![i], params)

        }

        dots!![0].setImageDrawable(ContextCompat.getDrawable(applicationContext,
            R.drawable.active_dot
        ))

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                for (i in 0 until dotscount) {
                    dots!![i].setImageDrawable(ContextCompat.getDrawable(applicationContext,
                        R.drawable.nonactive_dot
                    ))
                }

                dots!![position].setImageDrawable(ContextCompat.getDrawable(applicationContext,
                    R.drawable.active_dot
                ))

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }


    private fun initUi() {

        val viewPagerAdapter =
            SlidingImage_Adapter(this)
        viewPager.adapter = viewPagerAdapter
        dotscount = viewPagerAdapter.count
        dots = emptyArray()

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.ImageViewSettings -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
            }
            R.id.ImageViewHome -> {
                val HomeIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(HomeIntent)
            }
            R.id.ImageViewActivity -> {
                val ActivityIntent = Intent(this, DailyAgendaACtivity::class.java)
                startActivity(ActivityIntent)
            }
            R.id.ImageViewMenuActivity -> {
                val MenuIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(MenuIntent)
            }
        }

    }


    /*private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Selecteddate.setText(sdf.format(calendar.getTime()));

    }*/


}
