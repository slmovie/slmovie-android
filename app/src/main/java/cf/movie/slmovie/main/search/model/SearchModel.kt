package cf.movie.slmovie.main.search.model

import android.app.Activity

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

import cf.movie.slmovie.main.search.bean.SearchAdapter
import cf.movie.slmovie.main.search.bean.SearchResult
import cf.movie.slmovie.main.search.event.SearchEvent
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode

/**
 * Created by 包俊 on 2017/8/12.
 */

class SearchModel(private val context: Activity) {
    private val event: SearchEvent

    init {
        event = SearchEvent(true, "")
    }

    fun getMovies(search: String) {
        val url = Constant.WEBROOT + HtmlCode.Search + search
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, Response.Listener { response ->
            val gson = Gson()
            val bean = gson.fromJson(response.toString(), SearchResult::class.java)
            if (bean.status!!.code == "1") {
                val adapter = SearchAdapter(bean.movies)
                event.isStatus = true
                event.adapter = adapter
                EventBus.getDefault().post(event)
            } else {
                event.isStatus = false
                EventBus.getDefault().post(event)
            }
        }, Response.ErrorListener { error ->
            event.isStatus = false
            event.message = error.message
            EventBus.getDefault().post(event)
        })
        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }

}
