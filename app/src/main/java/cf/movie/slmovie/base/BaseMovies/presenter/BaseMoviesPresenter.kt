package cf.movie.slmovie.base.BaseMovies.presenter

import android.app.Activity
import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.event.AdapterEvent
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesModel
import cf.movie.slmovie.base.BaseMovies.ui.IBaseMovies
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 包俊 on 2018/4/28.
 */
class BaseMoviesPresenter(val activity: Activity, val view: IBaseMovies) {
    private var model: BaseMoviesModel
    private var which: Which.UrlType? = null;
    private var isRefresh = false;

    init {
        model = BaseMoviesModel(activity)
        EventBus.getDefault().register(this)
    }

    /**
     * 请求电影数据
     *
     * @param which 数据类型 0：热门电影
     */
    fun getMovies(which: Which.UrlType) {
        if (!isRefresh) {
            this.which = which
            isRefresh = true
            model!!.getMovies(which)
        }
    }

    /**
     * 事件响应方法
     * 接收消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: AdapterEvent) {
        if (event.which == which) {
            isRefresh = false
            if (event.isStatus) {
                var adapter = event.adapter
                if (adapter != null)
                    if (event.isRefresh)
                        view.refreshOver(adapter)
                    else
                        view.setAdapter(adapter)
                else
                    view.reqError("请求失败，请重试")
            } else {
                view.reqError("请求失败，请重试")
            }
        }
    }

}