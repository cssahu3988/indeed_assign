package com.example.indeed_assign.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface  ApiInterface {
    @GET("/photos/?client_id=f0irxxF5D_V9HYAPyVtZpOpQyHGe2ZGl_C1kbPNSSf4")
    Call<List<Data>> getImages();

    @GET("/photos/?client_id=f0irxxF5D_V9HYAPyVtZpOpQyHGe2ZGl_C1kbPNSSf4")
    public void getImagess(Call<List<Data>> call);
}
