package com.dajdad.coronavirus;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> {
    private ArrayList<CovidCountry> covidCountries;
    private ArrayList<CovidCountry> tmpArrayList;
    private final recycelerViewInterface rcView;

    public CovidCountryAdapter(ArrayList covidCountries, recycelerViewInterface rcView){
        this.covidCountries = new ArrayList<>(covidCountries);
        tmpArrayList = new ArrayList<>(covidCountries);
        this.rcView = rcView;
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country,parent,false);

        return new ViewHolder(view, rcView);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCountryAdapter.ViewHolder holder, int position) {
        CovidCountry covidCountry = covidCountries.get(position);
        holder.country_name.setText(covidCountry.getmCovidCountry());
        startCountAnimation(holder.total_cases, Integer.parseInt(covidCountry.getmCases()));
    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView total_cases, country_name;
        public ViewHolder(@NonNull View itemView, recycelerViewInterface rcView) {
            super(itemView);
            total_cases = itemView.findViewById(R.id.txt_total_cases);
            country_name = itemView.findViewById(R.id.txt_country_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rcView != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            rcView.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    private void startCountAnimation(TextView textView, int count) {
        ValueAnimator animator = ValueAnimator.ofInt(0, count); //0 is min number, 600 is max number
        animator.setDuration(1000); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}
