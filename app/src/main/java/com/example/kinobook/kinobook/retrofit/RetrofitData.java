package com.example.kinobook.kinobook.retrofit;

import com.example.kinobook.kinobook.model.ModelDataCinema;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RetrofitData {

    private static Observable<ModelDataCinema> observable;
    private static BehaviorSubject<ModelDataCinema> obserableModelDataCinema;
    private static Subscription subscription;
    private static RequestAPI requestAPI;

    private static String previousIdStr = "";

    private RetrofitData(){

    }

    public static void initRetrofit(){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kinopoisk.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        requestAPI = retrofit.create(RequestAPI.class);


    }

    public static void resetObservable(String id){
        obserableModelDataCinema = BehaviorSubject.create();

        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        observable = requestAPI.getDataCinema("VATR22P-9CPMH1Q-MN5B7YZ-4G0YBB9", id, "id");

        previousIdStr = id;

        subscription = observable.subscribe(new Subscriber<ModelDataCinema>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                obserableModelDataCinema.onError(e);
            }

            @Override
            public void onNext(ModelDataCinema modelDataCinema) {
                obserableModelDataCinema.onNext(modelDataCinema);
            }
        });
    }

    public static Observable<ModelDataCinema> getDataCinema(String id){
        if (!previousIdStr.equals(id) || obserableModelDataCinema == null){
            resetObservable(id);
        }
        return obserableModelDataCinema;
    }

}
