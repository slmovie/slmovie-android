package cf.movie.slmovie.main.detailOS.view

import android.support.design.widget.TabLayout
import android.view.MenuItem
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.detail.model.bean.MovieDetailBean
import cf.movie.slmovie.main.detailOS.model.DetailOSAdapter
import cf.movie.slmovie.main.detailOS.model.bean.DoubanDetailBean
import cf.movie.slmovie.main.detailOS.model.event.DetailOsEvent
import cf.movie.slmovie.main.detailOS.model.event.DoubanDetailOsEvent
import cf.movie.slmovie.main.detailOS.presenter.DetailOSActivityPresenter
import cf.movie.slmovie.main.douban.model.Top250.Top250Bean
import kotlinx.android.synthetic.main.activity_detail_os.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by 包俊 on 2018/5/23.
 */
class DetailOSActivity : BaseActivity() {

    private var movie: Top250Bean.subject? = null
    private var presenter: DetailOSActivityPresenter? = null

    override val contentLayout: Int
        get() = R.layout.activity_detail_os

    override fun initGui() {

    }

    override fun initData() {
        presenter = DetailOSActivityPresenter(this)
        //获取传递过来的豆瓣电影信息
        movie = intent.getSerializableExtra("movie") as Top250Bean.subject

        setSupportActionBar(toolbar)
        supportActionBar!!.title = movie!!.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewPage.adapter = DetailOSAdapter(this.supportFragmentManager, movie!!)
        tablet!!.setupWithViewPager(viewPage)
        tablet!!.tabMode = TabLayout.GRAVITY_CENTER

        presenter!!.getDetail(movie?.id!!, impl)
    }

    var impl = object : BaseReqListener<MovieDetailBean> {
        override fun success(result: MovieDetailBean) {
            EventBus.getDefault().post(DetailOsEvent(result, true, ""))
        }

        override fun failed(errorCode: String?, errorMsg: String?) {
            if (errorCode.equals("0")) {
                EventBus.getDefault().post(DetailOsEvent(null, false, ""))
                presenter!!.getDouban(movie?.id!!, object : BaseReqListener<DoubanDetailBean> {
                    override fun success(result: DoubanDetailBean) {
                        EventBus.getDefault().post(DoubanDetailOsEvent(result, true, ""))
                    }

                    override fun failed(errorCode: String?, errorMsg: String?) {

                    }

                })
            }
        }
    }

    override fun initAction() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}