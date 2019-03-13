package com.ediksarkis.msttest;

import com.ediksarkis.msttest.network.ApiService;
import com.ediksarkis.msttest.network.model.SearchResultModel;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;

import static org.junit.Assert.assertTrue;

public class NetworkUnitTest {

    @Test
    public void testServer() throws IOException {


    }


    private String sendGetRequest(OkHttpClient okHttpClient, HttpUrl httpUrl)throws IOException{

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),"some text");

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().toString();
    }
}
