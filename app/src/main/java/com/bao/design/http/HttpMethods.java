package com.bao.design.http;

import android.util.Log;

import com.bao.design.Entry.Top250Entry;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods {
    private static final String TAG="HttpMethods";
    Retrofit retrofit = null;
    MovieServer moviceService = null;
    private static volatile HttpMethods instance = null;
    public static HttpMethods getInstance() { //创建单例
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null) {
                    instance = new HttpMethods();
                }
            }
        }
        return instance;
    }
    private HttpMethods(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                //.client(httpClient)
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        moviceService = retrofit.create(MovieServer.class);
    }
    //https://api.douban.com/v2/movie/top250?start=0&count=2
    public void getTop250(DisposableObserver<Top250Entry.Subject> top250Observer,int start,int count){
        moviceService.getTop250(start,count)
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Top250Entry, List<Top250Entry.Subject>>() {
                    @Override
                    public List<Top250Entry.Subject> apply(Top250Entry top250Entry) throws Exception {
                        return top250Entry.getSubjects();
                    }
                })
                .flatMap(new Function<List<Top250Entry.Subject>, ObservableSource<Top250Entry.Subject>>() {
                    @Override
                    public ObservableSource<Top250Entry.Subject> apply(List<Top250Entry.Subject> subjects) throws Exception {
                        return Observable.fromIterable(subjects);
                    }
                })
                .subscribe(top250Observer);
    }
}
