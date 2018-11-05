package com.framgia.vhlee.photomatic.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.adapter.GridPhotoAdapter;
import com.framgia.vhlee.photomatic.adapter.NonScrollListView;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.model.User.TypeCode;
import com.framgia.vhlee.photomatic.util.Constants;
import com.framgia.vhlee.photomatic.util.Time;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity
        implements ProfileContract.View, GridPhotoAdapter.PhotoClickListener {
    private static final String NEWBIE = "Newbie";
    private static final String OFFICIAL = "Official";
    private static final String ACTIVE = "Active";
    private static final String ADMIN = "Admin";
    private ProfilePresenter mPresenter;
    private ImageView mImageCover;
    private ImageView mImageAvatar;
    private ImageView mImageSubscribe;
    private TextView mTextName;
    private TextView mTextType;
    private TextView mTextPost;
    private TextView mTextSubs;
    private TextView mTextPoint;
    private TextView mTextEmail;
    private TextView mTextAddress;
    private TextView mTextDescription;
    private TextView mTextBirthday;
    private GridPhotoAdapter mGridPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initUI();
        initData();
    }

    private void initUI() {
        mImageCover = findViewById(R.id.image_cover);
        mImageAvatar = findViewById(R.id.image_avatar);
        mImageSubscribe = findViewById(R.id.image_subscribe);
        mTextName = findViewById(R.id.text_nick_profile);
        mTextType = findViewById(R.id.text_type_profile);
        mTextPost = findViewById(R.id.text_posts_count);
        mTextSubs = findViewById(R.id.text_subscriber_count);
        mTextPoint = findViewById(R.id.text_point_count);
        mGridPhotoAdapter = new GridPhotoAdapter(this);
        mGridPhotoAdapter.setPhotoClickListener(this);
        RecyclerView recyclerGrid = findViewById(R.id.recycler_grid_photos);
        recyclerGrid.setAdapter(mGridPhotoAdapter);
    }

    public void initData() {
        mPresenter = new ProfilePresenter(this);
        String userId = getIntent().getStringExtra(Constants.EXTRA_USER_ID);
        mPresenter.getProfile(userId);
        initGallery();
    }

    private void initGallery() {
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/serrano.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/monarch.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/monarch.png");
        mGridPhotoAdapter.addPhoto("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
    }

    public static Intent getProfileIntent(Context context, String userId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(Constants.EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    public void onGetProfileSuccess(User user) {
        mTextName.setText(user.getNickName());
        setUserType(user.getType());
        mTextPost.setText(String.valueOf(user.getPost()));
        mTextSubs.setText(String.valueOf(user.getSubscriber()));
        mTextPoint.setText(String.valueOf(user.getPoint()));
        updateBasicInfo(user);
    }

    private void updateBasicInfo(User user) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String[] keys = {"Name", "Sex", "Address",
                "Birthday", "Join time",
                "Email", "Description"};
        String[] values = {user.getNickName(), getSex(user.getSexual()), user.getAddress(),
                user.getBirthday(), Time.convertTime(user.getJoinTime()),
                user.getEmail(), user.getDescription()};
        String[] from = {"key", "value"};
        int[] to = {R.id.text_key_info, R.id.text_value_info};
        for (int i = 0; i < keys.length; i++) {
            HashMap<String, String> basicInfo = new HashMap<>();
            basicInfo.put(from[0], keys[i]);
            basicInfo.put(from[1], values[i]);
            list.add(basicInfo);
        }
        SimpleAdapter simpleAdapter =
                new SimpleAdapter(this, list, R.layout.item_info, from, to);
        NonScrollListView listInfo = findViewById(R.id.list_view_info);
        listInfo.setAdapter(simpleAdapter);
    }

    private void setUserType(@TypeCode int type) {
        switch (type) {
            case TypeCode.ACTIVE:
                mTextType.setText(R.string.type_active);
                mTextType.setBackgroundColor(getResources().getColor(R.color.color_green_a500));
                break;
            case TypeCode.ADMIN:
                mTextType.setText(R.string.type_admin);
                mTextType.setBackgroundColor(Color.BLACK);
                break;
            case TypeCode.NEWBIE:
                mTextType.setText(R.string.type_newbie);
                mTextType.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
                break;
            case TypeCode.OFFICIAL:
                mTextType.setText(R.string.type_official);
                mTextType.setBackgroundColor(getResources().getColor(R.color.color_blue_a700));
                break;
            default:
                mTextType.setText(R.string.type_newbie);
                mTextType.setBackgroundColor(Color.MAGENTA);
                break;
        }
    }

    public String getSex(int code) {
        if (code == 1) return getString(R.string.text_male);
        if (code == 2) return getString(R.string.text_female);
        return getString(R.string.text_secret);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onPhotoClick(int position) {

    }
}
