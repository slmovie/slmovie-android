package cf.movie.slmovie.main.douban.model.Top250

import android.content.Context
import cf.movie.slmovie.base.BaseReqListener
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

/**
 * Created by 包俊 on 2018/5/21.
 */
class Top250Model {
    fun start(context: Context, start: Int, impl: BaseReqListener<Top250Bean>) {
        val jsonRequest = JsonObjectRequest("http://api.douban.com/v2/movie/top250?start=" + start, null, Response.Listener<JSONObject> { response ->
            var gson = Gson()
            var bean = gson.fromJson(response.toString(), Top250Bean::class.java)
            impl.success(bean)
        }, Response.ErrorListener { error ->
            impl.failed(null, "请求失败，请重试")
        })
        Volley.newRequestQueue(context).add(jsonRequest)
    }
}