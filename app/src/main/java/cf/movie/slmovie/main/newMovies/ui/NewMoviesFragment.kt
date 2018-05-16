package cf.movie.slmovie.main.newMovies.ui

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.newMovies.model.NewMoviesPagerAdapter
import cf.movie.slmovie.utils.LogUtils

/**
 * Created by 包俊 on 2017/7/22.
 */

class NewMoviesFragment : BaseFragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override val contentLayout: Int
        get() = R.layout.fragment_new_movies

    override fun initGui() {
        tabLayout = view!!.findViewById(R.id.tablet) as TabLayout
        viewPager = view!!.findViewById(R.id.viewpage) as ViewPager
        val id = viewPager!!.id
        LogUtils.e("viewPage", "movie>>>>>$id")
    }

    override fun initData() {
        viewPager!!.adapter = NewMoviesPagerAdapter(activity!!.supportFragmentManager)
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun initAction() {

    }

    companion object {

        fun newInstance(): NewMoviesFragment {
            val fragment = NewMoviesFragment()
            return fragment
        }
    }
}
