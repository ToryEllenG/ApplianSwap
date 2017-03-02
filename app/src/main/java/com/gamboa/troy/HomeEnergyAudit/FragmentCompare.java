package com.gamboa.troy.HomeEnergyAudit;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by troygbv on 2/24/17.
 */

public class FragmentCompare extends Fragment {
//fix layout
    Button viewStats;

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

        viewStats = (Button) view.findViewById(R.id.viewStatisticsBT);

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openData = new Intent(getActivity(), HouseDataActivity.class);
                startActivity(openData);
            }
        });
        return view;
    }

}
