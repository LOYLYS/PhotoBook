package com.framgia.vhlee.photomatic.ui.more;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.ui.editprofile.EditProfileActivity;
import com.framgia.vhlee.photomatic.ui.login.LoginActivity;
import com.framgia.vhlee.photomatic.ui.profile.ProfileActivity;
import com.framgia.vhlee.photomatic.util.Notifications;
import com.google.firebase.auth.FirebaseAuth;

public class MoreFragment extends Fragment
        implements View.OnClickListener, MoreContract.View {
    private FirebaseAuth mAuth;
    private MorePresenter mMorePresenter;
    private TextView mTextName;
    private ImageView mImageAvatar;

    public MoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ImageView imageEditProfile = view.findViewById(R.id.image_edit_profile);
        View viewProfile = view.findViewById(R.id.view_profile);
        View viewSignOut = view.findViewById(R.id.linear_sign_out);
        mImageAvatar = view.findViewById(R.id.image_avatar_more_fragment);
        mTextName = view.findViewById(R.id.text_nick_name);
        mAuth = FirebaseAuth.getInstance();
        mMorePresenter = new MorePresenter(this);
        mMorePresenter.getProfile(mAuth.getUid());
        imageEditProfile.setOnClickListener(this);
        viewProfile.setOnClickListener(this);
        viewSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_profile:
                startActivity(ProfileActivity.getProfileIntent(getContext(), mAuth.getUid()));
                break;
            case R.id.image_edit_profile:
                startActivity(EditProfileActivity.getEditProfileIntent(getContext(), mAuth.getUid()));
                break;
            case R.id.linear_sign_out:
                String email = mAuth.getCurrentUser().getEmail();
                mAuth.signOut();
                startActivity(LoginActivity.getLoginIntent(getContext(), email));
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetProfileSuccess(User user) {
        mTextName.setText(user.getNickName());
    }

    @Override
    public void onFailure(String message) {

    }
}
