package com.gamboa.troy.HomeEnergyAudit;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by troygbv on 2/24/17.
 */

public class FragmentCompare extends Fragment {

    CheckBox dishwasherCheck, dryerCheck, washerCheck, fridgeCheck;
    EditText etDishwasher, etDryer, etWasher, etFridge;
    FloatingActionButton viewResults;
    Spinner stateSpin;

    public FragmentCompare(){
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare, container, false);

        dishwasherCheck = (CheckBox) view.findViewById(R.id.dishwasherCheckBox);
        dryerCheck = (CheckBox) view.findViewById(R.id.dryerCheckBox);
        washerCheck = (CheckBox) view.findViewById(R.id.washerCheckBox);
        fridgeCheck = (CheckBox) view.findViewById(R.id.fridgeCheckBox);

        etDishwasher = (EditText) view.findViewById(R.id.dishwasherET);
        etDryer = (EditText) view.findViewById(R.id.dryerET);
        etWasher = (EditText) view.findViewById(R.id.washerET);
        etFridge = (EditText) view.findViewById(R.id.fridgeET);

        //set default values for edit texts to be disabled
        etDishwasher.setEnabled(false);
        etDryer.setEnabled(false);
        etWasher.setEnabled(false);
        etFridge.setEnabled(false);

        //spinner and view button
        stateSpin = (Spinner) view.findViewById(R.id.stateSpinner);
        viewResults = (FloatingActionButton) view.findViewById(R.id.fabCompare);


        //Checkbox enabled logic
        dishwasherCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dishwasherCheck.isChecked()){
                    etDishwasher.setEnabled(true);
                }else etDishwasher.setEnabled(false);
            }
        });
        dryerCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dryerCheck.isChecked()){
                    etDryer.setEnabled(true);
                }else etDryer.setEnabled(false);
            }
        });
        washerCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(washerCheck.isChecked()){
                    etWasher.setEnabled(true);
                }else etWasher.setEnabled(false);
            }
        });
        fridgeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(fridgeCheck.isChecked()){
                    etFridge.setEnabled(true);
                }else etFridge.setEnabled(false);
            }
        });

        //button Click
        viewResults.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent openResults = new Intent(getActivity(), ResultsActivity.class);
                //set Strings for all edit text fields
                final String getDish = etDishwasher.getText().toString();
                final String getDryer = etDryer.getText().toString();
                final String getWasher = etWasher.getText().toString();
                final String getFridge = etFridge.getText().toString();
                final String stateStr = stateSpin.getSelectedItem().toString();
                //create bundle to store all strings inputted in editText fields
                Bundle extras = new Bundle();
                extras.putString("getDish", getDish);
                extras.putString("getDryer", getDryer);
                extras.putString("getWasher", getWasher);
                extras.putString("getFridge", getFridge);
                extras.putString("states", stateStr);
                //pass the bundle through the intent
                openResults.putExtras(extras);

                //check user input and toast to alert
                if(!etDishwasher.isEnabled() && !etDryer.isEnabled() && !etWasher.isEnabled() && !etFridge.isEnabled()) {
                    Toast.makeText(getActivity(), "Please select at least one appliance.", Toast.LENGTH_SHORT).show();

                } else if (getDish.isEmpty() && dishwasherCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Dishwasher.", Toast.LENGTH_SHORT).show();

                } else if (getDryer.isEmpty() && dryerCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Dryer.", Toast.LENGTH_SHORT).show();

                } else if (getWasher.isEmpty() && washerCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Washer.", Toast.LENGTH_SHORT).show();

                } else if (getFridge.isEmpty() && fridgeCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Refrigerator.", Toast.LENGTH_SHORT).show();
                }
                else if (stateStr.matches("")) {
                    Toast.makeText(getActivity(), "Please select a state!", Toast.LENGTH_SHORT).show();

                } else { //start activity if selected fields are valid
                    startActivity(openResults);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //set text fields to empty when fragment is started again
        etDishwasher.setText("");
        etDryer.setText("");
        etWasher.setText("");
        etFridge.setText("");
        //set checkboxes to empty
        dishwasherCheck.setChecked(false);
        dryerCheck.setChecked(false);
        washerCheck.setChecked(false);
        fridgeCheck.setChecked(false);
        //state spinner stays on previously selected, as a user will probably be in the same state to make comparisons

    }

}
