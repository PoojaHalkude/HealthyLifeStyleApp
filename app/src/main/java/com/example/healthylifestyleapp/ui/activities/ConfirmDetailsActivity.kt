package com.example.healthylifestyleapp.ui.activities

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.dialog_chose_level.*
import java.util.*

class ConfirmDetailsActivity : AppCompatActivity(), View.OnClickListener {

    internal var height = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_details)
        initListner()

    }

    private fun initListner() {
        LLHeaderAge.setOnClickListener(this)
        LLHeaderHeight.setOnClickListener(this)
        LLHeaderWeight.setOnClickListener(this)
        LLHeaderLevel.setOnClickListener(this)
        AppCompatButtonConfirmDetails.setOnClickListener(this)

    }

    override fun onClick(v: View) {


        when (v.id) {
            R.id.LLHeaderAge -> {

                val dialogAge = Dialog(this)
                dialogAge.setContentView(R.layout.dialog_chose_age)
                dialogAge.show()
                val spinnerAge = dialogAge.findViewById<View>(R.id.spinnerAge) as Spinner
                val age = ArrayList<Int>()
                for (i in 5..80) {
                    age.add(i)
                }
                val spinnerArrayAdapter = ArrayAdapter(
                    this, android.R.layout.simple_spinner_item, age
                )
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerAge.adapter = spinnerArrayAdapter
                spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {

                        val text = spinnerAge.selectedItem.toString()

                        AppCompatTextViewNewAge.text = text

                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
            }
            R.id.LLHeaderLevel -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_chose_level)
                dialog.show()
                val selectedId = RadioGroupLevel.checkedRadioButtonId


                RadioGroupLevel.setOnCheckedChangeListener { group, checkedId ->
                    if (RadioButtonBeginner.isChecked) {
                        //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                        val mytext1 = RadioButtonBeginner.text.toString()
                        AppCompatTextViewNewLevel.text = mytext1

                    } else if (RadioButtonAdvance.isChecked) {
                        //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                        val mytext1 = RadioButtonAdvance.text.toString()
                        AppCompatTextViewNewLevel.text = mytext1

                    } else {
                        //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                        val mytext1 = RadioButtonIntermediate.text.toString()
                        AppCompatTextViewNewLevel.text = mytext1

                    }
                }
            }
            R.id.LLHeaderWeight -> {
                val dialogWeight = Dialog(this)
                dialogWeight.setContentView(R.layout.dialog_chose_weight)
                dialogWeight.show()
                val spinnerWeight = dialogWeight.findViewById<Spinner>(R.id.SpinnerWeight)
                val weight = ArrayList<Int>()
                for (i in 5..100) {
                    weight.add(i)
                }
                val spinnerWeightAdapter = ArrayAdapter(
                    this, android.R.layout.simple_spinner_item, weight
                )
                spinnerWeightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerWeight.adapter = spinnerWeightAdapter
                spinnerWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {

                        val text = spinnerWeight.selectedItem.toString()

                        AppCompatTextViewNewWeight.text = text

                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
            }
            R.id.LLHeaderHeight -> {
                val dialogHeight = Dialog(this)
                dialogHeight.setContentView(R.layout.dialog_chose_height)
                dialogHeight.show()
                val spinnerHeight1: Spinner
                spinnerHeight1 = dialogHeight.findViewById(R.id.SpinnerHeight1)
                // SpinnerHeight2 = dialogHeight.findViewById(R.id.SpinnerHeight2);
                val heightMeter = ArrayList<Int>()
                for (i in 1..10) {
                    heightMeter.add(i)
                }
                val spinnerHeightAdapater1 = ArrayAdapter(
                    this, android.R.layout.simple_spinner_item, heightMeter
                )
                spinnerHeightAdapater1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerHeight1.adapter = spinnerHeightAdapater1
                /*List HeightCM = new ArrayList<Integer>();
                for (int i = 5; i <= 100; i++) {
                    HeightCM.add(Integer.toString(i));
                }*/
                /* ArrayAdapter<Integer> SpinnerHeightAdapater2 = new ArrayAdapter<Integer>(
                        this, android.R.layout.simple_spinner_item, HeightCM);
                SpinnerHeightAdapater2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                SpinnerHeight2.setAdapter(SpinnerHeightAdapater2);
*/
                spinnerHeight1.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {

                            val text = spinnerHeight1.selectedItem.toString()

                            AppCompatTextViewNewHeight.text = text

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            R.id.AppCompatButtonConfirmDetails ->
                //check if input details are filled or not
                if (validate()) {
                    AppCompatButtonConfirmDetails.text = "FINISH"
                    finish()
                    val myIntent = Intent(this, UserProfileActivity::class.java)
                    startActivity(myIntent)
                }
        }/*SpinnerHeight2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String text = SpinnerHeight2.getSelectedItem().toString();

                        AppCompatTextViewNewHeight.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
    }

    private fun validate(): Boolean {

        if (isEmpty(AppCompatTextViewNewAge)) {
            val t = Toast.makeText(this, "You must enter your Age!", Toast.LENGTH_SHORT)
            t.show()
        } else if (isEmpty(AppCompatTextViewNewHeight)) {
            val t = Toast.makeText(this, "You must enter your Height!", Toast.LENGTH_SHORT)
            t.show()
        } else if (isEmpty(AppCompatTextViewNewLevel)) {
            val t = Toast.makeText(this, "You must enter your Level!", Toast.LENGTH_SHORT)
            t.show()
        } else if (isEmpty(AppCompatTextViewNewWeight)) {
            val t = Toast.makeText(this, "You must enter your Weight!", Toast.LENGTH_SHORT)
            t.show()
        }
        return false
    }


    private fun isEmpty(text: AppCompatTextView): Boolean {
        AppCompatButtonConfirmDetails.text = "FINISH"
        AppCompatButtonConfirmDetails.background =
            resources.getDrawable(R.drawable.sign_up_background)
        val myIntent = Intent(this, UserProfileActivity::class.java)
        startActivity(myIntent)
        val str = text.text.toString()
        return TextUtils.isEmpty(str)

    }
}


