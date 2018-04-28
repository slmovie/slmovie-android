package cf.movie.slmovie.base.BaseMovies.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseFragment;
import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter;
import cf.movie.slmovie.utils.impl.RecyclerItemClickListener;
import cf.movie.slmovie.base.BaseMovies.presenter.BaseMoviesPresenter;
import cf.movie.slmovie.main.detail.ui.DetailActivity;
import cf.movie.slmovie.utils.LogUtils;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesFragment extends BaseFragment implements IBaseMovies {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private BaseMoviesPresenter presenter;
    private Which.UrlType which;
    private BaseMoviesAdapter adapter = null;
    private CoordinatorLayout container;
    private static final String ARG_PARAM1 = "which";
    private static final String ARG_PARAM2 = "adapter";

    public BaseMoviesFragment() {

    }

    public static BaseMoviesFragment newInstance(Which.UrlType which) {
        LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "newInstance");
        BaseMoviesFragment fragment = new BaseMoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, which);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            which = (Which.UrlType) getArguments().getSerializable(ARG_PARAM1);
            adapter = (BaseMoviesAdapter) getArguments().getSerializable(ARG_PARAM2);
        }
        LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "onCreate");
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_base_movies;
    }

    protected void initGui() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler);
        swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeLayout);
        container = (CoordinatorLayout) getView().findViewById(R.id.container);
    }

    protected void initData() {
        LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "initData");
        if (adapter == null || adapter.getItemCount() == 0) {
            LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "重新加载adapter");
            presenter = new BaseMoviesPresenter(getActivity(), this);
            swipeLayout.setColorSchemeColors(Color.BLUE,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.RED);
            swipeLayout.setDistanceToTriggerSync(300);
            swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
            swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    presenter.getMovies(which);
                }
            });
            swipeLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(true);
                    presenter.getMovies(which);
                }
            });
        } else {
            LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "加载已有adapter");
            setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "onResume");
        super.onResume();
    }

    protected void initAction() {
    }

    @Override
    public void setAdapter(final BaseMoviesAdapter adapter) {
        this.adapter = adapter;
        swipeLayout.setRefreshing(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("address", adapter.getMovies(position).getAddress());
                intent.putExtra("name", adapter.getMovies(position).getName());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void refreshOver(BaseMoviesAdapter adapter) {
        this.adapter = adapter;
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void reqError(String msg) {
        swipeLayout.setRefreshing(false);
        Snackbar.make(container, msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("重新加载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeLayout.setRefreshing(true);
                                presenter.getMovies(which);
                            }
                        });
                    }
                })
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show();
    }
}
