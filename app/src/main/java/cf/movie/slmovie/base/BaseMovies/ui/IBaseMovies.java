package cf.movie.slmovie.base.BaseMovies.ui;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 包俊 on 2017/7/21.
 */

public interface IBaseMovies {
    void setAdapter(RecyclerView.Adapter adapter);

    void refreshOver(RecyclerView.Adapter adapter);

    void reqError(String msg);
}
