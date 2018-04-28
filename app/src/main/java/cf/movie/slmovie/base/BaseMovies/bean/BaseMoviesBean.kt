package cf.movie.slmovie.base.BaseMovies.bean

import java.util.ArrayList

import cf.movie.slmovie.base.BaseVo
import cf.movie.slmovie.bean.DetailsBean
import cf.movie.slmovie.bean.StatusBean

/**
 * Created by 包俊 on 2017/7/21.
 */

class BaseMoviesBean : BaseVo() {

    var status: StatusBean? = null
    var movies: ArrayList<BaseMoviesBean.movie>? = null


    inner class movie {
        var name: String? = null
        var address: String? = null
        var post: String? = null
        var douban: String? = null
        var year: String? = null
        var details: DetailsBean? = null
    }

}
