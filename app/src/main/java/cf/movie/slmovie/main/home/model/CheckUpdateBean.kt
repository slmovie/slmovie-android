package cf.movie.slmovie.main.home.model

import cf.movie.slmovie.base.BaseVo
import cf.movie.slmovie.bean.StatusBean

/**
 * Created by 包俊 on 2018/5/16.
 */
class CheckUpdateBean : BaseVo() {
    var status: StatusBean? = null
    var movies: data? = null

    inner class data : BaseVo() {
        var version: Boolean = false
        var info: String = "升级"
    }
}
