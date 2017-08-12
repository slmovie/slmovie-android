package cf.movie.slmovie.main.search.ui;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseActivity;

import static android.R.attr.button;

public class SearchResultActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("搜索");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initAction() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("请输入电影");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchResultActivity.this, query, Toast.LENGTH_LONG).show();
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
}
