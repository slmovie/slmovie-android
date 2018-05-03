package cf.movie.slmovie.server

import com.facebook.soloader.SoLoader.init

/**
 * Created by 包俊 on 2017/7/21.
 */

object Constant {
    private val ENVIRONMENT_TYPE = "P"
    var Log = true
    var WEBROOT: String? = null

    init {
        if (ENVIRONMENT_TYPE == "T") {
            WEBROOT = "http://192.168.43.22:3000"
        }
        if (ENVIRONMENT_TYPE == "P") {
            WEBROOT = "http://45.32.41.169:3000"
        }
    }

}
