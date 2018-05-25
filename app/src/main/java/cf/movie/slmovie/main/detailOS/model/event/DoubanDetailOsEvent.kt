package cf.movie.slmovie.main.detailOS.model.event

import cf.movie.slmovie.eventBus.BaseEvent
import cf.movie.slmovie.main.detailOS.model.bean.DoubanDetailBean

/**
 * Created by 包俊 on 2018/5/25.
 */
class DoubanDetailOsEvent(movie: DoubanDetailBean, status: Boolean, message: String) : BaseEvent(status, message) {
    var movie: DoubanDetailBean = movie
}