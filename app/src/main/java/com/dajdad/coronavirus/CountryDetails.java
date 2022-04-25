package com.dajdad.coronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetails extends AppCompatActivity {
    private TextView txt_country, txt_confirmed, txt_recovered, txt_deaths;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        txt_country = findViewById(R.id.txt_country);
        txt_confirmed = findViewById(R.id.txt_confirmed);
        txt_recovered = findViewById(R.id.txt_recovered);
        txt_deaths = findViewById(R.id.txt_deaths);
        back = findViewById(R.id.goBack);

        Intent intent = getIntent();

        String country = intent.getStringExtra("country");
        String confirmed = intent.getStringExtra("confirmed");
        String recovered = intent.getStringExtra("recovered");
        String deaths = intent.getStringExtra("deaths");

        txt_country.setText(country);
        startCountAnimation(txt_confirmed,Integer.parseInt(confirmed));
        startCountAnimation(txt_recovered,Integer.parseInt(recovered));
        startCountAnimation(txt_deaths,Integer.parseInt(deaths));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CountryDetails.this, MainActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }


    private static String matches(String str){
        long n = Long.parseLong(str);
        int m = (int) n/1000000;
        int k = (int) (n%1000000)/1000;
        int r = (int) n%1000;
        String rf;
        if(r>99)
            rf = String.valueOf(r);
        else if(r>9)
            rf = "0" + r;
        else rf = "00" +r;
        if(m==0)
            return (k!=0 ? k+".":"") + rf;
        return m+"."+(k!=0 ? k+".":"000") + rf;
    }

    private void startCountAnimation(TextView textView, int count) {
        ValueAnimator animator = ValueAnimator.ofInt(0, count); //0 is min number, count is max number
        animator.setDuration(1500); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(matches(animation.getAnimatedValue().toString()));
            }
        });
        animator.start();
    }
}