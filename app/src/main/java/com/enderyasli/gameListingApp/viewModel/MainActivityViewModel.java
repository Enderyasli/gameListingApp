package com.enderyasli.gameListingApp.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.enderyasli.gameListingApp.model.GameResponse;
import com.enderyasli.gameListingApp.service.IGetGames;
import com.enderyasli.gameListingApp.service.RetrofitInstance;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    IGetGames getGames;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MutableLiveData<GameResponse> data = new MutableLiveData<GameResponse>();
    public MutableLiveData<Boolean> dataError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();

    public void loadMoreGame(int page) {
        getGameData(page);
    }

    /**
     * <p>
     * this make request with RxJava
     * </p>
     */
    private void getGameData(int page) {


        getGames = RetrofitInstance.getGames();
        dataLoading.setValue(true);

        compositeDisposable.add(getGames.getGames(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GameResponse>() {
                    @Override
                    public void onNext(GameResponse gameResponse) {

                        data.setValue(gameResponse);
                        dataError.setValue(false);
                        dataLoading.setValue(false);

                    }

                    @Override
                    public void onError(Throwable e) {

                        dataError.setValue(true);
                        dataLoading.setValue(false);

                        Log.d("serverError", Objects.requireNonNull(e.getMessage()));

                    }

                    @Override
                    public void onComplete() {
                    }
                }));


    }


}



