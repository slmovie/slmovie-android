package cf.movie.slmovie.main.detailOS.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cf.movie.slmovie.main.detailOS.view.Fragment.InfoFragment
import cf.movie.slmovie.main.detailOS.view.Fragment.UrlFragment
import cf.movie.slmovie.main.douban.model.Top250.Top250Bean

/**
 * Created by 包俊 on 2018/5/25.
 */
class DetailOSAdapter(fm: FragmentManager?, var movie: Top250Bean.subject) : FragmentPagerAdapter(fm) {

    private var infoFragment: InfoFragment? = null
    private var urlFragment: UrlFragment? = null

    init {
        infoFragment = InfoFragment.newInstance(movie)
        urlFragment = UrlFragment()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return infoFragment!!
            1 -> return urlFragment!!
            else -> return infoFragment!!
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "详情"
            1 -> return "资源"
            else -> return "详情"
        }
    }
}