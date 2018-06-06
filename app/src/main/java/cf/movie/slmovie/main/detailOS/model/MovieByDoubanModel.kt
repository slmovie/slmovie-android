package cf.movie.slmovie.main.detailOS.model

import android.content.Context
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.detail.model.bean.MovieDetailBean
import cf.movie.slmovie.server.APPRestClient
import cf.movie.slmovie.server.APPRestClient.Companion.get
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode

/**
 * Created by 包俊 on 2018/5/24.
 */

class MovieByDoubanModel(var context: Context) {
    fun start(id: String, impl: BaseReqListener<MovieDetailBean>) {
        get<MovieDetailBean>(
                context,
                Constant.WEBROOT + HtmlCode.DouBanID + id, MovieDetailBean::class.java,
                object : APPRestClient.HttpCallBack<MovieDetailBean> {
                    override fun onSuccess(bean: MovieDetailBean) {
                        impl.success(bean)
                    }

                    override fun onFailed(errorCode: String, errorMsg: String) {
                        impl.failed(errorCode, errorMsg)
                    }
                }
        )
    }
}
