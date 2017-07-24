package com.aac.andcun.themoviedb_mvvm.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ItemMovieBinding;
import com.aac.andcun.themoviedb_mvvm.vo.ResultMovie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by cuneytcarikci on 25/05/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<ResultMovie> movieList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    public MovieAdapter() {
        this.movieList = new ArrayList<>();
    }

    public void addMovieList(List<ResultMovie> movieList) {
        List<ResultMovie> sumList = this.movieList;
        sumList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ResultMovie movie = movieList.get(position);
        holder.bind(movie);
        /*if (temp instanceof ResultMovie) {
            ResultMovie tempMovie = (ResultMovie) temp;
            Glide.with(holder.itemView.getContext()).
                    load(ApiConstants.IMAGE_PREFIX + tempMovie.getBackdropPath())
                    .into(holder.ivPoster);
            holder.tvTitle.setText(tempMovie.getTitle());
            holder.tvGenres.setText(Genre.getGenresText(tempMovie.getGenreIds()));
            holder.tvVoteAverage.setText(String.valueOf(tempMovie.getVoteAverage()));
            holder.tvReleaseYear.setText(dateFormat.format(tempMovie.getReleaseDate()));
        } else {
            ResultTv tempTv = (ResultTv) temp;
            Glide.with(holder.itemView.getContext()).
                    load(ApiConstants.IMAGE_PREFIX + tempTv.getBackdropPath())
                    .into(holder.ivPoster);
            holder.tvTitle.setText(tempTv.getName());
            holder.tvGenres.setText(Genre.getGenresText(tempTv.getGenreIds()));
            holder.tvVoteAverage.setText(String.valueOf(tempTv.getVoteAverage()));
            holder.tvReleaseYear.setText(dateFormat.format(tempTv.getFirstAirDate()));
        }*/

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(ResultMovie resultMovie) {
            binding.setMovie(resultMovie);
            binding.executePendingBindings();
        }

    }

}