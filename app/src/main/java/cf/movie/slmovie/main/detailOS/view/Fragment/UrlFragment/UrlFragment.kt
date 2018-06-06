package cf.movie.slmovie.main.detailOS.view.Fragment.UrlFragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.detailOS.model.event.DetailOsEvent
import cf.movie.slmovie.main.search.ui.SearchActivity
import cf.movie.slmovie.utils.SpanTextClick
import kotlinx.android.synthetic.main.fragment_detailos_download.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 包俊 on 2018/5/25.
 */
class UrlFragment : BaseFragment() {
    override val contentLayout: Int
        get() = R.layout.fragment_detailos_download

    override fun initGui() {
    }

    override fun initData() {

    }

    override fun initAction() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(result: DetailOsEvent) {
        if (result.isStatus) {
            val linearLayoutManager = LinearLayoutManager(activity!!)
            linearLayoutManager.orientation = OrientationHelper.VERTICAL
            recycler.layoutManager = linearLayoutManager
            var adapter = UrlsAdapter(activity!!, result.movie?.movies?.files!!)
            recycler.adapter = adapter
            recycler.visibility = View.VISIBLE
        } else {
            SpanTextClick.setSpan(tv_search, "点我去搜索", {
                var intent = Intent(activity, SearchActivity::class.java)
                activity!!.startActivity(intent)
            })
            tv_search.visibility = View.VISIBLE
        }
    }

}