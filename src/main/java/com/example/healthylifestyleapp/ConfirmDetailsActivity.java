
package com.example.healthylifestyleapp;

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class ConfirmDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout LLHeaderAge, LLHeaderHeight, LLHeaderWeight, LLHeaderLevel;
    AppCompatButton AppCompatButtonConfirmDetails;
    RadioGroup RadioGroupLevel;
    RadioButton RadioButtonBeginner, RadioButtonIntermediate, RadioButtonAdvance;
    View view;
    String age="", weight="", height="",level="";
    LinearLayout ll = (LinearLayout) view;
    AppCompatTextView AppCompatTextViewNewAge, AppCompatTextViewNewLevel, AppCompatTextViewNewWeight, AppCompatTextViewNewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);
        iniUI();
        initListner();

    }

    private void initListner() {
        LLHeaderAge.setOnClickListener(this);
        LLHeaderHeight.setOnClickListener(this);
        LLHeaderWeight.setOnClickListener(this);
        LLHeaderLevel.setOnClickListener(this);
        AppCompatButtonConfirmDetails.setOnClickListener(this);

    }

    private void iniUI() {
        LLHeaderAge = findViewById(R.id.LLHeaderAge);
        LLHeaderHeight = findViewById(R.id.LLHeaderHeight);
        LLHeaderWeight = findViewById(R.id.LLHeaderWeight);
        LLHeaderLevel = findViewById(R.id.LLHeaderLevel);
        AppCompatTextViewNewAge = findViewById(R.id.AppCompatTextViewNewAge);
        AppCompatTextViewNewLevel = findViewById(R.id.AppCompatTextViewNewLevel);
        AppCompatButtonConfirmDetails = findViewById(R.id.AppCompatButtonConfirmDetails);
        AppCompatTextViewNewWeight = findViewById(R.id.AppCompatTextViewNewWeight);
        AppCompatTextViewNewHeight = findViewById(R.id.AppCompatTextViewNewHeight);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.LLHeaderAge:

                final Dialog dialogAge = new Dialog(this);
                dialogAge.setContentView(R.layout.dialog_chose_age);
                dialogAge.show();
                final Spinner spinnerAge = (Spinner) dialogAge.findViewById(R.id.spinnerAge);
                List age = new ArrayList<Integer>();
                for (int i = 5; i <= 80; i++) {
                    age.add(Integer.toString(i));
                }
                ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                        this, android.R.layout.simple_spinner_item, age);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerAge.setAdapter(spinnerArrayAdapter);
                spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String text = spinnerAge.getSelectedItem().toString();

                        AppCompatTextViewNewAge.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                break;
            case R.id.LLHeaderLevel:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_chose_level);
                dialog.show();
                RadioGroupLevel = (RadioGroup) dialog.findViewById(R.id.RadioGroupLevel);
                int selectedId = RadioGroupLevel.getCheckedRadioButtonId();

                AppCompatTextViewNewLevel = findViewById(R.id.AppCompatTextViewNewLevel);


                RadioButtonBeginner = dialog.findViewById(R.id.RadioButtonBeginner);
                RadioButtonIntermediate = dialog.findViewById(R.id.RadioButtonIntermediate);
                RadioButtonAdvance = dialog.findViewById(R.id.RadioButtonAdvance);

                RadioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (RadioButtonBeginner.isChecked()) {
                            //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                            String mytext1 = RadioButtonBeginner.getText().toString();
                            AppCompatTextViewNewLevel.setText(mytext1);

                        } else if (RadioButtonAdvance.isChecked()) {
                            //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                            String mytext1 = RadioButtonAdvance.getText().toString();
                            AppCompatTextViewNewLevel.setText(mytext1);

                        } else {
                            //Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();
                            String mytext1 = RadioButtonIntermediate.getText().toString();
                            AppCompatTextViewNewLevel.setText(mytext1);

                        }


                    }
                });

                break;
            case R.id.LLHeaderWeight:
                final Dialog dialogWeight = new Dialog(this);
                dialogWeight.setContentView(R.layout.dialog_chose_weight);
                dialogWeight.show();
                final Spinner spinnerWeight = dialogWeight.findViewById(R.id.SpinnerWeight);
                List weight = new ArrayList<Integer>();
                for (int i = 5; i <= 100; i++) {
                    weight.add(Integer.toString(i));
                }
                ArrayAdapter<Integer> SpinnerWeightAdapater = new ArrayAdapter<Integer>(
                        this, android.R.layout.simple_spinner_item, weight);
                SpinnerWeightAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerWeight.setAdapter(SpinnerWeightAdapater);
                spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String text = spinnerWeight.getSelectedItem().toString();

                        AppCompatTextViewNewWeight.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.LLHeaderHeight:
                final Dialog dialogHeight = new Dialog(this);
                dialogHeight.setContentView(R.layout.dialog_chose_height);
                dialogHeight.show();
                final Spinner SpinnerHeight1, SpinnerHeight2;
                SpinnerHeight1 = dialogHeight.findViewById(R.id.SpinnerHeight1);
               // SpinnerHeight2 = dialogHeight.findViewById(R.id.SpinnerHeight2);
                List HeightMeter = new ArrayList<Integer>();
                for (int i = 1; i <= 10; i++) {
                    HeightMeter.add(Integer.toString(i));
                }
                ArrayAdapter<Integer> SpinnerHeightAdapater1 = new ArrayAdapter<Integer>(
                        this, android.R.layout.simple_spinner_item, HeightMeter);
                SpinnerHeightAdapater1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

               SpinnerHeight1.setAdapter(SpinnerHeightAdapater1);
                /*List HeightCM = new ArrayList<Integer>();
                for (int i = 5; i <= 100; i++) {
                    HeightCM.add(Integer.toString(i));
                }*/
               /* ArrayAdapter<Integer> SpinnerHeightAdapater2 = new ArrayAdapter<Integer>(
                        this, android.R.layout.simple_spinner_item, HeightCM);
                SpinnerHeightAdapater2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                SpinnerHeight2.setAdapter(SpinnerHeightAdapater2);
*/
                SpinnerHeight1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String text = SpinnerHeight1.getSelectedItem().toString();

                        AppCompatTextViewNewHeight.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*SpinnerHeight2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String text = SpinnerHeight2.getSelectedItem().toString();

                        AppCompatTextViewNewHeight.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
                break;

            case R.id.AppCompatButtonConfirmDetails:
                //check if input details are filled or not
                if(validate())
            {
                AppCompatButtonConfirmDetails.setText("FINISH");
                finish();
                Intent myIntent = new Intent(this, UserProfileActivity.class );
                startActivity(myIntent);
            }

