package com.nextgentele.busvalidatorv2.apiupdate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;


import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException {
                                          Request original = chain.request();

                                          Request request = original.newBuilder()
                                                  .header("Connection", "keep-alive")
                                                  .header("Content-Type", "application/json")
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  })
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
//                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();



        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}
