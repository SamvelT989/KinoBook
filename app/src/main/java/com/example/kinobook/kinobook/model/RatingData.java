package com.example.kinobook.kinobook.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class RatingData implements Serializable {

    @SerializedName("tmdb")
    private double tmdb;

    @SerializedName("kp")
    private double kp;

    @SerializedName("imdb")
    private double imdb;

    public double getTmdb() {
        return tmdb;
    }

    public double getKp() {
        return kp;
    }

    public double getImdb() {
        return imdb;
    }

}
