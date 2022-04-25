package com.dajdad.coronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

public class Loading extends AppCompatActivity {
    public final long TIME_OUT = 2000;
    private SpinKitView spinKitView;
    private TextView txt_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        spinKitView = findViewById(R.id.spin_kit);
        txt_loading = findViewById(R.id.txt_loading);



        new Handler().postDelayed(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                if(checkConnection()){
                    Intent i = new Intent(Loading.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else {
                    spinKitView.setVisibility(View.GONE);
                    txt_loading.setText("");
                    Dialog dialog = new Dialog(Loading.this);
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
                            Intent intent = new Intent(Loading.this, Loading.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        }
                    });

                    btn_exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                }

            }
        }, TIME_OUT);

    }
    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
}