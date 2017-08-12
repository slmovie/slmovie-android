package cf.movie.slmovie.base.BaseMovies.ui;

import android.support.v7.widget.RecyclerView;

import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter;

/**
 * Created by 包俊 on 2017/7/21.
 */

public interface IBaseMovies {
    void setAdapter(BaseMoviesAdapter adapter);

    void refreshOver(BaseMoviesAdapter adapter);

    void reqError(String msg);
}
