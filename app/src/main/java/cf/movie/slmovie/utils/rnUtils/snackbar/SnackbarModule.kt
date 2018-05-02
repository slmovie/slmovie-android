package cf.movie.slmovie.utils.rnUtils.snackbar

import android.graphics.Color
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

import java.util.HashMap

/**
 * Created by 包俊 on 2017/8/7.
 */

class SnackbarModule(reactContext: ReactApplicationContext, private var coordinator: CoordinatorLayout) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "SnackbarNative"
    }

    @ReactMethod
    fun showSnackbar(msg: String, duration: Int, callback: Callback) {
        Snackbar.make(coordinator, msg, duration)
                .setAction("重新加载") { callback.invoke() }
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show()
    }

    @ReactMethod
    fun showSnackbar(msg: String, duration: Int) {
        Snackbar.make(coordinator, msg, duration)
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show()
    }

    override fun getConstants(): Map<String, Any>? {
        val constants = HashMap<String, Any>()
        constants[LENGTH_SHORT] = BaseTransientBottomBar.LENGTH_SHORT
        constants[LENGTH_LONG] = BaseTransientBottomBar.LENGTH_LONG
        constants[LENGTH_INDEFINITE] = BaseTransientBottomBar.LENGTH_INDEFINITE
        return constants
    }

    companion object {
        private val LENGTH_SHORT = "SHORT"
        private val LENGTH_LONG = "LONG"
        private val LENGTH_INDEFINITE = "INDEFINITE"
    }
}
