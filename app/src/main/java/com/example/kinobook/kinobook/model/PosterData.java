package com.example.kinobook.kinobook.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PosterData implements Serializable {
    @SerializedName("url")
    private String url;

    @SerializedName("previewUrl")
    private String previewUrl;

    public String getUrl() {
        return url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }
}
