package cf.movie.slmovie.base.BaseMovies.presenter;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.base.BaseMovies.event.AdapterEvent;
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter;
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesModel;
import cf.movie.slmovie.base.BaseMovies.ui.IBaseMovies;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesPresenter {

    private IBaseMovies view;
    private BaseMoviesModel model;
    private Which.UrlType which;
    private boolean isRefresh = false;

    public BaseMoviesPresenter(Activity activity, IBaseMovies view) {
        this.view = view;
        model = new BaseMoviesModel(activity);
        EventBus.getDefault().register(this);
    }

    /**
     * 请求电影数据
     *
     * @param which 数据类型 0：热门电影
     */
    public void getMovies(Which.UrlType which) {
        if (!isRefresh) {
            this.which = which;
            isRefresh = true;
            model.getMovies(which);
        }
    }

    /**
     * 事件响应方法
     * 接收消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AdapterEvent event) {
        if (event.getWhich() == which) {
            isRefresh = false;
            if (event.isStatus()) {
                BaseMoviesAdapter adapter = event.getAdapter();
                if (event.isRefresh()) {
                    view.refreshOver(adapter);
                } else {
                    view.setAdapter(adapter);
                }
            } else {
                view.reqError("请求失败，请重试");
            }
        }
    }
}
