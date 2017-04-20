package com.gamboa.troy.HomeEnergyAudit;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * Created by Troygbv on 2/24/17.
 */

public class FragmentSettings extends PreferenceFragmentCompat {

    public FragmentSettings(){
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //variables for settings entities
        Preference openAbout = findPreference("keyAbout");
        Preference openContact = findPreference("keyContact");
        Preference openAppliances = findPreference("openAppliances");
        Preference openUser = findPreference("openUser");
        Preference logout = findPreference("keyLogout");

        //preference intent for about activity
        openAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent openStats = new Intent(getActivity(), AboutActivity.class);
                startActivity(openStats);
                return false;
            }
        });

        //preference intent for Contact Activity
        openContact.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent open = new Intent(getActivity(), ContactActivity.class);
                startActivity(open);
                return false;
            }
        });

        //preference intent for User Activity.
        openUser.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent open = new Intent(getActivity(), ShowUserData.class);
                String userID = getActivity().getIntent().getExtras().getString("userID");
                String username = getActivity().getIntent().getExtras().getString("username");
                String email = getActivity().getIntent().getExtras().getString("email");
                String pass = getActivity().getIntent().getExtras().getString("password");

                Bundle extras = new Bundle();
                extras.putString("userID", userID);
                extras.putString("username", username);
                extras.putString("email", email);
                extras.putString("password", pass);
                open.putExtras(extras);
                startActivity(open);
                return false;
            }
        });

        //preference intent to ViewAppliance Activity. Make new activity and change this later
       openAppliances.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent open = new Intent(getActivity(), ApplianceActivity.class);
                startActivity(open);
                return false;
            }
        });

        //preference intent to logout.
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //Alert Dialog to make sure user wants to log out
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Are you sure you want to logout?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent open = new Intent(getActivity(), LoginActivity.class);
                                Toast logoutSuccess = Toast.makeText(getActivity(), "Logout successful!", Toast.LENGTH_SHORT);
                                logoutSuccess.show();
                                startActivity(open);
                            }
                            });
                        builder.create();
                        builder.show();


                return false;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
