package com.example.indeed_assign;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.indeed_assign.API.Data;
import com.example.indeed_assign.Repository.SecondTaskRepo;
import com.example.indeed_assign.ViewModels.SecondTaskViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondTaskActivity extends AppCompatActivity {
    private SecondTaskRepo secondTaskRepo;
    private SecondTaskViewModel viewModel;
    private GridView gridView;
    private MutableLiveData<List<Data>>list;
    private List<Data> l = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_task);
        list = new MutableLiveData<>();
        adapter = new Adapter(this,l);
        gridView = findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
        secondTaskRepo = SecondTaskRepo.getInstance(getApplicationContext());

        if (isConnectingToInternet()){
            secondTaskRepo.getPhotos(list);
        }
        else{
            String json=getApplicationContext().getSharedPreferences("qwerty",MODE_PRIVATE).getString("list","");
            if (!json.isEmpty()){
                Type type = new TypeToken<List<Data>>(){}.getType();
                l = new Gson().fromJson(json,type);
                adapter.setValue(l);
            }
            else{
                Toast.makeText(getApplicationContext(), "No offile images", Toast.LENGTH_SHORT).show();
            }
        }

        observe();
    }
    private boolean isConnectingToInternet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;

    }
    private void observe(){
        list.observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                adapter.setValue(data);
                Toast.makeText(getApplicationContext(), "Got photos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    interface CallBack{
        void onCallback(Object object);
    }

    private class Adapter extends BaseAdapter {
        private List<Data> list;
        private Activity context;

        public Adapter(Activity context, List<Data> list){
            this.list = list;
            this.context = context;
        }
        public void setValue(List<Data> list){
            this.list = list;
            notifyDataSetChanged();
            getApplicationContext().getSharedPreferences("qwerty",MODE_PRIVATE).edit().putString("list",new Gson().toJson(list)).apply();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            }
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);
            Glide.with(context).load(list.get(position).user.profileImage.large).into(imageView);
//            imageView.setImageURI(Uri.parse(list.get(position).user.profileImage.large));
//            Picasso.get().load(list.get(position).user.profileImage.small).fit().networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
            textView.setText(list.get(position).getId());
            Log.d("TAG71", "getView: "+list.get(position).user.profileImage.large);
            return convertView;
        }
    }


}