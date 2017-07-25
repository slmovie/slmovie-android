package cf.movie.slmovie.main.newMovies.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment;
import cf.movie.slmovie.utils.LogUtils;

import static android.R.attr.id;

/**
 * Created by 包俊 on 2017/7/22.
 */

public class NewMoviesPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 8;

    public NewMoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BaseMoviesFragment.newInstance(Which.UrlType.LastUpdateMovie);
            case 1:
                return BaseMoviesFragment.newInstance(Which.UrlType.ActionMovie);
            case 2:
                return BaseMoviesFragment.newInstance(Which.UrlType.Comedy);
            case 3:
                return BaseMoviesFragment.newInstance(Which.UrlType.LoveMovie);
            case 4:
                return BaseMoviesFragment.newInstance(Which.UrlType.ScienceMovie);
            case 5:
                return BaseMoviesFragment.newInstance(Which.UrlType.HorrorMovie);
            case 6:
                return BaseMoviesFragment.newInstance(Which.UrlType.DramaMovie);
            case 7:
                return BaseMoviesFragment.newInstance(Which.UrlType.WarMovie);
            default:
                return BaseMoviesFragment.newInstance(Which.UrlType.LastUpdateMovie);
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
                return Which.LastUpdateMovieStr;
            case 1:
                return Which.ActionMovieStr;
            case 2:
                return Which.ComedyStr;
            case 3:
                return Which.LoveMovieStr;
            case 4:
                return Which.ScienceMovieStr;
            case 5:
                return Which.HorrorMovieStr;
            case 6:
                return Which.DramaMovieStr;
            case 7:
                return Which.WarMovieStr;
            default:
                return Which.LastUpdateMovieStr;
        }
    }

}
