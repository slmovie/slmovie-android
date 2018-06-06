package cf.movie.slmovie.main.detailOS.presenter

import android.content.Context
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.detail.model.bean.MovieDetailBean
import cf.movie.slmovie.main.detailOS.model.DoubanQueryModel
import cf.movie.slmovie.main.detailOS.model.MovieByDoubanModel
import cf.movie.slmovie.main.detailOS.model.bean.DoubanDetailBean

/**
 * Created by 包俊 on 2018/5/24.
 */
class DetailOSActivityPresenter(private var context: Context) {
    fun getDetail(id: String, impl: BaseReqListener<MovieDetailBean>) {
        var model = MovieByDoubanModel(context)
        model.start(id, impl)
    }

    fun getDouban(id: String, impl: BaseReqListener<DoubanDetailBean>) {
        var model = DoubanQueryModel()
        model.start(context, id, impl)
    }
}