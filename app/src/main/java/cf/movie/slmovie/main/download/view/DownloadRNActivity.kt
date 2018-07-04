package cf.movie.slmovie.main.download.view

import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadReactPackage
import cf.movie.slmovie.utils.ReactNativeJson
import cf.movie.slmovie.utils.rnUtils.baseRN.view.BaseRNActivity
import com.facebook.react.ReactInstanceManagerBuilder
import com.google.gson.Gson

/**
 * Created by 包俊 on 2018/6/5.
 */
class DownloadRNActivity : BaseRNActivity() {

    private var bean: XLDownloadDBBean? = null

    init {
        title = "下载管理"
    }

    override val moduleName: String
        get() = "DownloadActivity"

    override fun loadFinished() {
        val gson = Gson()
        swipeRefresh(false)
        if (bean != null)
            emit("DownloadBean", ReactNativeJson.convertStringToMap(gson.toJson(bean)))
    }

    override fun setMyReactPackage(builder: ReactInstanceManagerBuilder) {
        builder.addPackage(XLDownloadReactPackage(this))
    }

    override fun initData() {
        super.initData()
        try {
            bean = intent.extras.get("download") as XLDownloadDBBean
        } catch (e: Exception) {

        }
    }

}