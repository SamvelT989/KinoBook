package com.example.kinobook.kinobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kinobook.R;
import com.example.kinobook.kinobook.model.ModelDataCinema;
import com.example.kinobook.kinobook.retrofit.RetrofitData;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Podgruzfilm extends AppCompatActivity {

    private Subscription subscription;
    private String id;

    private TextView textViewName;
    private TextView textViewSlogan;
    private TextView textViewDescription;
    private TextView textViewRatingKp;
    private ImageView imageViewPoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podgruzfilm);

        textViewName = findViewById(R.id.textview_name_more);
        textViewSlogan = findViewById(R.id.textview_slogan_more);
        textViewDescription = findViewById(R.id.textview_description_more);
        textViewRatingKp = findViewById(R.id.textview_rating_kp_more);
        imageViewPoster = findViewById(R.id.imageview_poster_more);

        Bundle intent = getIntent().getExtras();
        id = intent.getString("Ошибка");

        getDataCinema(id);
    }
    private void getDataCinema(String id){
        if (subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        subscription = RetrofitData.getDataCinema(id)
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