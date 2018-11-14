package com.ws.yonghong.doustudy.function.rxjavaretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<ReponseBean>> listRepos(@Path("user") String user);
}