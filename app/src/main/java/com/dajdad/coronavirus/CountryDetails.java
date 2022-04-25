package com.dajdad.coronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CountryDetails extends AppCompatActivity {
    private TextView txt_country, txt_confirmed, txt_recovered, txt_deaths, txt_percentage, txt_cal_recovered;
    private ImageView back;
    private ProgressBar progressBar;
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        txt_country = findViewById(R.id.txt_country);
        txt_confirmed = findViewById(R.id.txt_confirmed);
        txt_recovered = findViewById(R.id.txt_recovered);
        txt_deaths = findViewById(R.id.txt_deaths);
        back = findViewById(R.id.goBack);
        progressBar = findViewById(R.id.progressBar);
        txt_percentage = findViewById(R.id.txt_percentage);
        txt_cal_recovered = findViewById(R.id.prgCases);

        Intent intent = getIntent();

        String country = intent.getStringExtra("country");
        String confirmed = intent.getStringExtra("confirmed");
        String recovered = intent.getStringExtra("recovered");
        String deaths = intent.getStringExtra("deaths");

        txt_country.setText(country);
        startCountAnimation(txt_confirmed,Integer.parseInt(confirmed));
        startCountAnimation(txt_recovered,Integer.parseInt(recovered));
        startCountAnimation(txt_deaths,Integer.parseInt(deaths));


        int rec = Integer.parseInt(recovered);
        int det = Integer.parseInt(deaths);
        int cas = Integer.parseInt(confirmed);
        progressBar.setMax(cas);
        progressBar.setProgress(cas-det);
        txt_cal_recovered.setText(matches(recovered));
        float deathsPer = (float)100* det/cas;
        txt_percentage.setText(String.format("%.2f", 100-deathsPer )+ "%");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection()){
                    Intent i = new Intent(CountryDetails.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }else showMyDialog();

            }
        });
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    private void showMyDialog() {
        Dialog dialog = new Dialog(CountryDetails.this);
        dialog.setContentView(R.layout.dialog_connection_error);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.cd_bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialog.show();

        Button btn_try_again = dialog.findViewById(R.id.btn_try_again);
        Button btn_exit = dialog.findViewById(R.id.btn_exit);

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CountryDetails.this, Loading.class);
                startActivity(intent);
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