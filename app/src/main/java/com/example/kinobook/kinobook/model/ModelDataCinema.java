package com.example.kinobook.kinobook.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModelDataCinema implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("slogan")
    private String slogan;

    @SerializedName("poster")
    private PosterData poster;

    @SerializedName("rating")
    private RatingData rating;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSlogan() {
        return slogan;
    }

    public PosterData getPoster() {
        return poster;
    }

    public RatingData getRating() {
        return rating;
    }
}
