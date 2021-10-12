package com.example.indeed_assign.ViewModels;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.indeed_assign.API.Data;

import java.util.ArrayList;
import java.util.List;

public class SecondTaskViewModel extends AndroidViewModel {
    private MutableLiveData<List<Data>> list;
    private List<Data> list2;
    public SecondTaskViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        list = new MutableLiveData<>();
        list2 = new ArrayList<>();
        list.postValue(list2);
    }

    public MutableLiveData<List<Data>> getList() {
        return list;
    }

    public void setList(MutableLiveData<List<Data>> list) {
        this.list = list;
    }
}
