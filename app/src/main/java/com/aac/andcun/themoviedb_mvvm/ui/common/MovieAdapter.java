package com.aac.andcun.themoviedb_mvvm.ui.common;

import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.util.DiffUtil;
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

    private List<Movie> mMovieList;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter() {
        this.mMovieList = new ArrayList<>();
    }

    public void updateMovieList(final List<Movie> movieList) {
        this.mMovieList = movieList;
        notifyDataSetChanged();
        /*if (mMovieList == null || mMovieList.isEmpty()) {
            mMovieList = movieList;
            notifyItemRangeChanged(0, mMovieList.size());
        } else {
            new MovieDiffUtil(mMovieList, movieList, new MovieDiffUtilCallback() {
                @Override
                public void onDiffCalculated(DiffUtil.DiffResult diffResult) {
                    diffResult.dispatchUpdatesTo(MovieAdapter.this);
                    mMovieList = movieList;
                }
            }).execute();
        }*/
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
                            mMovieList.get(holder.getAdapterPosition()));
            }
        });

        Movie movie = mMovieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
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

    static class MovieDiffUtil extends AsyncTask<Void, Void, DiffUtil.DiffResult> {

        private List<Movie> mOldList;
        private List<Movie> mNewList;
        private MovieDiffUtilCallback mCallback;

        MovieDiffUtil(List<Movie> oldList, List<Movie> newList, MovieDiffUtilCallback callback) {
            this.mOldList = oldList;
            this.mNewList = newList;
            this.mCallback = callback;
        }

        @Override
        protected DiffUtil.DiffResult doInBackground(Void... voids) {
            return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mOldList.size();
                }

                @Override
                public int getNewListSize() {
                    return mNewList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Movie oldMovie = mOldList.get(oldItemPosition);
                    Movie newMovie = mNewList.get(newItemPosition);
                    return oldMovie.getId() == newMovie.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Movie oldMovie = mOldList.get(oldItemPosition);
                    Movie newMovie = mNewList.get(newItemPosition);
                    return oldMovie.getTitle().equals(newMovie.getTitle());
                }
            });
        }

        @Override
        protected void onPostExecute(DiffUtil.DiffResult diffResult) {
            super.onPostExecute(diffResult);
            mCallback.onDiffCalculated(diffResult);
        }
    }

    public interface MovieDiffUtilCallback {
        void onDiffCalculated(DiffUtil.DiffResult diffResult);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Movie item);
    }

}