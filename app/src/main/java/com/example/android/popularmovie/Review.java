package com.example.android.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;

    }

    public String getAuthor() { return author; }

    public String getContent() { return content; }

    @Override
    public int describeContents() {
        return 0;
    }

    public Review(Parcel in) {
        readFromParcel(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {

        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    private void readFromParcel(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

}
