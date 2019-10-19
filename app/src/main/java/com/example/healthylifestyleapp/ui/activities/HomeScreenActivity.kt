package com.example.healthylifestyleapp.ui.activities

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {

    private var pStatus = 0
    private val handler = Handler()
    private var textViewProgressDrink: TextView? = null
    private var textViewProgressFood: TextView? = null
    private var textViewProgressSleep: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        textViewProgressDrink = findViewById(R.id.textViewProgressDrink)
        textViewProgressFood = findViewById(R.id.textViewProgressFood)
        textViewProgressSleep = findViewById(R.id.textViewProgressSleep)

        Thread(Runnable {
            while (pStatus <= 30) {
                handler.post {
                    progressBarDrink.progress = pStatus
                    textViewProgressDrink!!.text = "$pStatus ltr"
                }
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                pStatus++
            }
            while (pStatus <= 40) {
                handler.post {
                    progressBarFood.progress = pStatus
                    textViewProgressFood!!.text = pStatus.toString() + "" +
                            "" +
                            "g"
                }
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                pStatus++

            }
            while (pStatus <= 9.40) {
                handler.post {
                    progressBarSleep.progress = pStatus
                    textViewProgressSleep!!.text = "$pStatus ltr"
                }
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                pStatus++
            }
        }).start()

    }
}

