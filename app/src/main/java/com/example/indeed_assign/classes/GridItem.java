package com.example.indeed_assign.classes;

import androidx.lifecycle.MutableLiveData;

public class GridItem {
    private MutableLiveData<String> name;
    private MutableLiveData<String> url;

    public GridItem(MutableLiveData<String> name, MutableLiveData<String> url) {
        this.name = name;
        this.url = url;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<String> getUrl() {
        return url;
    }

    public void setUrl(MutableLiveData<String> url) {
        this.url = url;
    }
}
