package com.dajdad.coronavirus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView nav_icon;
    private FragmentManager fragmentManager;
    private boolean nav_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_icon = findViewById(R.id.img_menu);
        fragmentManager = getSupportFragmentManager();
        nav_icon.setOnClickListener(this);
        replaceFragment(new Home());
        nav_icon.setBackgroundResource(R.drawable.icon_menu);
    }



    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(nav_clicked ? R.anim.slide_in_left :R.anim.slide_in_right,
                                                nav_clicked ? R.anim.slide_out_right:R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_menu:
                nav_clicked = !nav_clicked;
                if(nav_clicked){
                    if(checkConnection()){
                        replaceFragment(new Country());
                        nav_icon.setBackgroundResource(R.drawable.icon_half_menu);
                    }else {
                        showMyDialog();
                    }
                }
                else {
                    if(checkConnection()){
                        replaceFragment(new Home());
                        nav_icon.setBackgroundResource(R.drawable.icon_menu);
                    }else {
                        showMyDialog();
                    }
                }
                break;
        }
    }

    private void showMyDialog(){
        Dialog dialog = new Dialog(MainActivity.this);
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
                Intent intent = new Intent(MainActivity.this, Loading.class);
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

    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
}