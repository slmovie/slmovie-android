package cf.movie.slmovie.base.BaseMovies.event

import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.eventBus.BaseEvent
import cf.movie.slmovie.base.BaseMovies.model.BaseMoviesAdapter

/**
 * Created by 包俊 on 2017/7/21.
 */

class AdapterEvent(status: Boolean, message: String) : BaseEvent(status, message) {
    var adapter: BaseMoviesAdapter? = null
    var isRefresh: Boolean = false
    var which: Which.UrlType? = null
}
