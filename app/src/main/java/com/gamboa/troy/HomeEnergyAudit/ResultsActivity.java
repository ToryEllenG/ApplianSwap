package com.gamboa.troy.HomeEnergyAudit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ResultsActivity extends AppCompatActivity {

    TextView powerTV, stateView;
    Toolbar resultTB;
    Spinner applianceChoices;

    //Variables for API call
    TextView results;
    String JsonURL = "http://54.147.237.12:3000/api/Appliances";
    String jsonResponse = "";
    //Define Queue
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //toolbar
        resultTB = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(resultTB);
        getSupportActionBar().setTitle("Comparison Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resultTB.setTitleTextColor(Color.WHITE);

        Bundle extras = getIntent().getExtras();

        final String bGetDish = extras.getString("getDish");
        final String bGetDryer = extras.getString("getDryer");
        final String bGetWasher = extras.getString("getWasher");
        final String bGetFridge = extras.getString("getFridge");
        final String states = extras.getString("states");

        powerTV = (TextView)findViewById(R.id.powerTV);
        //get selected state
        stateView = (TextView)findViewById(R.id.stateTestView);
        stateView.setText(states);

        //spinner logic
        applianceChoices = (Spinner)findViewById(R.id.choicesSP);

        //assign spinner to an adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.result_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        applianceChoices.setAdapter(adapter);

        applianceChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //set power TextView based on spinner. Edit this later to  also include alternative results
                switch (position) {
                    case 0:
                        powerTV.setText(bGetDish);
                        break;
                    case 1:
                        powerTV.setText(bGetDryer);
                        break;
                    case 2:
                        powerTV.setText(bGetWasher);
                        break;
                    case 3:
                        powerTV.setText(bGetFridge);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Create RequestQueue through Volley
        requestQueue = Volley.newRequestQueue(this);
        //Put results in a TextView embedded within a scrollview
        results = (TextView) findViewById(R.id.jsonData);
        //Call JSON Request Method
        fetchHouseData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //set action bar menu from menu layout
        getMenuInflater().inflate(R.menu.results_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle action bar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //logout button
            case R.id.action_logout:
                startActivity(new Intent(ResultsActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);

    }

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
