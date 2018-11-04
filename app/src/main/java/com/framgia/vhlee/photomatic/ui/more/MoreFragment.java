package com.framgia.vhlee.photomatic.ui.more;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.ui.login.LoginActivity;
import com.framgia.vhlee.photomatic.ui.profile.ProfileActivity;
import com.framgia.vhlee.photomatic.util.Notifications;
import com.google.firebase.auth.FirebaseAuth;

public class MoreFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        ImageView imageEditProfile = view.findViewById(R.id.image_edit_profile);
        View viewProfile = view.findViewById(R.id.view_profile);
        View viewSignOut = view.findViewById(R.id.linear_sign_out);
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
}
