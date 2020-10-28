package com.enderyasli.gameListingApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.enderyasli.gameListingApp.adapter.GameAdapter;


import com.enderyasli.gameListingApp.databinding.ActivityMainBinding;
import com.enderyasli.gameListingApp.model.GameDetail;
import com.enderyasli.gameListingApp.viewModel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;
    ActivityMainBinding binding;
    GameAdapter adapter = null;
    boolean isLoading = false;
    LinearLayoutManager linearLayoutManager;
    public int page = 1;

    @Override
    protected void onStart() {
        super.onStart();

        observeLiveData();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewModel = (MainActivityViewModel) ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.loadMoreGame(page);


    }


    /**
     * <p>
     * this method gets data from te API
     * </p>
     */
    private void observeLiveData() {

        viewModel.data.observe(this, gameResponse -> {

            if (adapter == null)
                initRecyclerView((ArrayList<GameDetail>) gameResponse.getGameDetails());
            else {
                /* added not to load extra pages */
                if (linearLayoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 1)
                    adapter.updateGameList((ArrayList<GameDetail>) gameResponse.getGameDetails());
            }

        });

        viewModel.dataLoading.observe(this, loading -> {

            if (page > 1) {

                if (loading) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    isLoading = true;
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    isLoading = false;
                }

            } else {
                if (loading) {
                    binding.progressBarMain.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBarMain.setVisibility(View.GONE);
                }
            }
        });

        viewModel.dataError.observe(this, error -> {

            /*
            if (error)
                binding.dataError.setVisibility(View.VISIBLE);
             else
                binding.dataError.setVisibility(View.GONE);
            */

        });
    }

    private void initRecyclerView(ArrayList<GameDetail> gameDetail) {

        adapter = new GameAdapter(MainActivity.this, gameDetail);

        binding.gameRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        linearLayoutManager = ((LinearLayoutManager) binding.gameRecyclerView.getLayoutManager());

        binding.gameRecyclerView.setAdapter(adapter);
        binding.gameRecyclerView.setHasFixedSize(true);


        initScrollListener();

    }

    private void initScrollListener() {
        binding.gameRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading) {
                    if (!recyclerView.canScrollVertically(1)) {
                        page++;
                        viewModel.loadMoreGame(page);
                    }
                }
            }
        });

    }

}