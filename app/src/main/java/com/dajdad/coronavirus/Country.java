package com.dajdad.coronavirus;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Country extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<CovidCountry> covidCountries;
    private View view;
    private static final String TAG = Country.class.getSimpleName();
    private EditText edt_search;
    private ArrayList<CovidCountry> tmpArray;
    private CovidCountryAdapter adapter;


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);
        recyclerView = view.findViewById(R.id.rvCountry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        edt_search = view.findViewById(R.id.search_country);

        getDateFromServer();


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = edt_search.getText().toString();
                if(!input.equals("")){
                    adapter.getFilter().filter(input);
                }
                else{
                    adapter = new CovidCountryAdapter(covidCountries);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void showRecyclerView(ArrayList<CovidCountry> arrayList){
        adapter = new CovidCountryAdapter(arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void getDateFromServer() {
        String url = "https://corona.lmao.ninja/v3/covid-19/countries";
        covidCountries = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            Log.e(TAG, "onResponse " + response);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    covidCountries.add(new CovidCountry(jsonObject.getString("country"), jsonObject.getString("cases")));
                                }
                                showRecyclerView(covidCountries);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"onResponse "+error);
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }


}