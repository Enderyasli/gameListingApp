package com.enderyasli.gameListingApp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.enderyasli.gameListingApp.databinding.GameItemBinding;
import com.enderyasli.gameListingApp.model.GameDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {


    public GameAdapter(Activity context, ArrayList<GameDetail> collectionModel) {
        this.context = context;
        this.gameModel = collectionModel;
    }

    Activity context;
    ArrayList<GameDetail> gameModel;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GameItemBinding binding = GameItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GameDetail item = gameModel.get(position);


        holder.binding.titleTextview.setText(item.getName());
        holder.binding.definitontextView.setText(formatDate(item.getReleased()));
        holder.binding.ratingBar.setRating(item.getRating());

        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).
                asBitmap()
                .load(item.getBackground_image())
                .apply(options)
                .into(holder.binding.collectionImageView);

    }

    @Override
    public int getItemCount() {
        return gameModel.size();
    }

    public void updateGameList(List<GameDetail> employees) {

        this.gameModel.addAll(employees);
        this.notifyItemRangeInserted(this.gameModel.size() - employees.size(), employees.size());

    }

    private String formatDate(String date) {
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy ", Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(date, dtf);
            return dtf2.format(ld);

        } else {

            SimpleDateFormat month_date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date1 = sdf.parse(date);

                return month_date.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public GameItemBinding binding;


        public ViewHolder(GameItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
