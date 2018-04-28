package cf.movie.slmovie.eventBus

/**
 * Created by 包俊 on 2017/7/21.
 */

open class BaseEvent(status: Boolean, message: String) {
    var isStatus: Boolean = false
    var message: String? = null

    init {
        this.isStatus = status
        this.message = message
    }
}
