package com.enderyasli.gameListingApp.service;

import com.enderyasli.gameListingApp.model.GameResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IGetGames {

    @Headers({"x-rapidapi-host: rawg-video-games-database.p.rapidapi.com",
            "x-rapidapi-key: 90e881d70emshaa18e83013c846fp141dc5jsn56781df673cb"})
    @GET("games")

    Observable<GameResponse> getGames(@Query("page") int page);
}
