package com.udacity.baking.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Step {

    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;

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