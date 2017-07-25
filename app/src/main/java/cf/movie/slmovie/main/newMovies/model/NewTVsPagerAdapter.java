package cf.movie.slmovie.main.newMovies.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment;
import cf.movie.slmovie.utils.LogUtils;

/**
 * Created by 包俊 on 2017/7/22.
 */

public class NewTVsPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 5;

    public NewTVsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BaseMoviesFragment.newInstance(Which.UrlType.NewTVs);
            case 1:
                return BaseMoviesFragment.newInstance(Which.UrlType.ChinaTV);
            case 2:
                return BaseMoviesFragment.newInstance(Which.UrlType.HongTaiTV);
            case 3:
                return BaseMoviesFragment.newInstance(Which.UrlType.WestenTV);
            case 4:
                return BaseMoviesFragment.newInstance(Which.UrlType.JapanKoreaTV);
            default:
                return BaseMoviesFragment.newInstance(Which.UrlType.NewTVs);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Which.NewTVStr;
            case 1:
                return Which.ChinaTVStr;
            case 2:
                return Which.HongTaiTVStr;
            case 3:
                return Which.WestenTVStr;
            case 4:
                return Which.JapanKoreaTVStr;
            default:
                return Which.NewTVStr;
        }
    }

}
