package cf.movie.slmovie.main.newMovies.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.newMovies.model.NewTVsPagerAdapter
import cf.movie.slmovie.utils.LogUtils

/**
 * Created by 包俊 on 2017/7/22.
 */

class NewTVsFragment : BaseFragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override val contentLayout: Int
        get() = R.layout.fragment_new_tvs

    override fun initGui() {
        tabLayout = view!!.findViewById(R.id.tablet) as TabLayout
        viewPager = view!!.findViewById(R.id.viewPage) as ViewPager
        val id = viewPager!!.id
        LogUtils.e("viewPage", "tv>>>>>$id")
    }

    override fun initData() {
        viewPager!!.adapter = NewTVsPagerAdapter(activity?.supportFragmentManager)
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun initAction() {

    }

    companion object {

        fun newInstance(): NewTVsFragment {
            return NewTVsFragment()
        }
    }
}
