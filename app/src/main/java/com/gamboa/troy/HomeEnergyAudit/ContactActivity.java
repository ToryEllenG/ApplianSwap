package com.gamboa.troy.HomeEnergyAudit;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class ContactActivity extends AppCompatActivity {

    Toolbar contactToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Contact Us");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //custom Contact toolbar
        contactToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(contactToolBar);
        getSupportActionBar().setTitle("Contact Information");
        contactToolBar.setTitleTextColor(Color.WHITE);

    }
}
