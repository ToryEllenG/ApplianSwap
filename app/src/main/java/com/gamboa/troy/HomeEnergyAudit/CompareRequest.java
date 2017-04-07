package com.gamboa.troy.HomeEnergyAudit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 4/5/17.
 */

public class CompareRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://54.147.237.12/phpAPI/registerComp.php"; //Replace with API endpoint
    private Map<String, String> params;

    public CompareRequest(String dishValue, String dryerValue, String washerValue, String fridgeValue, String state,  Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("dishValue", dishValue);
        params.put("dryerValue", dryerValue);
        params.put("washerValue", washerValue);
        params.put("fridgeValue", fridgeValue);
        params.put("state", state);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}