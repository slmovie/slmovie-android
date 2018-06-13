package cf.movie.slmovie.server

import android.os.Environment

/**
 * Created by 包俊 on 2017/7/21.
 */

object Constant {
    val ENVIRONMENT_TYPE = "P"
    var Log = true
    var WEBROOT: String? = null
    var RNVersion: Int = 1
    var DownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/slys/download/"

    init {
        when (ENVIRONMENT_TYPE) {
            "T" -> WEBROOT = "http://192.168.43.22:3000"
            "P" -> WEBROOT = "http://45.32.41.169:3000"
        }
    }

}
