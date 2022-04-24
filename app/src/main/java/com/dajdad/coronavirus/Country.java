package com.dajdad.coronavirus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Country extends Fragment implements recycelerViewInterface{
    private RecyclerView recyclerView;
    private ArrayList<CovidCountry> covidCountries;
    private View view;
    private static final String TAG = Country.class.getSimpleName();
    private EditText edt_search;
    private CovidCountryAdapter adapter;
    private ArrayList<CovidCountry> tmpArray;
    private boolean using_array1 = true;


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);
        recyclerView = view.findViewById(R.id.rvCountry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        edt_search = view.findViewById(R.id.search_country);
        tmpArray = new ArrayList<>();

        getDateFromServer();


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = edt_search.getText().toString();
                if(!input.equals("")){
                    searchedCountries();
                    adapter = new CovidCountryAdapter(tmpArray, Country.this);
                    using_array1 = false;
                }
                else{
                    adapter = new CovidCountryAdapter(covidCountries, Country.this);
                    using_array1 = true;
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchedCountries(){
        tmpArray.clear();
        String input = edt_search.getText().toString().toLowerCase().trim();
        for(CovidCountry item : covidCountries){
            if(item.getmCovidCountry().toLowerCase().startsWith(input)){
                tmpArray.add(item);
            }
        }
    }

    private void showRecyclerView(ArrayList<CovidCountry> arrayList){
        adapter = new CovidCountryAdapter(arrayList, this);
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
                                    covidCountries.add(new CovidCountry(jsonObject.getString("country"), jsonObject.getString("cases"),
                                            jsonObject.getString("recovered"), jsonObject.getString("deaths")));
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


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), CountryDetails.class);
        putData(intent,using_array1 ? covidCountries : tmpArray, position);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void putData(Intent intent, ArrayList<CovidCountry> arrayList, int position){
        intent.putExtra("country",arrayList.get(position).getmCovidCountry());
        intent.putExtra("confirmed", arrayList.get(position).getmCases());
        intent.putExtra("recovered", arrayList.get(position).getmRecovered());
        intent.putExtra("deaths", arrayList.get(position).getmDeaths());
    }
}