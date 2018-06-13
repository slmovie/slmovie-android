package cf.movie.slmovie.utils.rnUtils.baseRN.model.LoadModule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod


/**
 * Created by 包俊 on 2018/6/12.
 */
class LoadModule(var context: ReactApplicationContext, val listener: LoadListener) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "LoadUtilNative"
    }

    @ReactMethod
    fun loadFinish() {
        listener.loadFinish()
    }

    interface LoadListener {
        fun loadFinish()
    }
}