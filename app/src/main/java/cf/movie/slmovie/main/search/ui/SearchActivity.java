package cf.movie.slmovie.main.search.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseActivity;
import cf.movie.slmovie.main.detail.ui.DetailActivity;
import cf.movie.slmovie.main.search.bean.SearchAdapter;
import cf.movie.slmovie.main.search.presenter.SearchPresenter;
import cf.movie.slmovie.utils.impl.RecyclerItemClickListener;

public class SearchActivity extends BaseActivity implements ISearchActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SearchPresenter presenter;
    private SwipeRefreshLayout swipeLayout;
    private SearchAdapter adapter = null;
    private SearchView searchView;
    private CoordinatorLayout container;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        container = (CoordinatorLayout) findViewById(R.id.container);
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("搜索");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new SearchPresenter(this, this);
        swipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeLayout.setEnabled(false);
    }

    @Override
    protected void initAction() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("请输入电影");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getMovies(query);
                getSupportActionBar().setTitle(query);
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setAdapter(final SearchAdapter adapter) {
        this.adapter = adapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("address", adapter.getMovies(position).getId());
                intent.putExtra("name", adapter.getMovies(position).getName());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void reqError(String msg) {
        Snackbar.make(container, msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("重新加载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeLayout.setRefreshing(true);
                                presenter.getMovies(getSupportActionBar().getTitle().toString());
                            }
                        });
                    }
                })
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show();
    }

    @Override
    public void swipe(boolean refresh) {
        swipeLayout.setRefreshing(refresh);
    }
}
