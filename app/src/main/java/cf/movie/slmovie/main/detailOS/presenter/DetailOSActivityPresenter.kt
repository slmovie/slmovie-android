package cf.movie.slmovie.main.detailOS.presenter

import android.content.Context
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.detail.bean.MovieDetailBean
import cf.movie.slmovie.main.detailOS.model.MovieByDoubanModel

/**
 * Created by 包俊 on 2018/5/24.
 */
class DetailOSActivityPresenter(private var context: Context) {
    fun getDetail(id: String, impl: BaseReqListener<MovieDetailBean>) {
        var model = MovieByDoubanModel(context)
        model.start(id, impl)
    }
}