package com.example.healthylifestyleapp;

/*
import android.support.v7.app.AppCompatActivity;
*/
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

public class ConfirmDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout LLHeaderAge,LLHeaderHeight,LLHeaderWeight,LLHeaderLevel;
    AppCompatButton AppCompatButtonConfirmDetails;
    RadioButton RadioButtonBeginner,RadioButtonIntermediate,RadioButtonAdvance;
        View view;
    LinearLayout ll = (LinearLayout) view;

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
    }

    private void iniUI() {
        LLHeaderAge=findViewById(R.id.LLHeaderAge);
        LLHeaderHeight=findViewById(R.id.LLHeaderHeight);
        LLHeaderWeight=findViewById(R.id.LLHeaderWeight);
        LLHeaderLevel=findViewById(R.id.LLHeaderLevel);
        AppCompatButtonConfirmDetails=findViewById(R.id.AppCompatButtonConfirmDetails);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.LLHeaderLevel:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_chose_level);
                dialog.show();
                RadioButtonBeginner = dialog.findViewById(R.id.RadioButtonBeginner);
                RadioButtonIntermediate = dialog.findViewById(R.id.RadioButtonIntermediate);
                RadioButtonAdvance = dialog.findViewById(R.id.RadioButtonAdvance);
                if (RadioButtonBeginner.isChecked()) {
                    Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();

                } else if (RadioButtonIntermediate.isChecked()) {
                    Toast.makeText(this, RadioButtonIntermediate.getText(), Toast.LENGTH_SHORT).show();

                } else
            {
                Toast.makeText(this, RadioButtonAdvance.getText(), Toast.LENGTH_SHORT).show();

            }
                break;
            case R.id.LLHeaderAge:
                final Dialog dialogAge = new Dialog(this);
            dialogAge.setContentView(R.layout.dialog_chose_age);
            dialogAge.show();
                break;
        }
                /*switch(v.getId())
                {
                    case R.id.RadioButtonBeginner:
                        if (checked) {
                            Toast.makeText(this, RadioButtonBeginner.getText(), Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.RadioButtonIntermediate:
                        if (checked) {
                            Toast.makeText(this, RadioButtonIntermediate.getText(), Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.RadioButtonAdvance:
                        if (checked) {
                            Toast.makeText(this, RadioButtonAdvance.getText(), Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.LLHeaderAge:
                        break;

                }*/

        }
    }


