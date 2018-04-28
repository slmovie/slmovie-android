package cf.movie.slmovie.main.detail.bean

import cf.movie.slmovie.base.BaseVo
import java.util.ArrayList

import cf.movie.slmovie.bean.DetailsBean
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.bean.StatusBean

/**
 * Created by 包俊 on 2017/8/6.
 */

class MovieDetail : BaseVo() {
    var status: StatusBean? = null
    var movies: MovieDetail.movie? = null

    inner class movie {
        var name: String? = null
        var post: String? = null
        var describe: String? = null
        var details: DetailsBean? = null
        var files: ArrayList<FilesBean>? = null
        var detail: ArrayList<String>? = null
    }

}