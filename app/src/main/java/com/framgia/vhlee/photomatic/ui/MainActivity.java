package com.framgia.vhlee.photomatic.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String HOME = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        selectTab(new HomeFragment(), HOME, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                selectTab(HomeFragment.getInstance(), HOME, false);
                break;
            case R.id.navigation_rank:
                break;
            case R.id.navigation_gallery:
                break;
            case R.id.navigation_notifications:
                break;
            case R.id.navigation_more:
                break;
            default:
                break;
        }
        return true;
    }

    private void selectTab(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragments_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
