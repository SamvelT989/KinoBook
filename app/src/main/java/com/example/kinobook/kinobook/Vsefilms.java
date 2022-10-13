package com.example.kinobook.kinobook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kinobook.R;
import com.example.kinobook.kinobook.adapter.Adapter;
import com.example.kinobook.kinobook.model.Docs;
import com.example.kinobook.kinobook.model.ModelDataCinema;
import com.example.kinobook.kinobook.retrofit.RetrofitDataMonth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Vsefilms extends AppCompatActivity {
    BottomNavigationView nav;

    private Subscription subscription;

    private Docs docs;
    private ListView listView;
    private Adapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsefilms);

        listView = findViewById(R.id.listview_month_film);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("Ошибка", String.valueOf(docs.getDocs().get(i).getId()));
                Intent intent = new Intent(Vsefilms.this, Podgruzfilm.class);
                intent.putExtra("Ошибка", String.valueOf(docs.getDocs().get(i).getId()));
                startActivity(intent);
            }
        });

        getDataCinema();

        nav = findViewById(R.id.bottomNavigation);
        nav.setSelectedItemId(R.id.films);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), FilmActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

    }
    private void getDataCinema(){
        if (subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        subscription = RetrofitDataMonth.getDataCinema("2022")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Docs>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(listView, "Ошибка подключения", Snackbar.LENGTH_SHORT)
                                .show();
                        Log.d("Ошибка",e.toString());
                    }

                    @Override
                    public void onNext(Docs modelDataCinema) {
                        docs = modelDataCinema;
                        List list = docs.getDocs();
                        Predicate<ModelDataCinema> condition = employee -> employee.getName() == null;
                        list.removeIf(condition);
                        adapter = new Adapter(list);
                        listView.setAdapter(adapter);
                    }
                });
    }
}