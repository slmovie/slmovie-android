package cf.movie.slmovie.main.home.ui;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseActivity;
import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment;
import cf.movie.slmovie.main.newMovies.ui.NewMoviesFragment;
import cf.movie.slmovie.main.newMovies.ui.NewTVsFragment;
import cf.movie.slmovie.utils.LogUtils;

/**
 * Created by 包俊 on 2017/7/19.
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private BaseMoviesFragment hotMoviesFragment = null;
    private NewMoviesFragment newMoviesFragment = null;
    private NewTVsFragment newTVsFragment = null;
    private TextView tv_name;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View HeadView = navigationView.getHeaderView(0);
        tv_name = (TextView) HeadView.findViewById(R.id.tv_name);
        LogUtils.e("viewPage", "Main>>>" + tv_name.getId());
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("热门电影");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentManager = getSupportFragmentManager();
        hotMoviesFragment = BaseMoviesFragment.newInstance(Which.UrlType.HotMovie);
        newTVsFragment = NewTVsFragment.newInstance();
        newMoviesFragment = NewMoviesFragment.newInstance();
        fragmentManager.beginTransaction().add(R.id.frameLayout, hotMoviesFragment).add(R.id.frameLayout, newMoviesFragment).add(R.id.frameLayout, newTVsFragment).commitAllowingStateLoss();
        fragmentManager.beginTransaction().hide(newTVsFragment).hide(newMoviesFragment).show(hotMoviesFragment).commitAllowingStateLoss();
        tv_name.setText("11111");
    }

    @Override
    protected void initAction() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.movie_new) {
            getSupportActionBar().setTitle("最新电影");
            fragmentManager.beginTransaction().hide(newTVsFragment).hide(hotMoviesFragment).show(newMoviesFragment).commitAllowingStateLoss();
        } else if (id == R.id.movie_hot) {
            getSupportActionBar().setTitle("热门电影");
            fragmentManager.beginTransaction().hide(newTVsFragment).hide(newMoviesFragment).show(hotMoviesFragment).commitAllowingStateLoss();
        } else if (id == R.id.tv_new) {
            getSupportActionBar().setTitle("最新电视剧");
            fragmentManager.beginTransaction().show(newTVsFragment).hide(newMoviesFragment).hide(hotMoviesFragment).commitAllowingStateLoss();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
