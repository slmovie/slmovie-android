package cf.movie.slmovie.utils

import android.util.Log

import cf.movie.slmovie.server.Constant

/**
 * Created by 包俊 on 2017/7/24.
 */

object LogUtils {

    fun e(Tag: String, msg: String) {
        if (Constant.Log)
            Log.e("slmovie", msg)
    }

    fun w(Tag: String, msg: String) {
        if (Constant.Log)
            Log.w("slmovie", msg)
    }
}
