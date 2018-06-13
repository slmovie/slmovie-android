package cf.movie.slmovie.main.detail.ui

import cf.movie.slmovie.main.detail.rn.DetailReactPackage
import cf.movie.slmovie.main.download.rn.download.XLDownloadReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.view.BaseRNActivity
import com.facebook.react.ReactInstanceManagerBuilder

/**
 * Created by 包俊 on 2018/6/6.
 */
class DetailRNActivity : BaseRNActivity() {

    private var address = ""

    override val moduleName: String
        get() = "DetailActivity"

    override fun loadFinished() {
    }

    override fun initData() {
        address = intent.getStringExtra("address")
        super.initData()
    }

    private val iDetailView = object : IDetailView {
        /**
         * swipeLayout刷新状态
         *
         * @param fresh
         */
        override fun refresh(fresh: Boolean) {
            swipeRefresh(fresh)
        }
    }

    override fun setMyReactPackage(builder: ReactInstanceManagerBuilder) {
        builder.addPackage(DetailReactPackage(address!!, iDetailView))
        builder.addPackage(XLDownloadReactPackage(this))
    }

}