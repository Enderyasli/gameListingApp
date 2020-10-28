package com.enderyasli.gameListingApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameResponse {


    public List<GameDetail> getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(List<GameDetail> gameDetails) {
        this.gameDetails = gameDetails;
    }

    @SerializedName("results")
    @Expose
    private List<GameDetail> gameDetails;



}