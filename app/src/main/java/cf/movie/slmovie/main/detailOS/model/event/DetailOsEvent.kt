package cf.movie.slmovie.main.detailOS.model.event

import cf.movie.slmovie.eventBus.BaseEvent
import cf.movie.slmovie.main.detail.bean.MovieDetailBean

/**
 * Created by 包俊 on 2018/5/25.
 */
class DetailOsEvent(movie: MovieDetailBean, status: Boolean, message: String) : BaseEvent(status, message) {
    var movie: MovieDetailBean? = movie
}