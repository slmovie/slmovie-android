package cf.movie.slmovie.main.home.model

import android.content.Context
import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.server.APPRestClient
import cf.movie.slmovie.server.APPRestClient.Companion.get
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode
import cf.movie.slmovie.utils.Version
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

/**
 * Created by 包俊 on 2018/5/16.
 */
class CheckUpdateModel(private val context: Context, private val impl: BaseReqListener<CheckUpdateBean>) {
    fun start() {
        val url = Constant.WEBROOT + HtmlCode.APPUpdate + Version.getVersion(context)
        get<CheckUpdateBean>(context, url, CheckUpdateBean::class.java, object : APPRestClient.HttpCallBack<CheckUpdateBean> {
            override fun onSuccess(bean: CheckUpdateBean) {
                impl.success(bean)
            }

            override fun onFailed(errorCode: String, errorMsg: String) {
            }

        })
    }

}