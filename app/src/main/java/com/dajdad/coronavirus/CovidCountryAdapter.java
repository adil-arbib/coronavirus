package com.dajdad.coronavirus;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable {
    private ArrayList<CovidCountry> covidCountries;
    private ArrayList<CovidCountry> tmpArrayList;

    public CovidCountryAdapter(ArrayList covidCountries){
        this.covidCountries = new ArrayList<>(covidCountries);
        tmpArrayList = new ArrayList<>(covidCountries);
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country,parent,false);


        return new ViewHolder(view);
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

    @Override
    public Filter getFilter() {
        return tmpFilter;
    }

    private Filter tmpFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<CovidCountry> filteredList = new ArrayList<>();
            if(charSequence==null || charSequence.length()==0){
                filteredList.addAll(tmpArrayList);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(CovidCountry item : tmpArrayList){
                    if(item.getmCovidCountry().toLowerCase().startsWith(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            covidCountries.clear();
            covidCountries.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total_cases, country_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            total_cases = itemView.findViewById(R.id.txt_total_cases);
            country_name = itemView.findViewById(R.id.txt_country_name);
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
