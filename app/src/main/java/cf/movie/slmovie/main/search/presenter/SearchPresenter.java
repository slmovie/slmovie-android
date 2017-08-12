package cf.movie.slmovie.main.search.presenter;

import android.app.Activity;
import android.support.v7.widget.SearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cf.movie.slmovie.main.search.event.SearchEvent;
import cf.movie.slmovie.main.search.model.SearchModel;
import cf.movie.slmovie.main.search.ui.ISearchActivity;

import static android.R.attr.searchMode;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class SearchPresenter {

    private Activity context;
    private ISearchActivity iSearch;
    private SearchModel searchModel;

    public SearchPresenter(Activity context, ISearchActivity iSearch) {
        this.context = context;
        this.iSearch = iSearch;
        searchModel = new SearchModel(context);
        EventBus.getDefault().register(this);
    }

    public void getMovies(String search) {
        iSearch.swipe(true);
        searchModel.getMovies(search);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchEvent event) {
        iSearch.swipe(false);
        if (event.isStatus()) {
            iSearch.setAdapter(event.getAdapter());
        } else {
            iSearch.reqError("请求失败，请重试");
        }
    }

}
