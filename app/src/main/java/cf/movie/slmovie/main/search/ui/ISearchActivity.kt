package cf.movie.slmovie.main.search.ui

import cf.movie.slmovie.main.search.event.SearchEvent

/**
 * Created by 包俊 on 2017/8/12.
 */

interface ISearchActivity {

    fun setMovies(event: SearchEvent)

    fun reqError(msg: String)

    fun swipe(refresh: Boolean)
}
