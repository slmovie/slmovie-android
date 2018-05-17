package cf.movie.slmovie.main.home.model

import android.content.Context
import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean
import cf.movie.slmovie.base.BaseReqListener
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
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            val gson = Gson()
            val bean = gson.fromJson(response.toString(), CheckUpdateBean::class.java)
            impl.success(bean)
        }, Response.ErrorListener { error ->
            impl.failed(error.networkResponse.statusCode.toString(), error.message)
        })
        Volley.newRequestQueue(context).add(stringRequest)
    }

}