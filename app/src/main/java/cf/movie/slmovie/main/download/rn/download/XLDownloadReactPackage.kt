package cf.movie.slmovie.main.download.rn.download

import android.app.Activity
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadReactPackage(var activity: Activity) : ReactPackage {
    override fun createNativeModules(reactContext: ReactApplicationContext?): MutableList<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(XLDownloadModule(activity, reactContext!!))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}