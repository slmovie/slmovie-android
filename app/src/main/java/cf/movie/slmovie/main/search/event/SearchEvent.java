package cf.movie.slmovie.main.search.event;

import cf.movie.slmovie.eventBus.BaseEvent;
import cf.movie.slmovie.main.search.bean.SearchAdapter;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class SearchEvent extends BaseEvent {

    private SearchAdapter adapter;

    public SearchEvent(boolean status, String message) {
        super(status, message);
    }

    public SearchAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(SearchAdapter adapter) {
        this.adapter = adapter;
    }
}
