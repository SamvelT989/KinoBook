package com.example.kinobook.kinobook.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Docs implements Serializable {
    @SerializedName("docs")
    private List<ModelDataCinema> docs;

    public List<ModelDataCinema> getDocs() {
        return docs;
    }
}
