package com.bao.design.http;

import com.bao.design.Entry.Top250Entry;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.douban.com/v2/movie/top250?start=0&count=2
public interface MovieServer{
    @GET("top250")
   Observable<Top250Entry> getTop250(@Query("start")int start,@Query("count")int count);
//    Observable<ResponseBody> getTop250(@Query("start")int start, @Query("count")int count);
}
