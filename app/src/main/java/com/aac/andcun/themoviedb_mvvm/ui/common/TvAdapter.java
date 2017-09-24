package com.aac.andcun.themoviedb_mvvm.ui.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.databinding.ItemTvBinding;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cuneytcarikci on 25/05/2017.
 */

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private List<Tv> tvList;
    private TvAdapter.OnItemClickListener onItemClickListener;

    public TvAdapter() {
        this.tvList = new ArrayList<>();
    }

    public void updateTvList(List<Tv> tvList) {
        this.tvList = tvList;
        notifyDataSetChanged();
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTvBinding binding = ItemTvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final TvViewHolder holder, int position) {
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getAdapterPosition(),
                            tvList.get(holder.getAdapterPosition()));
            }
        });

        Tv movie = tvList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public void setOnItemClickListener(TvAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class TvViewHolder extends RecyclerView.ViewHolder {

        ItemTvBinding binding;

        TvViewHolder(ItemTvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(Tv Tv) {
            binding.setTv(Tv);
            binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, Tv item);
    }

}