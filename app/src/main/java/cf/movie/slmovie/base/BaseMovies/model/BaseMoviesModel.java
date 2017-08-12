package cf.movie.slmovie.base.BaseMovies.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean;
import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.event.AdapterEvent;
import cf.movie.slmovie.server.Constant;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesModel {

    private Context context;
    private AdapterEvent event;
    private BaseMoviesAdapter adapter;

    public BaseMoviesModel(Context context) {
        this.context = context;
        event = new AdapterEvent(true, "");
    }

    /**
     * 请求电影数据
     *
     * @param which
     */
    public void getMovies(Which.UrlType which) {
        String url = Which.getUrl(which);
        event.setWhich(which);
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.WEBROOT + url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BaseMoviesBean bean = gson.fromJson(response.toString(), BaseMoviesBean.class);
                        if (bean.getStatus().getCode().equals("1")) {
                            event.setStatus(true);
                            if (adapter == null) {
                                adapter = new BaseMoviesAdapter(bean.getMovies());
                                event.setAdapter(adapter);
                                event.setRefresh(false);
                                EventBus.getDefault().post(event);
                            } else {
                                adapter.refresh(bean.getMovies());
                                event.setAdapter(adapter);
                                event.setRefresh(true);
                                EventBus.getDefault().post(event);
                            }
                        } else {
                            event.setStatus(false);
                            event.setMessage(bean.getStatus().getError());
                            EventBus.getDefault().post(event);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        event.setStatus(false);
                        event.setMessage(error.getMessage());
                        EventBus.getDefault().post(event);
                    }
                });
        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
