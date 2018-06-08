package cf.movie.slmovie.main.download.view

import cf.movie.slmovie.main.download.rn.download.XLDownloadReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.view.BaseRNActivity
import com.facebook.react.ReactInstanceManagerBuilder

/**
 * Created by 包俊 on 2018/6/5.
 */
class DownloadActivity : BaseRNActivity() {
    override val moduleName: String
        get() = "DownloadActivity"

    override fun setMyReactPackage(builder: ReactInstanceManagerBuilder) {
        builder.addPackage(XLDownloadReactPackage(this))
    }

}