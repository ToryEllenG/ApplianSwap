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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    TextView powerTV, stateView;
    Toolbar resultTB;
    Spinner applianceChoices;

    //Variables for API call
    String JsonDishURL = "http://54.147.237.12/phpAPI/getDishValues.php";
    String JsonDryerURL = "http://54.147.237.12/phpAPI/getDryerValues.php";
    String JsonWasherURL = "http://54.147.237.12/phpAPI/getWasherValues.php";
    String JsonFridgeURL = "http://54.147.237.12/phpAPI/getFridgeValues.php";
    //floats for chart
    float valueOne, valueTwo, valueThree, valueFour, userValue;
    //chart
    PieChart pieChart;
    //Define Queue
    RequestQueue requestQueue;
    Button viewAll;

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

        //Use Bundle to get extra strings from user input from FragmentCompare.java
        Bundle extras = getIntent().getExtras();
        final String bGetDish = extras.getString("getDish");
        final String bGetDryer = extras.getString("getDryer");
        final String bGetWasher = extras.getString("getWasher");
        final String bGetFridge = extras.getString("getFridge");
        String states = extras.getString("states");

        powerTV = (TextView)findViewById(R.id.powerTV);
        //get selected state
        stateView = (TextView)findViewById(R.id.stateTestView);
        stateView.setText(states);

        //spinner logic
        applianceChoices = (Spinner)findViewById(R.id.choicesSP);

        //Create RequestQueue through Volley
        requestQueue = Volley.newRequestQueue(this);

        //PieChart
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);

        //legend
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        //button to view all
        viewAll = (Button)findViewById(R.id.viewAllBT); //temporary button to view all appliances
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll = new Intent(ResultsActivity.this, ApplianceActivity.class);
                startActivity(viewAll);
            }
        });

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
                        //set TextView based on user input from FragmentCompare.java
                        powerTV.setText(bGetDish);
                        //call json request method based on spinner position
                        fetchDishData();
                        break;
                    case 1:
                        powerTV.setText(bGetDryer);
                        fetchDryerData();
                        break;
                    case 2:
                        powerTV.setText(bGetWasher);
                        fetchWasherData();
                        break;
                    case 3:
                        powerTV.setText(bGetFridge);
                        fetchFridgeData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


    //custom methods to call json data based on appliance type
    private void fetchDishData() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonDishURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject HouseData = response.getJSONObject("dishwashers");

                                //get Strings
                                String dish1 = HouseData.getString("dish1");
                                String dish2 = HouseData.getString("dish2");
                                String dish3 = HouseData.getString("dish3");
                                String dish4 = HouseData.getString("dish4");
                                String userInput = getIntent().getExtras().getString("getDish");

                            //Call entry List
                            List<PieEntry> entries = new ArrayList<>();

                            //check if nothing was inputted in edittext
                            if(!userInput.isEmpty()){
                                userValue = Float.valueOf(userInput);
                                entries.add(new PieEntry(userValue, "Your Input"));
                            }
                                valueOne = Float.valueOf(dish1);
                                valueTwo = Float.valueOf(dish2);
                                valueThree = Float.valueOf(dish3);
                                valueFour = Float.valueOf(dish4);

                                entries.add(new PieEntry(valueOne, "Dishwasher 1"));
                                entries.add(new PieEntry(valueTwo, "Dishwasher 2"));
                                entries.add(new PieEntry(valueThree, "Dishwasher 3"));
                                entries.add(new PieEntry(valueFour, "Dishwasher 4"));

                                //data set
                                PieDataSet set = new PieDataSet(entries, "");
                                set.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                set.setValueTextColor(Color.BLACK);

                                PieData data = new PieData(set);
                                data.setValueTextSize(16f);
                                data.setValueTextColor(Color.BLACK);
                                pieChart.setCenterText("Dishwashers Compared by Estimated Yearly kWh use");
                                pieChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
                                pieChart.setData(data);
                                pieChart.notifyDataSetChanged();
                                pieChart.invalidate();


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
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonDryerURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject HouseData = response.getJSONObject("dryers");

                            //get Strings
                            String dryer1 = HouseData.getString("dryer1");
                            String dryer2 = HouseData.getString("dryer2");
                            String dryer3 = HouseData.getString("dryer3");
                            String dryer4 = HouseData.getString("dryer4");
                            String userInput = getIntent().getExtras().getString("getDryer");

                            //check if nothing was inputted in edittext
                            List<PieEntry> entries = new ArrayList<>();
                            if(!userInput.isEmpty()){
                                userValue = Float.valueOf(userInput);
                                entries.add(new PieEntry(userValue, "Your Input"));
                            }

                                //convert to floats for chart
                                valueOne = Float.valueOf(dryer1);
                                valueTwo = Float.valueOf(dryer2);
                                valueThree = Float.valueOf(dryer3);
                                valueFour = Float.valueOf(dryer4);

                                //entries
                                entries.add(new PieEntry(valueOne, "Dryer 1"));
                                entries.add(new PieEntry(valueTwo, "Dryer 2"));
                                entries.add(new PieEntry(valueThree, "Dryer 3"));
                                entries.add(new PieEntry(valueFour, "Dryer 4"));

                                //data set
                                PieDataSet set = new PieDataSet(entries, "");
                                set.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                set.setValueTextColor(Color.BLACK);

                                PieData data = new PieData(set);
                                data.setValueTextSize(16f);
                                data.setValueTextColor(Color.BLACK);
                                pieChart.setCenterText("Dryers Compared by Estimated Yearly kWh use");
                                pieChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
                                pieChart.setData(data);
                                pieChart.notifyDataSetChanged();
                                pieChart.invalidate();


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
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonWasherURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject HouseData = response.getJSONObject("washers");

                            //get Strings
                            String washer1 = HouseData.getString("washer1");
                            String washer2 = HouseData.getString("washer2");
                            String washer3 = HouseData.getString("washer3");
                            String washer4 = HouseData.getString("washer4");
                            String userInput = getIntent().getExtras().getString("getWasher");

                            //check if nothing was inputted in edittext
                            List<PieEntry> entries = new ArrayList<>();

                            if(!userInput.isEmpty()){
                                userValue = Float.valueOf(userInput);
                                entries.add(new PieEntry(userValue, "Your Input"));
                            }
                                //convert to floats for chart
                                valueOne = Float.valueOf(washer1);
                                valueTwo = Float.valueOf(washer2);
                                valueThree = Float.valueOf(washer3);
                                valueFour = Float.valueOf(washer4);

                                //entries
                                entries.add(new PieEntry(valueOne, "Washer 1"));
                                entries.add(new PieEntry(valueTwo, "Washer 2"));
                                entries.add(new PieEntry(valueThree, "Washer 3"));
                                entries.add(new PieEntry(valueFour, "Washer 4"));

                                //data set
                                PieDataSet set = new PieDataSet(entries, "");
                                set.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                set.setValueTextColor(Color.BLACK);

                                PieData data = new PieData(set);
                                data.setValueTextSize(16f);
                                data.setValueTextColor(Color.BLACK);
                                pieChart.setCenterText("Washers Compared by Estimated Yearly kWh use");
                                pieChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
                                pieChart.setData(data);
                                pieChart.notifyDataSetChanged();
                                pieChart.invalidate();


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
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonFridgeURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject HouseData = response.getJSONObject("fridges");

                            //get Strings
                            String fridge1 = HouseData.getString("fridge1");
                            String fridge2 = HouseData.getString("fridge2");
                            String fridge3 = HouseData.getString("fridge3");
                            String fridge4 = HouseData.getString("fridge4");
                            String userInput = getIntent().getExtras().getString("getFridge");

                            //check if nothing was inputted in edittext and call list
                            List<PieEntry> entries = new ArrayList<>();

                            if(!userInput.isEmpty()){
                                userValue = Float.valueOf(userInput);
                                entries.add(new PieEntry(userValue, "Your Input"));
                            }
                                //convert to floats for chart
                                valueOne = Float.valueOf(fridge1);
                                valueTwo = Float.valueOf(fridge2);
                                valueThree = Float.valueOf(fridge3);
                                valueFour = Float.valueOf(fridge4);

                                //entries
                                entries.add(new PieEntry(valueOne, "Fridge 1"));
                                entries.add(new PieEntry(valueTwo, "Fridge 2"));
                                entries.add(new PieEntry(valueThree, "Fridge 3"));
                                entries.add(new PieEntry(valueFour, "Fridge 4"));

                                //data set
                                PieDataSet set = new PieDataSet(entries, "");
                                set.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                set.setValueTextColor(Color.BLACK);

                                PieData data = new PieData(set);
                                data.setValueTextSize(16f);
                                data.setValueTextColor(Color.BLACK);
                                pieChart.setCenterText("Refrigerators Compared by Estimated Yearly kWh use");
                                pieChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
                                pieChart.setData(data);
                                pieChart.notifyDataSetChanged();
                                pieChart.invalidate();

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
