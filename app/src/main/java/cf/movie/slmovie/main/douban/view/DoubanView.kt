package cf.movie.slmovie.main.douban.view

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.detailOS.view.DetailOSActivity
import cf.movie.slmovie.main.douban.model.Top250.Top250Adapter
import cf.movie.slmovie.main.douban.presenter.DoubanPresenter
import cf.movie.slmovie.main.douban.presenter.DoubanPresenterImpl
import cf.movie.slmovie.utils.impl.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_douban_top250.*

/**
 * Created by 包俊 on 2018/5/21.
 */
class DoubanView : BaseFragment(), DoubanPresenterImpl {

    private var presenter: DoubanPresenter? = null

    override val contentLayout: Int
        get() = R.layout.activity_douban_top250

    override fun initGui() {
    }

    override fun initData() {
        val linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        swipe_target!!.layoutManager = linearLayoutManager
        swipe_target?.addOnItemTouchListener(RecyclerItemClickListener(activity!!, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onLongItemClick(view: View?, position: Int) {
            }

            override fun onItemClick(view: View, position: Int) {
                var adapter = swipe_target?.adapter as Top250Adapter
                var movie = adapter.getMovies(position)
                var intent = Intent(activity, DetailOSActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }
        }))

        presenter = DoubanPresenter(activity!!, this)
        presenter?.getTop250(true)
    }

    override fun initAction() {
        swipeToLoadLayout!!.setOnRefreshListener({
            presenter!!.getTop250(true)
        })
        swipeToLoadLayout!!.setOnLoadMoreListener({
            presenter!!.getTop250(false)
        })
    }

    override fun setAdapter(adapter: Top250Adapter?, position: Int) {
        if (adapter != null) {
            swipe_target?.adapter = adapter
            swipeToLoadLayout!!.setRefreshing(false)
        } else {
            swipe_target?.smoothScrollToPosition(position)
            swipeToLoadLayout!!.setLoadingMore(false)
        }
    }

    override fun reqError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

}
