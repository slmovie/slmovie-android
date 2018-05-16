package cf.movie.slmovie.bean

import cf.movie.slmovie.base.BaseVo

/**
 * Created by 包俊 on 2017/8/12.
 */

class DetailsBean : BaseVo() {
    var name: String? = null
    var year: String? = null
    var location: String? = null
    var type: String? = null
    var actor: String? = null
    var director: String? = null
    var othername: String? = null
    var imdb: String? = null
    var status: String? = null
    var isTV: Boolean = false
    var average: String? = null
}
