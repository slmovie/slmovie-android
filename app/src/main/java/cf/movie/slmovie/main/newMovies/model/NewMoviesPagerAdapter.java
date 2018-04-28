package cf.movie.slmovie.main.newMovies.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment;

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
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.LastUpdateMovie);
            case 1:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.ActionMovie);
            case 2:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.Comedy);
            case 3:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.LoveMovie);
            case 4:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.ScienceMovie);
            case 5:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.HorrorMovie);
            case 6:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.DramaMovie);
            case 7:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.WarMovie);
            default:
                return BaseMoviesFragment.Companion.newInstance(Which.UrlType.LastUpdateMovie);
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
                return Which.INSTANCE.getLastUpdateMovieStr();
            case 1:
                return Which.INSTANCE.getActionMovieStr();
            case 2:
                return Which.INSTANCE.getComedyStr();
            case 3:
                return Which.INSTANCE.getLoveMovieStr();
            case 4:
                return Which.INSTANCE.getScienceMovieStr();
            case 5:
                return Which.INSTANCE.getHorrorMovieStr();
            case 6:
                return Which.INSTANCE.getDramaMovieStr();
            case 7:
                return Which.INSTANCE.getWarMovieStr();
            default:
                return Which.INSTANCE.getLastUpdateMovieStr();
        }
    }

}
