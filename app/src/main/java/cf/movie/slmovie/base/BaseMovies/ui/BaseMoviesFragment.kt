package cf.movie.slmovie.base.BaseMovies.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter
import cf.movie.slmovie.utils.impl.RecyclerItemClickListener
import cf.movie.slmovie.base.BaseMovies.presenter.BaseMoviesPresenter
import cf.movie.slmovie.main.detail.ui.DetailActivity
import cf.movie.slmovie.utils.LogUtils

/**
 * Created by 包俊 on 2017/7/21.
 */

class BaseMoviesFragment : BaseFragment(), IBaseMovies {

    private var recyclerView: RecyclerView? = null
    private var swipeLayout: SwipeRefreshLayout? = null
    private var presenter: BaseMoviesPresenter? = null
    private var which: Which.UrlType? = null
    private var adapter: BaseMoviesAdapter? = null
    private var container: CoordinatorLayout? = null

    override val contentLayout: Int
        get() = R.layout.fragment_base_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            which = arguments?.getSerializable(ARG_PARAM1) as Which.UrlType
        }
        LogUtils.e("BaseMoviesFragment", which!!.toString() + ">>>>>>" + "onCreate")
    }

    override fun initGui() {
        recyclerView = view!!.findViewById(R.id.recycler) as RecyclerView
        swipeLayout = view!!.findViewById(R.id.swipeLayout) as SwipeRefreshLayout
        container = view!!.findViewById(R.id.container) as CoordinatorLayout
    }

    override fun initData() {
        LogUtils.e("BaseMoviesFragment", which!!.toString() + ">>>>>>" + "initData")
        if (adapter == null || adapter!!.itemCount == 0) {
            LogUtils.e("BaseMoviesFragment", which!!.toString() + ">>>>>>" + "重新加载adapter")
            presenter = BaseMoviesPresenter(activity!!, this)
            swipeLayout!!.setColorSchemeColors(Color.BLUE,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.RED)
            swipeLayout!!.setDistanceToTriggerSync(300)
            swipeLayout!!.setProgressBackgroundColorSchemeColor(Color.WHITE)
            swipeLayout!!.setSize(SwipeRefreshLayout.DEFAULT)
            swipeLayout!!.setOnRefreshListener { presenter!!.getMovies(which!!) }
            swipeLayout!!.post {
                swipeLayout!!.isRefreshing = true
                presenter!!.getMovies(which!!)
            }
        } else {
            LogUtils.e("BaseMoviesFragment", which!!.toString() + ">>>>>>" + "加载已有adapter")
            setAdapter(adapter!!)
        }
    }

    override fun onResume() {
        LogUtils.e("BaseMoviesFragment", which!!.toString() + ">>>>>>" + "onResume")
        super.onResume()
    }

    override fun initAction() {}

    override fun setAdapter(adapter: BaseMoviesAdapter) {
        this.adapter = adapter
        swipeLayout!!.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.adapter = adapter
        recyclerView!!.addOnItemTouchListener(RecyclerItemClickListener(activity!!, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("address", adapter.getMovies(position).address)
                intent.putExtra("name", adapter.getMovies(position).name)
                startActivity(intent)
            }

            override fun onLongItemClick(view: View?, position: Int) {

            }
        }))
    }

    override fun refreshOver(adapter: BaseMoviesAdapter) {
        this.adapter = adapter
        swipeLayout!!.isRefreshing = false
    }

    override fun reqError(msg: String) {
        swipeLayout!!.isRefreshing = false
        Snackbar.make(container!!, msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("重新加载") {
                    swipeLayout!!.post {
                        swipeLayout!!.isRefreshing = true
                        presenter!!.getMovies(which!!)
                    }
                }
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show()
    }

    companion object {
        private val ARG_PARAM1 = "which"

        fun newInstance(which: Which.UrlType): BaseMoviesFragment {
            LogUtils.e("BaseMoviesFragment", which.toString() + ">>>>>>" + "newInstance")
            val fragment = BaseMoviesFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, which)
            fragment.arguments = args
            return fragment
        }
    }
}
