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

/**
 * Created by troygbv on 2/24/17.
 */

public class FragmentCompare extends Fragment {

    CheckBox dishwasher, dryer, washer, fridge;
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

        dishwasher = (CheckBox) view.findViewById(R.id.dishwasherCheckBox);
        dryer = (CheckBox) view.findViewById(R.id.dryerCheckBox);
        washer = (CheckBox) view.findViewById(R.id.washerCheckBox);
        fridge = (CheckBox) view.findViewById(R.id.fridgeCheckBox);

        etDishwasher = (EditText) view.findViewById(R.id.dishwasherET);
        etDryer = (EditText) view.findViewById(R.id.dryerET);
        etWasher = (EditText) view.findViewById(R.id.washerET);
        etFridge = (EditText) view.findViewById(R.id.fridgeET);

        //spinner
        stateSpin = (Spinner) view.findViewById(R.id.stateSpinner);

        viewResults = (FloatingActionButton) view.findViewById(R.id.fabCompare);

        //work on checkbox logic here

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
                startActivity(openResults);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        etDishwasher.setText("");
        etDryer.setText("");
        etWasher.setText("");
        etFridge.setText("");


    }

}
