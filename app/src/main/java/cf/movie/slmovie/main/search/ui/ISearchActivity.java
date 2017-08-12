package cf.movie.slmovie.main.search.ui;

import cf.movie.slmovie.main.search.bean.SearchAdapter;
import cf.movie.slmovie.main.search.event.SearchEvent;

/**
 * Created by 包俊 on 2017/8/12.
 */

public interface ISearchActivity {

    void setAdapter(SearchAdapter adapter);

    void reqError(String msg);

    void swipe(boolean refresh);
}
