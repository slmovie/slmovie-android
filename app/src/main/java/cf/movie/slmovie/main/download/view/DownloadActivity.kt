package cf.movie.slmovie.main.download.view

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.main.download.presenter.DownloadPresnter
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import kotlinx.android.synthetic.main.activity_download.*

/**
 * Created by 包俊 on 2018/6/5.
 */
class DownloadActivity : BaseActivity(), DefaultHardwareBackBtnHandler {

    private var presenter: DownloadPresnter? = null

    override val contentLayout: Int
        get() = R.layout.activity_download

    override fun initGui() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "下载"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        swipeLayout!!.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED)
        swipeLayout!!.setProgressBackgroundColorSchemeColor(Color.WHITE)
        swipeLayout!!.setSize(SwipeRefreshLayout.DEFAULT)
        swipeLayout!!.isEnabled = false
        swipeLayout!!.isRefreshing = true
    }

    override fun initAction() {
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }
}