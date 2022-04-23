package com.dajdad.coronavirus;

import static com.google.android.material.resources.MaterialResources.getDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;


public class Home extends Fragment {
    private View view;
    private TextView txt_confirmed;
    private TextView txt_recovered;
    private TextView txt_deaths;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        txt_confirmed = view.findViewById(R.id.txt_confirmed);
        txt_recovered = view.findViewById(R.id.txt_recovered);
        txt_deaths = view.findViewById(R.id.txt_deaths);

        getData();



        return view;
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://corona.lmao.ninja/v3/covid-19/all";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String cases = jsonObject.getString("cases");
                            String recovered = jsonObject.getString("recovered");
                            String deaths = jsonObject.getString("deaths");
                            startCountAnimation(txt_confirmed, Integer.parseInt(cases));
                            startCountAnimation(txt_recovered, Integer.parseInt(recovered));
                            startCountAnimation(txt_deaths, Integer.parseInt(deaths));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.drawable.cd_bg);
                dialog.setTitle("Error");
                dialog.setCancelable(false);
                dialog.show();

            }
        });
        requestQueue.add(stringRequest);
    }



    private void startCountAnimation(TextView textView, int count) {
        ValueAnimator animator = ValueAnimator.ofInt(0, count); //0 is min number, 600 is max number
        animator.setDuration(1500); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }


}