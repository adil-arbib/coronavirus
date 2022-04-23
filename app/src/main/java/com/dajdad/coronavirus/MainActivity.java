package com.dajdad.coronavirus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
                    replaceFragment(new Country());
                    nav_icon.setBackgroundResource(R.drawable.icon_half_menu);
                }
                else {
                    replaceFragment(new Home());
                    nav_icon.setBackgroundResource(R.drawable.icon_menu);
                }
                break;
        }
    }
}