break;

        }
    }

    private boolean validate() {

        if (isEmpty(AppCompatTextViewNewAge)) {
            Toast t = Toast.makeText(this, "You must enter your Age!", Toast.LENGTH_SHORT);
            t.show();
        }
        else if (isEmpty(AppCompatTextViewNewHeight))
        {
            Toast t = Toast.makeText(this, "You must enter your Height!", Toast.LENGTH_SHORT);
            t.show();
        }
        else if (isEmpty(AppCompatTextViewNewLevel))
        {
            Toast t = Toast.makeText(this, "You must enter your Level!", Toast.LENGTH_SHORT);
            t.show();
        }  else if (isEmpty(AppCompatTextViewNewWeight))
        {
            Toast t = Toast.makeText(this, "You must enter your Weight!", Toast.LENGTH_SHORT);
            t.show();
        }
        return false;
    }


    boolean isEmpty(AppCompatTextView text) {
        AppCompatButtonConfirmDetails.setText("FINISH");
        AppCompatButtonConfirmDetails.setBackground(getResources().getDrawable(R.drawable.sign_up_background));
        Intent myIntent = new Intent(this, UserProfileActivity.class );
        startActivity(myIntent);
            CharSequence str = text.getText().toString();
            return TextUtils.isEmpty(str);

    }
}


