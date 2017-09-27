package com.aac.andcun.themoviedb_mvvm.ui.tv;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentTvPageBinding;
import com.aac.andcun.themoviedb_mvvm.repository.NextPageHandler;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;
import com.aac.andcun.themoviedb_mvvm.ui.common.TvAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.common.decoration.GridSpacingItemDecoration;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvPageFragment extends BaseFragment<FragmentTvPageBinding> {

    private static final String ARG_TV_LIST_TYPE_ORDINAL = TvPageFragment.class.getSimpleName() + ".arg_tv_list_type_ordinal";

    @Inject
    TvRepository repository;

    TvAdapter adapter;
    NextPageHandler nextPageHandler;
    TvRepository.TvListType tvListType;

    public static TvPageFragment newInstance(TvRepository.TvListType tvListType) {
        Bundle args = new Bundle();
        args.putInt(ARG_TV_LIST_TYPE_ORDINAL, tvListType.ordinal());
        TvPageFragment fragment = new TvPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvListType = TvRepository.TvListType.values()[getArguments().getInt(ARG_TV_LIST_TYPE_ORDINAL, 0)];
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecyclerView();
        setUpNextPageHandler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tv_page;
    }

    private void setUpRecyclerView() {
        LiveData<Resource<List<Tv>>> resourceLiveData = repository.loadTvs(tvListType);

        resourceLiveData.observe(this, new Observer<Resource<List<Tv>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Tv>> listResource) {
                //aktif olmayan pager sayfasında da her yenilemede bu işlem tekrarlanıyor!
                if (listResource != null && listResource.data != null) {
                    adapter.updateTvList(listResource.data);
                    binding.executePendingBindings();
                }
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisiblePosition == (adapter.getItemCount() - 1))
                    nextPageHandler.loadNextPage();
            }
        });

        adapter = new TvAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.movie_tv_item_margin)));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Tv item) {
                Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpNextPageHandler() {
        nextPageHandler = new NextPageHandler() {
            @Override
            public LiveData<Resource<Boolean>> createNextPageCall() {
                return repository.fetchNextPage(tvListType);
            }
        };

        nextPageHandler.getLoadMoreState().observe(this, new Observer<NextPageHandler.LoadMoreState>() {
            @Override
            public void onChanged(@Nullable NextPageHandler.LoadMoreState loadMoreState) {
                if (loadMoreState == null)
                    binding.setLoadingMore(false);
                else {
                    binding.setLoadingMore(loadMoreState.isRunning());
                    String error = loadMoreState.getErrorMessage();
                    if (error != null)
                        Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}