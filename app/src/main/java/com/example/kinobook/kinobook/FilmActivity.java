package com.example.kinobook.kinobook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.kinobook.R;
import com.example.kinobook.kinobook.model.ModelDataCinema;
import com.example.kinobook.kinobook.retrofit.RetrofitData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FilmActivity extends AppCompatActivity {
    BottomNavigationView nav;
    private Subscription subscription;

    private ModelDataCinema dataCinema;

    private TextView textViewName;
    private TextView textViewSlogan;
    private TextView textViewDescription;
    private TextView textViewRatingKp;
    private ImageView imageViewPoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        textViewName = findViewById(R.id.textview_name);
        textViewSlogan = findViewById(R.id.textview_slogan);
        textViewDescription = findViewById(R.id.textview_description);
        textViewRatingKp = findViewById(R.id.textview_rating_kp);
        imageViewPoster = findViewById(R.id.imageview_poster);

        nav = findViewById(R.id.bottomNavigation);
        nav.setSelectedItemId(R.id.home);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.films:
                        startActivity(new Intent(getApplicationContext(), Vsefilms.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });
        getDataCinema();






    }

    private void getDataCinema(){
        if (subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }



        subscription = RetrofitData.getDataCinema("328")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelDataCinema>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(textViewName, "Ошибка подключения", Snackbar.LENGTH_SHORT)
                                .show();
                        Log.d("Ошибка",e.toString());
                    }

                    @Override
                    public void onNext(ModelDataCinema modelDataCinema) {
                        dataCinema = modelDataCinema;
                        fillData(modelDataCinema);
                    }
                });
    }

    private void fillData(ModelDataCinema modelDataCinema){
        Picasso.get()
                .load(modelDataCinema.getPoster().getUrl())
                .into(imageViewPoster);

        textViewName.setText(modelDataCinema.getName());
        textViewSlogan.setText(modelDataCinema.getSlogan());
        textViewDescription.setText(modelDataCinema.getDescription());
        textViewRatingKp.setText(String.format("%.1f",modelDataCinema.getRating().getKp()));
    }
}