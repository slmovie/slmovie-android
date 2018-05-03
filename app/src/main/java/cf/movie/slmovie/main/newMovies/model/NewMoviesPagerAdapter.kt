package cf.movie.slmovie.main.newMovies.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment

/**
 * Created by 包俊 on 2017/7/22.
 */

class NewMoviesPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return BaseMoviesFragment.newInstance(Which.UrlType.LastUpdateMovie)
            1 -> return BaseMoviesFragment.newInstance(Which.UrlType.ActionMovie)
            2 -> return BaseMoviesFragment.newInstance(Which.UrlType.Comedy)
            3 -> return BaseMoviesFragment.newInstance(Which.UrlType.LoveMovie)
            4 -> return BaseMoviesFragment.newInstance(Which.UrlType.ScienceMovie)
            5 -> return BaseMoviesFragment.newInstance(Which.UrlType.HorrorMovie)
            6 -> return BaseMoviesFragment.newInstance(Which.UrlType.DramaMovie)
            7 -> return BaseMoviesFragment.newInstance(Which.UrlType.WarMovie)
            else -> return BaseMoviesFragment.newInstance(Which.UrlType.LastUpdateMovie)
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return Which.LastUpdateMovieStr
            1 -> return Which.ActionMovieStr
            2 -> return Which.ComedyStr
            3 -> return Which.LoveMovieStr
            4 -> return Which.ScienceMovieStr
            5 -> return Which.HorrorMovieStr
            6 -> return Which.DramaMovieStr
            7 -> return Which.WarMovieStr
            else -> return Which.LastUpdateMovieStr
        }
    }

    companion object {
        private val PAGE_COUNT = 8
    }

}
