package cf.movie.slmovie.base.BaseMovies.model

import android.content.Context
import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean
import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.event.AdapterEvent
import cf.movie.slmovie.server.Constant
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import com.android.volley.Response

/**
 * Created by 包俊 on 2017/7/21.
 */

class BaseMoviesModel(private val context: Context) {
    private val event: AdapterEvent
    private var adapter: BaseMoviesAdapter? = null

    init {
        event = AdapterEvent(true, "")
    }

    /**
     * 请求电影数据
     *
     * @param which
     */
    fun getMovies(which: Which.UrlType) {
        val url = Which.getUrl(which)
        event.which = which
        val jsonRequest = JsonObjectRequest(Constant.WEBROOT + url, null, Response.Listener<JSONObject> { response ->
            val gson = Gson()
            val bean = gson.fromJson(response.toString(), BaseMoviesBean::class.java)
            if (bean.status!!.code == "1") {
                event.isStatus = true
                if (adapter == null) {
                    adapter = BaseMoviesAdapter(context, bean.movies!!)
                    event.adapter = adapter
                    event.isRefresh = false
                    EventBus.getDefault().post(event)
                } else {
                    adapter!!.refresh(bean.movies!!)
                    event.adapter = adapter
                    event.isRefresh = true
                    EventBus.getDefault().post(event)
                }
            } else {
                event.isStatus = false
                event.message = bean.status!!.error
                EventBus.getDefault().post(event)
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            event.isStatus = false
            event.message = error.message
            EventBus.getDefault().post(event)
        })
        Volley.newRequestQueue(context).add(jsonRequest)
    }
}
