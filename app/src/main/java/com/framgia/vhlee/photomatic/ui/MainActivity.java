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
import com.framgia.vhlee.photomatic.ui.more.MoreFragment;
import com.framgia.vhlee.photomatic.ui.upload.UploadActivity;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String HOME = "Home";
    private static final String MORE = "More";
    private static final String GALLERY = "Gallery";
    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);
        selectTab(new HomeFragment(), HOME, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomNavigation.getMenu().getItem(0).setChecked(true);
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
                startActivity(UploadActivity.getShareIntent(this));
                break;
            case R.id.navigation_notifications:
                break;
            case R.id.navigation_more:
                selectTab(new MoreFragment(), MORE, false);
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
