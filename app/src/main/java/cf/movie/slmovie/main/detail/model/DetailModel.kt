package cf.movie.slmovie.main.detail.model

import android.content.Context
import cf.movie.slmovie.server.APPRestClient
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by 包俊 on 2017/8/7.
 */

class DetailModel(private val context: Context, private val iDetailModel: IDetailModel) {

    /**
     * 获取电影信息
     *
     * @param address
     */
    fun getMovie(address: String) {
        val url = Constant.WEBROOT + HtmlCode.Details + address
        APPRestClient.get<String>(context, url, String::class.java, object : APPRestClient.HttpCallBack<String> {
            override fun onSuccess(bean: String) {
                iDetailModel.callBack(bean)
            }

            override fun onFailed(errorCode: String, errorMsg: String) {
                try {
                    val status = JSONObject()
                    status.put("code", "0")
                    status.put("error", "请求失败，请重试")
                    val `object` = JSONObject()
                    `object`.put("status", status)
                    iDetailModel.callBack(`object`.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        })
    }

}
