package cf.movie.slmovie.main.search.model;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cf.movie.slmovie.main.search.bean.SearchAdapter;
import cf.movie.slmovie.main.search.bean.SearchResult;
import cf.movie.slmovie.main.search.event.SearchEvent;
import cf.movie.slmovie.server.Constant;
import cf.movie.slmovie.server.HtmlCode;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class SearchModel {

    private Activity context;
    private SearchEvent event;

    public SearchModel(Activity context) {
        this.context = context;
        event = new SearchEvent(true, "");
    }

    public void getMovies(String search) {
        String url = Constant.WEBROOT + HtmlCode.Search + search;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                SearchResult bean = gson.fromJson(response.toString(), SearchResult.class);
                if (bean.getStatus().getCode().equals("1")) {
                    SearchAdapter adapter = new SearchAdapter(bean.getMovies());
                    event.setStatus(true);
                    event.setAdapter(adapter);
                    EventBus.getDefault().post(event);
                } else {
                    event.setStatus(false);
                    EventBus.getDefault().post(event);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                event.setStatus(false);
                event.setMessage(error.getMessage());
                EventBus.getDefault().post(event);
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

}
