package cf.movie.slmovie.base.BaseMovies.event;

import cf.movie.slmovie.base.BaseMovies.constant.Which;
import cf.movie.slmovie.eventBus.BaseEvent;
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class AdapterEvent extends BaseEvent {
    private BaseMoviesAdapter adapter;
    private boolean isRefresh;
    private Which.UrlType which;

    public AdapterEvent(boolean status, String message) {
        super(status, message);
    }

    public BaseMoviesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseMoviesAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public Which.UrlType getWhich() {
        return which;
    }

    public void setWhich(Which.UrlType which) {
        this.which = which;
    }
}
