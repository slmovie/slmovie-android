package cf.movie.slmovie.main.newMovies.ui

import android.support.design.widget.TabLayout
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.newMovies.model.NewTVsPagerAdapter
import kotlinx.android.synthetic.main.fragment_new_tvs.*

/**
 * Created by 包俊 on 2017/7/22.
 */

class NewTVsFragment : BaseFragment() {

    override val contentLayout: Int
        get() = R.layout.fragment_new_tvs

    override fun initGui() {
    }

    override fun initData() {
        viewPage!!.adapter = NewTVsPagerAdapter(activity?.supportFragmentManager)
        tablet!!.setupWithViewPager(viewPage)
        tablet!!.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun initAction() {

    }

    companion object {

        fun newInstance(): NewTVsFragment {
            return NewTVsFragment()
        }
    }
}
