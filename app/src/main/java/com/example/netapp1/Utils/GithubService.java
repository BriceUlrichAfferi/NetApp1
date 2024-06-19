package com.example.netapp1.Utils;

import com.example.netapp1.Models.GithubUser;
import com.example.netapp1.Models.GithubUserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users/{username}/following")
    //Call<List<GithubUser>> getFollowing(@Path("username") String username);
    Observable<List<GithubUser>> getFollowing(@Path("username") String username);
    @GET("/users/{username}")
        //Call<List<GithubUser>> getFollowing(@Path("username") String username);
    Observable<GithubUserInfo> getUserInfos(@Path("username") String username);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
