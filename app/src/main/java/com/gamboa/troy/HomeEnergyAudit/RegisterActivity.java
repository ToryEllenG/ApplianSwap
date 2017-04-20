package com.gamboa.troy.HomeEnergyAudit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Troygbv on 2/2/17.
 */

public class RegisterActivity extends AppCompatActivity{

    EditText  ETemail, ETusername, ETpassword, ETconfirm;
    Button btnSignUp;
    Toolbar registerToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.accountRegister));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //custom register toolbar
        registerToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle(R.string.RegisterPls);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerToolbar.setTitleTextColor(Color.WHITE);

        //Text Fields
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETusername = (EditText) findViewById(R.id.ETusername);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        ETconfirm = (EditText) findViewById(R.id.ETconfirm);

        ETemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ETusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ETpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ETconfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        //Button
        btnSignUp = (Button)findViewById(R.id.BTsignup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String username = ETusername.getText().toString();
                    final String password = ETpassword.getText().toString();
                    final String confirm = ETconfirm.getText().toString();
                    final String email = ETemail.getText().toString().trim();

                    //String to check that email is in correct format with Regular expression
                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    //Initiate Listener
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                //Call json "response" as shown in PHP API
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                //Open Login Activity & attempt to verify password and confirm
                                if (success) {
                                    Intent openLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                    Toast registerSuccess = Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG);
                                    registerSuccess.show();
                                    startActivity(openLogin);
                                }
                                //If no connection can be made to db
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Username or Email Already Exists!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    //call RegisterRequest class created in RegisterRequest.java and validate empty fields, email format, and confirm password
                if(email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please Enter your information!", Toast.LENGTH_LONG).show();
                    }

                else if(!email.matches(emailPattern)){
                   Toast.makeText(getApplicationContext()," Please enter Email in the form of 'email@email.com'. ", Toast.LENGTH_LONG).show();
                 }
                else if(!confirm.equals(password)){
                    Toast.makeText(getApplicationContext()," Passwords do not match! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    //if all fields succeed, call new volley queue and register to the database
                    RegisterRequest registerRequest = new RegisterRequest(username, password, email, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


                }

        });

    }


    //method to hide keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ETemail.setText("");
        ETusername.setText("");
        ETpassword.setText("");
        ETconfirm.setText("");


     }
}
