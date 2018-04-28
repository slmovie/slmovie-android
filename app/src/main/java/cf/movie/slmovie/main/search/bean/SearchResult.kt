package cf.movie.slmovie.main.search.bean

import cf.movie.slmovie.base.BaseVo
import java.util.ArrayList

import cf.movie.slmovie.bean.DetailsBean
import cf.movie.slmovie.bean.StatusBean

/**
 * Created by 包俊 on 2017/8/12.
 */

class SearchResult : BaseVo(){
    var status: StatusBean? = null
    var movies: ArrayList<Movies>? = null

    inner class Movies {
        var name: String? = null
        var post: String? = null
        var describe: String? = null
        var id: String? = null
        var details: DetailsBean? = null

    }

}
