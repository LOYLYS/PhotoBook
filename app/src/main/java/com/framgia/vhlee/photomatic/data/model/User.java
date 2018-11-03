package com.framgia.vhlee.photomatic.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class User implements Parcelable {
    private static final String NO_INFO = "No information";
    private String mAvatar;
    private String mEmail;
    private String mNickName;
    private String mDescription;
    private String mAddress;
    private long mJoinTime;
    private long mBirthday;
    private int mSexual;
    private int mPost;
    private int mSubscriber;
    private int mPoint;
    private int mType;

    public User() {
        mDescription = NO_INFO;
        mAddress = NO_INFO;
    }

    protected User(Parcel in) {
        mAvatar = in.readString();
        mEmail = in.readString();
        mNickName = in.readString();
        mDescription = in.readString();
        mAddress = in.readString();
        mJoinTime = in.readLong();
        mBirthday = in.readLong();
        mSexual = in.readInt();
        mPost = in.readInt();
        mSubscriber = in.readInt();
        mPoint = in.readInt();
        mType = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public long getJoinTime() {
        return mJoinTime;
    }

    public void setJoinTime(long joinTime) {
        mJoinTime = joinTime;
    }

    public long getBirthday() {
        return mBirthday;
    }

    public void setBirthday(long birthday) {
        mBirthday = birthday;
    }

    public int getSexual() {
        return mSexual;
    }

    public void setSexual(@SexualCode int sexual) {
        mSexual = sexual;
    }

    public int getPost() {
        return mPost;
    }

    public void setPost(int post) {
        mPost = post;
    }

    public int getSubscriber() {
        return mSubscriber;
    }

    public void setSubscriber(int subscriber) {
        mSubscriber = subscriber;
    }

    public int getPoint() {
        return mPoint;
    }

    public void setPoint(int point) {
        mPoint = point;
    }

    public int getType() {
        return mType;
    }

    public void setType(@TypeCode int type) {
        mType = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAvatar);
        parcel.writeString(mEmail);
        parcel.writeString(mNickName);
        parcel.writeString(mDescription);
        parcel.writeString(mAddress);
        parcel.writeLong(mJoinTime);
        parcel.writeLong(mBirthday);
        parcel.writeInt(mSexual);
        parcel.writeInt(mPost);
        parcel.writeInt(mSubscriber);
        parcel.writeInt(mPoint);
        parcel.writeInt(mType);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            SexualCode.NONE, SexualCode.MALE, SexualCode.FEMALE
    })
    @interface SexualCode {
        int NONE = 0;
        int MALE = 1;
        int FEMALE = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TypeCode.NEWBIE, TypeCode.OFFICAL,
            TypeCode.ACTIVE, TypeCode.ADMIN
    })
    @interface TypeCode {
        int NEWBIE = 0;
        int OFFICAL = 1;
        int ACTIVE = 2;
        int ADMIN = 3;
    }
}
