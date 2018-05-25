package cf.movie.slmovie.main.detailOS.model

import android.content.Context
import android.text.TextUtils
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.detailOS.model.bean.DoubanDetailBean
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

/**
 * Created by 包俊 on 2018/5/25.
 */
class DoubanQueryModel {
    fun start(context: Context, id: String, impl: BaseReqListener<DoubanDetailBean>) {
        val jsonRequest = JsonObjectRequest("http://api.douban.com/v2/movie/subject/" + id, null, Response.Listener<JSONObject> { response ->
            var douban = response.optString("id")
            if (!TextUtils.isEmpty(douban) && douban.equals(id)) {
                var gson = Gson()
                var bean = gson.fromJson(response.toString(), DoubanDetailBean::class.java)
                impl.success(bean)
            } else {
                impl.failed(null, "请求失败，请重试")
            }
        }, Response.ErrorListener { error ->
            impl.failed(null, "请求失败，请重试")
        })
        Volley.newRequestQueue(context).add(jsonRequest)
    }
}