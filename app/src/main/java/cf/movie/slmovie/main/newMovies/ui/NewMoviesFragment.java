package cf.movie.slmovie.main.newMovies.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseFragment;
import cf.movie.slmovie.main.newMovies.model.NewMoviesPagerAdapter;

/**
 * Created by 包俊 on 2017/7/22.
 */

public class NewMoviesFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static NewMoviesFragment newInstance() {
        NewMoviesFragment fragment = new NewMoviesFragment();
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_new_movies;
    }

    @Override
    protected void initGui() {
        tabLayout = (TabLayout) view.findViewById(R.id.tablet);
        viewPager = (ViewPager) view.findViewById(R.id.viewPage);
    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new NewMoviesPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode (TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initAction() {

    }
}
