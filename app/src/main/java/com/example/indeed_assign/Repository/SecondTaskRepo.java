package com.example.indeed_assign.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.indeed_assign.API.Api;
import com.example.indeed_assign.API.ApiInterface;
import com.example.indeed_assign.API.Data;
import com.example.indeed_assign.BaseClass.BASE;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondTaskRepo  {
    private static SecondTaskRepo instance = null;
    private Context context=null;
    private ApiInterface apiInterface = null;

    private SecondTaskRepo(Context context){
        this.context = context;
        this.apiInterface = Api.getApiInterface();
    }

    public static SecondTaskRepo getInstance(Context context){
        if (instance==null){
            instance = new SecondTaskRepo(context);
        }
        return instance;
    }

    public void getPhotos(MutableLiveData<List<Data>> photos){
        Call<List<Data>> call = this.apiInterface.getImages();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(@NonNull Call<List<Data>> call, @NonNull Response<List<Data>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Log.d("TAG71", "onResponse: "+response.body().size());
                    photos.postValue(response.body());
                }
                else{
                    Log.d("TAG71", "onResponse: "+"Failure"+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Data>> call, @NonNull Throwable t) {
                Log.d("TAG71", "onFailure: "+t.toString());
            }
        });
    }
    public void getPhotoss(){

    }

}
