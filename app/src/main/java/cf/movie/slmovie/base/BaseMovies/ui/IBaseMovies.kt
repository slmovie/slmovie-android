package cf.movie.slmovie.base.BaseMovies.ui

import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter

/**
 * Created by 包俊 on 2018/4/28.
 */
interface IBaseMovies {
    fun setAdapter(adapter: BaseMoviesAdapter)

    fun refreshOver(adapter: BaseMoviesAdapter)

    fun reqError(msg: String)
}