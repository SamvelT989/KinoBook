package com.example.kinobook.kinobook.retrofit;

import com.example.kinobook.kinobook.model.Docs;
import com.example.kinobook.kinobook.model.ModelDataCinema;
import com.example.kinobook.kinobook.retrofit.RequestAPI;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RetrofitDataMonth {

    private static Observable<Docs> observable;
    private static BehaviorSubject<Docs> obserableModelDataCinema;
    private static Subscription subscription;
    private static RequestAPI requestAPI;

    private RetrofitDataMonth(){

    }

    public static void initRetrofit(){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kinopoisk.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        requestAPI = retrofit.create(RequestAPI.class);

        //observable = requestAPI.getDataCinema("VATR22P-9CPMH1Q-MN5B7YZ-4G0YBB9", "843650", "id");
    }

    public static void resetObservable(String month){
        obserableModelDataCinema = BehaviorSubject.create();

        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        observable = requestAPI.getDataCinemaMonth("VATR22P-9CPMH1Q-MN5B7YZ-4G0YBB9", month, "year", "100");

        subscription = observable.subscribe(new Subscriber<Docs>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                obserableModelDataCinema.onError(e);
            }

            @Override
            public void onNext(Docs modelDataCinema) {
                obserableModelDataCinema.onNext(modelDataCinema);
            }
        });
    }

    public static Observable<Docs> getDataCinema(String month){
        if (obserableModelDataCinema == null){
            resetObservable(month);
        }
        return obserableModelDataCinema;
    }
}
