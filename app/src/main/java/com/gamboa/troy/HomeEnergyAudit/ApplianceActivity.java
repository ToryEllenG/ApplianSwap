package com.gamboa.troy.HomeEnergyAudit; //this activity simply parses a returned json and puts it in a scrol view.

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    //Variables for API call
    TextView results;
    String JsonURL = "http://54.147.237.12:3000/api/Appliances";
    String jsonResponse = "";
    //Define Queue
    RequestQueue requestQueue;
    //Toolbar
    Toolbar applianceToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.applianceList));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_data);

        //custom Appliance toolbar
        //applianceToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(applianceToolbar);
       // getSupportActionBar().setTitle("List of Appliances");
       // applianceToolbar.setTitleTextColor(Color.WHITE);

        //Create RequestQueue through Volley
        requestQueue = Volley.newRequestQueue(this);
        //Put results in a TextView embedded within a scrollview
        results = (TextView) findViewById(R.id.jsonData);
        //Call JSON Request Method
        fetchHouseData();
    }

    //initiate JSON Request Method
    private void fetchHouseData() {

        JsonArrayRequest req = new JsonArrayRequest(JsonURL,

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
                                String cost = HouseData.getString("cost");
                                String lifeExpectancy = HouseData.getString("lifeExpectancy");
                                String estimatedYearlyUse = HouseData.getString("estimatedYearlyUse");
                                String estimatedYearlyCost = HouseData.getString("estimatedYearlyCost");
                                String kwhLowUse = HouseData.getString("kwhLowUse");
                                String kwhMedUse = HouseData.getString("kwhMedUse");
                                String kwhHighUse = HouseData.getString("kwhHighUse");

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


}

