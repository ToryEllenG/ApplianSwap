package com.gamboa.troy.HomeEnergyAudit;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
                //set Strings for all edit text fields
                final String dishValue = etDishwasher.getText().toString();
                final String dryerValue = etDryer.getText().toString();
                final String washerValue = etWasher.getText().toString();
                final String fridgeValue = etFridge.getText().toString();
                final String state = stateSpin.getSelectedItem().toString();

                //initiate listener

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Call json "response" as shown in PHP API
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //Open results activity
                            if (success) {
                                Intent openResults = new Intent(getActivity(), ResultsActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("getDish", dishValue);
                                extras.putString("getDryer", dryerValue);
                                extras.putString("getWasher", washerValue);
                                extras.putString("getFridge", fridgeValue);
                                extras.putString("states", state);
                                //pass the bundle through the intent
                                openResults.putExtras(extras);
                                startActivity(openResults);

                            }
                            //If no connection can be made to db
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Register Failed!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //check user input and toast to alert
                if(!etDishwasher.isEnabled() && !etDryer.isEnabled() && !etWasher.isEnabled() && !etFridge.isEnabled()) {
                    Toast.makeText(getActivity(), "Please select at least one appliance.", Toast.LENGTH_SHORT).show();

                } else if (dishValue.isEmpty() && dishwasherCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Dishwasher.", Toast.LENGTH_SHORT).show();

                } else if (dryerValue.isEmpty() && dryerCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Dryer.", Toast.LENGTH_SHORT).show();

                } else if (washerValue.isEmpty() && washerCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Washer.", Toast.LENGTH_SHORT).show();

                } else if (fridgeValue.isEmpty() && fridgeCheck.isChecked()) {
                    Toast.makeText(getActivity(), "Please enter a valid value for Refrigerator.", Toast.LENGTH_SHORT).show();
                }
                else if (state.matches("")) {
                    Toast.makeText(getActivity(), "Please select a state!", Toast.LENGTH_SHORT).show();

                } else {
                    CompareRequest compareRequest = new CompareRequest(dishValue, dryerValue, washerValue, fridgeValue,state, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(compareRequest);
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
