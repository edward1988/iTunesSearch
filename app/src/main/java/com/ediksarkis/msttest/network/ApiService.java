package com.ediksarkis.msttest.network;

import com.ediksarkis.msttest.app.Const;
import com.ediksarkis.msttest.network.model.SearchResultModel;


import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Const.SEARCH_PREFIX)
    Single<SearchResultModel> getItunesContent(@Query(Const.SEARCH) CharSequence search);
}
