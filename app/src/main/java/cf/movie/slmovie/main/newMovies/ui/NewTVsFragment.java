package cf.movie.slmovie.main.newMovies.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseFragment;
import cf.movie.slmovie.main.newMovies.model.NewTVsPagerAdapter;
import cf.movie.slmovie.utils.LogUtils;

/**
 * Created by 包俊 on 2017/7/22.
 */

public class NewTVsFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static NewTVsFragment newInstance() {
        NewTVsFragment fragment = new NewTVsFragment();
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_new_tvs;
    }

    @Override
    protected void initGui() {
        tabLayout = (TabLayout) view.findViewById(R.id.tablet);
        viewPager = (ViewPager) view.findViewById(R.id.viewPage);
        int id = viewPager.getId();
        LogUtils.e("viewPage", "tv>>>>>" + id + "");
    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new NewTVsPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initAction() {

    }
}
