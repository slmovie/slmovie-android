package cf.movie.slmovie.main.search.event

import cf.movie.slmovie.eventBus.BaseEvent
import cf.movie.slmovie.main.search.bean.SearchAdapter

/**
 * Created by 包俊 on 2017/8/12.
 */

class SearchEvent(status: Boolean, message: String) : BaseEvent(status, message) {

    var adapter: SearchAdapter? = null
}
