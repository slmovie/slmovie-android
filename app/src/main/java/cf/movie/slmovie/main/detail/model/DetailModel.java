package cf.movie.slmovie.main.detail.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cf.movie.slmovie.server.Constant;
import cf.movie.slmovie.server.HtmlCode;
import cf.movie.slmovie.utils.AssetsUtils;
import cf.movie.slmovie.utils.ZipUtils;

/**
 * Created by 包俊 on 2017/8/7.
 */

public class DetailModel {

    private IDetailModel iDetailModel;
    private Context context;

    public DetailModel(Context context, IDetailModel iDetailModel) {
        this.iDetailModel = iDetailModel;
        this.context = context;
    }

    /**
     * 获取电影信息
     *
     * @param address
     */
    public void getMovie(String address) {
        String url = Constant.WEBROOT + HtmlCode.Details + address;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                iDetailModel.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject status = new JSONObject();
                    status.put("code", "0");
                    status.put("error", "请求失败，请重试");
                    JSONObject object = new JSONObject();
                    object.put("status", status);
                    iDetailModel.callBack(object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Volley.newRequestQueue(context).add(stringRequest);
    }

}
