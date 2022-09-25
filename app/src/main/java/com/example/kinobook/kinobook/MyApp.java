package com.example.kinobook.kinobook;

import android.app.Application;

import com.example.kinobook.kinobook.retrofit.RetrofitData;
import com.example.kinobook.kinobook.retrofit.RetrofitDataMonth;

public class MyApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        RetrofitData.initRetrofit();
        RetrofitDataMonth.initRetrofit();
    }
}
