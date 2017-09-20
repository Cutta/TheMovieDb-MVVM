package com.aac.andcun.themoviedb_mvvm.ui.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.databinding.ItemMovieBinding;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cuneytcarikci on 25/05/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter() {
        this.movieList = new ArrayList<>();
    }

    public void addMovieList(List<Movie> movieList) {
        List<Movie> sumList = this.movieList;
        sumList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getAdapterPosition(),
                            movieList.get(holder.getAdapterPosition()));
            }
        });

        Movie movie = movieList.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, Movie item);
    }

}