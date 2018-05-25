package cf.movie.slmovie.server

import android.content.Context
import android.text.TextUtils
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

/**
 * Created by 包俊 on 2018/5/24.
 */
class APPRestClient {

    companion object {
        fun <T> get(context: Context, url: String, clazz: Class<*>, callBack: HttpCallBack<T>) {
            var jsonRequest = StringRequest(url,
                    Response.Listener<String> { response ->
                        if (clazz.name == "java.lang.String") {
                            callBack.onSuccess(response as T)
                        } else {
                            val gson = Gson()
                            var json = JSONObject(response)
                            var status = json.optString("status")
                            if (!TextUtils.isEmpty(status)) {
                                var statusJson = JSONObject(status)
                                var code = statusJson.optString("code")
                                if (!TextUtils.isEmpty(code) && !code.equals("0")) {
                                    var movie = json.optString("movies")
                                    if (!TextUtils.isEmpty(movie) && !"0".equals(movie)) {
                                        val t = gson.fromJson<T>(response, clazz)
                                        callBack.onSuccess(t)
                                    } else {
                                        callBack.onFailed("0", "无数据")
                                    }
                                } else {
                                    callBack.onFailed("-1", "请求失败请重试")
                                }
                            } else {
                                callBack.onFailed("-1", "请求失败请重试")
                            }
                        }
                    },
                    Response.ErrorListener { error ->
                        callBack.onFailed("-1", "请求失败请重试")
                    })
            Volley.newRequestQueue(context).add(jsonRequest)
        }
    }

    interface HttpCallBack<T> {
        fun onSuccess(bean: T)
        fun onFailed(errorCode: String, errorMsg: String)
    }
}