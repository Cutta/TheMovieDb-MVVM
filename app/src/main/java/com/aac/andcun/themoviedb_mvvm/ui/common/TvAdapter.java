package com.aac.andcun.themoviedb_mvvm.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.databinding.ItemMovieBinding;
import com.aac.andcun.themoviedb_mvvm.databinding.ItemTvBinding;
import com.aac.andcun.themoviedb_mvvm.vo.ResultTv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by cuneytcarikci on 25/05/2017.
 */

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private List<ResultTv> movieList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    public TvAdapter() {
        this.movieList = new ArrayList<>();
    }

    public void addMovieList(List<ResultTv> movieList) {
        List<ResultTv> sumList = this.movieList;
        sumList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTvBinding binding = ItemTvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TvViewHolder holder, int position) {
        ResultTv movie = movieList.get(position);
        holder.bind(movie);
        /*if (temp instanceof ResultTv) {
            ResultTv tempMovie = (ResultTv) temp;
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

    static class TvViewHolder extends RecyclerView.ViewHolder {

        ItemTvBinding binding;

        TvViewHolder(ItemTvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(ResultTv ResultTv) {
            binding.setTv(ResultTv);
            binding.executePendingBindings();
        }

    }

}