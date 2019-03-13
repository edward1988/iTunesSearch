package com.ediksarkis.msttest.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItunesContent {

    @Expose
    @SerializedName("artistName")
    private String artistName;

    @Expose
    @SerializedName("collectionName")
    private String collectionName;

    @Expose
    @SerializedName("artworkUrl100")
    private String collectionImage;


    public String getArtistName(){
        return artistName;
    }

    public String getCollectionName(){
        return collectionName;
    }

    public String getCollectionImage(){
        return collectionImage;
    }


}
