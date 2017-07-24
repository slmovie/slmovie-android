package cf.movie.slmovie.utils;

import android.util.Log;

import cf.movie.slmovie.server.Constant;

/**
 * Created by 包俊 on 2017/7/24.
 */

public class LogUtils {

    public static void e(String Tag, String msg) {
        if (Constant.Log)
            Log.e(Tag, msg);
    }

    public static void w(String Tag, String msg) {
        if (Constant.Log)
            Log.w(Tag, msg);
    }
}
