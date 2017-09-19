package com.aac.andcun.themoviedb_mvvm.ui.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentTvPageBinding;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;
import com.aac.andcun.themoviedb_mvvm.ui.common.TvAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.common.decoration.GridSpacingItemDecoration;
import com.aac.andcun.themoviedb_mvvm.util.RxTransformer;
import com.aac.andcun.themoviedb_mvvm.vo.ResultTv;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvPageFragment extends BaseFragment<FragmentTvPageBinding> {

    private static final String ARG_POSITION = "position";

    @Inject
    TvRepository repository;

    TvAdapter adapter;

    int position;

    public static TvPageFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        TvPageFragment fragment = new TvPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Observable<List<ResultTv>> observable = null;

        switch (position) {
            case 0:
                observable = repository.getPopularTvs(1);
                break;

            case 1:
                observable = repository.getOnTheAirTvs(1);
                break;

        }


        observable
                .compose(RxTransformer.<List<ResultTv>>applyIOSchedulers())
                .subscribe(new Consumer<List<ResultTv>>() {
                    @Override
                    public void accept(List<ResultTv> ResultTvs) throws Exception {
                        Log.e("MoviePageFragment", "Count: " + ResultTvs.size());
                        adapter.addtvList(ResultTvs);
                        binding.executePendingBindings();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_tv_page;
    }

    @Override
    protected void resolveDaggerDependency() {

    }

    @Override
    protected void setUpUiComponents() {
        adapter = new TvAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.movie_tv_item_margin)));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ResultTv item) {
                Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
