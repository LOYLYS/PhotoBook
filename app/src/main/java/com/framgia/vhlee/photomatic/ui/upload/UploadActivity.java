package com.framgia.vhlee.photomatic.ui.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.adapter.GridPhotoAdapter;
import com.framgia.vhlee.photomatic.ui.post.PostActivity;
import com.framgia.vhlee.photomatic.util.FileSearch;
import com.framgia.vhlee.photomatic.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity
        implements View.OnClickListener, GridPhotoAdapter.PhotoClickListener,
        UploadContract.View, AdapterView.OnItemSelectedListener {
    private static final String EXTRA_DATA = "data";
    private static final String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    private static final String PICTURES = "/pictures";
    private static final String CAMERA = "/DCIM/";
    private static final String DOWNLOAD = "/download";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION = 13;
    private UploadPresenter mPresenter;
    private GridPhotoAdapter mGridPhotoAdapter;
    private ImageView mImageSelected;
    private Spinner mSpinnerDirs;
    private boolean mIsCenterCrop;
    private List<String> mDirectories;
    private String mPhotoSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initUI();
        checkPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(EXTRA_DATA);
            mImageSelected.setImageBitmap(imageBitmap);
        }
    }

    public static Intent getShareIntent(Context context) {
        return new Intent(context, UploadActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_DENIED) initData();
                else checkPermission();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_PERMISSION);
        } else initData();
    }

    private void initData() {
        initSpinner();
        mPresenter.getPhotos(mDirectories.get(0));
    }

    private void initSpinner() {
        mDirectories = new ArrayList<>();
        if (FileSearch.getDirectoryPaths(Strings.append(ROOT_DIR, PICTURES)) != null) {
            mDirectories = FileSearch.getDirectoryPaths(Strings.append(ROOT_DIR, CAMERA));
        }
        if (FileSearch.getDirectoryPaths(Strings.append(ROOT_DIR, PICTURES)) != null) {
            mDirectories.addAll(FileSearch.getDirectoryPaths(Strings.append(ROOT_DIR, PICTURES)));
        }
        mDirectories.add(Strings.append(ROOT_DIR, DOWNLOAD));
        ArrayList<String> directoryNames = new ArrayList<>();
        for (int i = 0; i < mDirectories.size(); i++) {
            String string = mDirectories.get(i).replace(ROOT_DIR, "");
            directoryNames.add(string);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDirs.setAdapter(adapter);

    }

    private void initUI() {
        mImageSelected = findViewById(R.id.image_selected);
        mSpinnerDirs = findViewById(R.id.spinner_directory);
        ImageView imageClose = findViewById(R.id.image_close);
        TextView textNext = findViewById(R.id.text_next);
        ImageView imageCamera = findViewById(R.id.image_camrera);
        mPresenter = new UploadPresenter(this);
        mGridPhotoAdapter = new GridPhotoAdapter(this);
        mGridPhotoAdapter.setPhotoClickListener(this);
        mImageSelected.setOnClickListener(this);
        mSpinnerDirs.setOnItemSelectedListener(this);
        imageClose.setOnClickListener(this);
        textNext.setOnClickListener(this);
        imageCamera.setOnClickListener(this);
        RecyclerView recyclerPhoto = findViewById(R.id.recycler_gallery);
        recyclerPhoto.setAdapter(mGridPhotoAdapter);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                finish();
                break;
            case R.id.text_next:
                startActivity(PostActivity.getPostIntent(this, mPhotoSource));
                break;
            case R.id.image_selected:
                if (mIsCenterCrop) {
                    mIsCenterCrop = false;
                    mImageSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    mIsCenterCrop = true;
                    mImageSelected.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
            case R.id.image_camrera:
                dispatchTakePictureIntent();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPhotoClick(int position) {
        mPhotoSource = mGridPhotoAdapter.getPhotoSource(position);
        mIsCenterCrop = false;
        mImageSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(this)
                .load(mPhotoSource)
                .into(mImageSelected);
    }

    @Override
    public void onSuccess(List<String> paths) {
        mGridPhotoAdapter.addPhotos(paths);
        mPhotoSource = mGridPhotoAdapter.getPhotoSource(0);
        mIsCenterCrop = true;
        Glide.with(this)
                .load(mPhotoSource)
                .into(mImageSelected);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        mGridPhotoAdapter.addPhotos(new ArrayList<String>());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d("pathPhoto", "onItemSelected: " + mDirectories.get(position));
        mPresenter.getPhotos(mDirectories.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
