package cf.movie.slmovie.base

/**
 * Created by 包俊 on 2017/11/23.
 */

interface BaseReqListener<T> {
    fun success(result: T)

    fun failed(errorCode: String?, errorMsg: String?)
}
