package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Step {

    @SerializedName("id")
    int id;

    @SerializedName("shortDescription")
    String shortDescription;

    @SerializedName("description")
    String description;

    @SerializedName("videoURL")
    String videoURL;

    @SerializedName("thumbnailURL")
    String thumbnailURL;

    public Step() { }

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}