package com.aac.andcun.themoviedb_mvvm.ui.detail;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.databinding.ItemPeopleBinding;
import com.aac.andcun.themoviedb_mvvm.vo.People;

import java.util.Collections;
import java.util.List;

/**
 * Created by cuneytcarikci on 27/09/2017.
 */

public class MoviePeopleAdapter<T extends People> extends RecyclerView.Adapter<MoviePeopleAdapter.MoviePeopleViewHolder> {

    private List<T> peoples;
    private OnItemClickListener<T> onItemClickListener;

    public MoviePeopleAdapter() {
        this.peoples = Collections.emptyList();
    }

    public void setPeoples(List<T> peopleList) {
        this.peoples = peopleList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MoviePeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPeopleBinding binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoviePeopleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MoviePeopleViewHolder holder, int position) {
        final T people = peoples.get(holder.getAdapterPosition());
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), people);
            }
        });
        holder.bind(people);

    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    static class MoviePeopleViewHolder extends RecyclerView.ViewHolder {
        ItemPeopleBinding binding;

        public MoviePeopleViewHolder(ItemPeopleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(People people) {
            binding.setPeople(people);
            binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item);
    }
}
