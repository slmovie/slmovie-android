package cf.movie.slmovie.main.detail.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cf.movie.slmovie.server.Constant;
import cf.movie.slmovie.server.HtmlCode;

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
