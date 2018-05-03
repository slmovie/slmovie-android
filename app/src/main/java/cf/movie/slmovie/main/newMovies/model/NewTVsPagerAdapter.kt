package cf.movie.slmovie.main.newMovies.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment

/**
 * Created by 包俊 on 2017/7/22.
 */

class NewTVsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return BaseMoviesFragment.newInstance(Which.UrlType.NewTVs)
            1 -> return BaseMoviesFragment.newInstance(Which.UrlType.ChinaTV)
            2 -> return BaseMoviesFragment.newInstance(Which.UrlType.HongTaiTV)
            3 -> return BaseMoviesFragment.newInstance(Which.UrlType.WestenTV)
            4 -> return BaseMoviesFragment.newInstance(Which.UrlType.JapanKoreaTV)
            else -> return BaseMoviesFragment.newInstance(Which.UrlType.NewTVs)
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return Which.NewTVStr
            1 -> return Which.ChinaTVStr
            2 -> return Which.HongTaiTVStr
            3 -> return Which.WestenTVStr
            4 -> return Which.JapanKoreaTVStr
            else -> return Which.NewTVStr
        }
    }

    companion object {
        private val PAGE_COUNT = 5
    }

}
