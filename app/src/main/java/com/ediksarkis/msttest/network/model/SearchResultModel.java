package com.ediksarkis.msttest.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultModel {
    @SerializedName("resultCount")
    @Expose
    private int resultCount;
    @SerializedName("results")
    @Expose
    private List<ItunesContent> resultModels = null;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<ItunesContent> getResultModels() {
        return resultModels;
    }

    public void setResultModels(List<ItunesContent> resultModels) {
        this.resultModels = resultModels;
    }
}
