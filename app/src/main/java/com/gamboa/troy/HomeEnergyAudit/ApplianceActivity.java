package com.gamboa.troy.HomeEnergyAudit; //this activity simply parses a returned json and puts it in a scrol view.

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Troygbv on 2/13/17.
 */

public class ApplianceActivity extends AppCompatActivity {

    TextView results;
    String JsonDishURL = "http://54.147.237.12/phpAPI/getDish.php";
    String JsonDryerURL = "http://54.147.237.12/phpAPI/getDryer.php";
    String JsonWasherURL = "http://54.147.237.12/phpAPI/getWasher.php";
    String JsonFridgeURL = "http://54.147.237.12/phpAPI/getFridge.php";
    String jsonResponse = "";
    RequestQueue requestQueue;
    Toolbar applianceToolbar;
    Spinner appChoices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.applianceList));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_data);

        //custom Appliance toolbar
        applianceToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(applianceToolbar);
        getSupportActionBar().setTitle("List of Appliances");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        applianceToolbar.setTitleTextColor(Color.WHITE);

        //Create RequestQueue through Volley
        requestQueue = Volley.newRequestQueue(this);
        //Put results in a TextView embedded within a scrollview
        results = (TextView) findViewById(R.id.jsonData);

        appChoices = (Spinner)findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.result_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appChoices.setAdapter(adapter);

        appChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //set power TextView based on spinner. Edit this later to  also include alternative results
                switch (position) {
                    case 0:
                        //call json request method based on spinner position
                        fetchDishData();
                        break;
                    case 1:
                        fetchDryerData();
                        break;
                    case 2:
                        fetchWasherData();
                        break;
                    case 3:
                        fetchFridgeData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void fetchDishData() {

        JsonArrayRequest req = new JsonArrayRequest(JsonDishURL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            //loop through each response in the json
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject HouseData = (JSONObject) response.get(i);

                                String make = HouseData.getString("make");
                                String model = HouseData.getString("model");
                                String type = HouseData.getString("type");
                                String cost = HouseData.getString("Cost");
                                String lifeExpectancy = HouseData.getString("life_expectancy");
                                String estimatedYearlyUse = HouseData.getString("estimated_yearly_use");
                                String estimatedYearlyCost = HouseData.getString("estimated_yearly_cost");
                                String kwhLowUse = HouseData.getString("kWh_low_use");
                                String kwhMedUse = HouseData.getString("kWh_med_use");
                                String kwhHighUse = HouseData.getString("kWh_high_use");
                                String features = HouseData.getString("Features");
                                String link = HouseData.getString("Link");
                                //add link String when data is added to database

                                //provide spacing and call parsed values
                                jsonResponse += "\n";
                                jsonResponse += "Make: " + make + "\n\n";
                                jsonResponse += "Model: " + model  + "\n\n";
                                jsonResponse += "Type: " + type + "\n\n";
                                jsonResponse += "Cost: " + cost   + "\n\n";
                                jsonResponse += "Life Expectancy: " + lifeExpectancy   + "\n\n";
                                jsonResponse += "Estimated Yearly Use (Kwh): " + estimatedYearlyUse   + "\n\n";
                                jsonResponse += "Estimated Yearly Cost: " + estimatedYearlyCost   + "\n\n";
                                jsonResponse += "KwH Low Use: " + kwhLowUse   + "\n\n";
                                jsonResponse += "KwH Med Use: " + kwhMedUse   + "\n\n";
                                jsonResponse += "KwH High Use: " + kwhHighUse   + "\n\n";
                                jsonResponse += "Additional Features: " + features + "\n\n";
                                jsonResponse += "Link to purchase: " + link + "\n\n";
                                jsonResponse += "----------------------------------------------" + "\n";
                            }
                            // Adds the jsonResponse string to the TextView "results"
                            results.setText(jsonResponse);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");


            }
        }

        );
        requestQueue.add(req);
    }

    private void fetchDryerData() {

        JsonArrayRequest req = new JsonArrayRequest(JsonDryerURL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            //loop through each response in the json
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject HouseData = (JSONObject) response.get(i);

                                String make = HouseData.getString("make");
                                String model = HouseData.getString("model");
                                String type = HouseData.getString("type");
                                String cost = HouseData.getString("Cost");
                                String lifeExpectancy = HouseData.getString("life_expectancy");
                                String estimatedYearlyUse = HouseData.getString("estimated_yearly_use");
                                String estimatedYearlyCost = HouseData.getString("estimated_yearly_cost");
                                String kwhLowUse = HouseData.getString("kWh_low_use");
                                String kwhMedUse = HouseData.getString("kWh_med_use");
                                String kwhHighUse = HouseData.getString("kWh_high_use");
                                String features = HouseData.getString("Features");
                                String link = HouseData.getString("Link");
                                //add link String when data is added to database

                                //provide spacing and call parsed values
                                jsonResponse += "\n";
                                jsonResponse += "Make: " + make + "\n\n";
                                jsonResponse += "Model: " + model  + "\n\n";
                                jsonResponse += "Type: " + type + "\n\n";
                                jsonResponse += "Cost: " + cost   + "\n\n";
                                jsonResponse += "Life Expectancy: " + lifeExpectancy   + "\n\n";
                                jsonResponse += "Estimated Yearly Use (Kwh): " + estimatedYearlyUse   + "\n\n";
                                jsonResponse += "Estimated Yearly Cost: " + estimatedYearlyCost   + "\n\n";
                                jsonResponse += "KwH Low Use: " + kwhLowUse   + "\n\n";
                                jsonResponse += "KwH Med Use: " + kwhMedUse   + "\n\n";
                                jsonResponse += "KwH High Use: " + kwhHighUse   + "\n\n";
                                jsonResponse += "Additional Features: " + features + "\n\n";
                                jsonResponse += "Link to purchase: " + link + "\n\n";
                                jsonResponse += "----------------------------------------------" + "\n";
                            }
                            // Adds the jsonResponse string to the TextView "results"
                            results.setText(jsonResponse);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");


            }
        }

        );
        requestQueue.add(req);
    }

    private void fetchWasherData() {

        JsonArrayRequest req = new JsonArrayRequest(JsonWasherURL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            //loop through each response in the json
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject HouseData = (JSONObject) response.get(i);

                                String make = HouseData.getString("make");
                                String model = HouseData.getString("model");
                                String type = HouseData.getString("type");
                                String cost = HouseData.getString("Cost");
                                String lifeExpectancy = HouseData.getString("life_expectancy");
                                String estimatedYearlyUse = HouseData.getString("estimated_yearly_use");
                                String estimatedYearlyCost = HouseData.getString("estimated_yearly_cost");
                                String kwhLowUse = HouseData.getString("kWh_low_use");
                                String kwhMedUse = HouseData.getString("kWh_med_use");
                                String kwhHighUse = HouseData.getString("kWh_high_use");
                                String features = HouseData.getString("Features");
                                String link = HouseData.getString("Link");
                                //add link String when data is added to database

                                //provide spacing and call parsed values
                                jsonResponse += "\n";
                                jsonResponse += "Make: " + make + "\n\n";
                                jsonResponse += "Model: " + model  + "\n\n";
                                jsonResponse += "Type: " + type + "\n\n";
                                jsonResponse += "Cost: " + cost   + "\n\n";
                                jsonResponse += "Life Expectancy: " + lifeExpectancy   + "\n\n";
                                jsonResponse += "Estimated Yearly Use (Kwh): " + estimatedYearlyUse   + "\n\n";
                                jsonResponse += "Estimated Yearly Cost: " + estimatedYearlyCost   + "\n\n";
                                jsonResponse += "KwH Low Use: " + kwhLowUse   + "\n\n";
                                jsonResponse += "KwH Med Use: " + kwhMedUse   + "\n\n";
                                jsonResponse += "KwH High Use: " + kwhHighUse   + "\n\n";
                                jsonResponse += "Additional Features: " + features + "\n\n";
                                jsonResponse += "Link to purchase: " + link + "\n\n";
                                jsonResponse += "----------------------------------------------" + "\n";
                            }
                            // Adds the jsonResponse string to the TextView "results"
                            results.setText(jsonResponse);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");


            }
        }

        );
        requestQueue.add(req);
    }

    private void fetchFridgeData() {

        JsonArrayRequest req = new JsonArrayRequest(JsonFridgeURL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            //loop through each response in the json
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject HouseData = (JSONObject) response.get(i);

                                String make = HouseData.getString("make");
                                String model = HouseData.getString("model");
                                String type = HouseData.getString("type");
                                String cost = HouseData.getString("Cost");
                                String lifeExpectancy = HouseData.getString("life_expectancy");
                                String estimatedYearlyUse = HouseData.getString("estimated_yearly_use");
                                String estimatedYearlyCost = HouseData.getString("estimated_yearly_cost");
                                String kwhLowUse = HouseData.getString("kWh_low_use");
                                String kwhMedUse = HouseData.getString("kWh_med_use");
                                String kwhHighUse = HouseData.getString("kWh_high_use");
                                String features = HouseData.getString("Features");
                                String link = HouseData.getString("Link");
                                //add link String when data is added to database

                                //provide spacing and call parsed values
                                jsonResponse += "\n";
                                jsonResponse += "Make: " + make + "\n\n";
                                jsonResponse += "Model: " + model  + "\n\n";
                                jsonResponse += "Type: " + type + "\n\n";
                                jsonResponse += "Cost: " + cost   + "\n\n";
                                jsonResponse += "Life Expectancy: " + lifeExpectancy   + "\n\n";
                                jsonResponse += "Estimated Yearly Use (Kwh): " + estimatedYearlyUse   + "\n\n";
                                jsonResponse += "Estimated Yearly Cost: " + estimatedYearlyCost   + "\n\n";
                                jsonResponse += "KwH Low Use: " + kwhLowUse   + "\n\n";
                                jsonResponse += "KwH Med Use: " + kwhMedUse   + "\n\n";
                                jsonResponse += "KwH High Use: " + kwhHighUse   + "\n\n";
                                jsonResponse += "Additional Features: " + features + "\n\n";
                                jsonResponse += "Link to purchase: " + link + "\n\n";
                                jsonResponse += "----------------------------------------------" + "\n";
                            }
                            // Adds the jsonResponse string to the TextView "results"
                            results.setText(jsonResponse);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");


            }
        }

        );
        requestQueue.add(req);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

