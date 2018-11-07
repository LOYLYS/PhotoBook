package com.framgia.vhlee.photomatic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String mCaption;
    private String mPhotoUrl;
    private String mUserId;
    private long mCreateTime;
    private int mLikeCount;
    private int mCommentCount;

    public Post() {
    }

    public Post(String caption, String photoUrl,
                String userId, long createTime, int likeCount, int commentCount) {
        mCaption = caption;
        mPhotoUrl = photoUrl;
        mUserId = userId;
        mCreateTime = createTime;
        mLikeCount = likeCount;
        mCommentCount = commentCount;
    }

    protected Post(Parcel in) {
        mCaption = in.readString();
        mPhotoUrl = in.readString();
        mUserId = in.readString();
        mCreateTime = in.readLong();
        mLikeCount = in.readInt();
        mCommentCount = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(long createTime) {
        mCreateTime = createTime;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        mLikeCount = likeCount;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCaption);
        parcel.writeString(mPhotoUrl);
        parcel.writeString(mUserId);
        parcel.writeLong(mCreateTime);
        parcel.writeInt(mLikeCount);
        parcel.writeInt(mCommentCount);
    }
}
