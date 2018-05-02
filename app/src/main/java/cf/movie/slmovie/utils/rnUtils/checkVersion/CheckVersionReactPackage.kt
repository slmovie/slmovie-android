package cf.movie.slmovie.utils.rnUtils.checkVersion

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

import java.util.ArrayList
import java.util.Collections

import cf.movie.slmovie.main.detail.ui.IDetailView
import cf.movie.slmovie.utils.rnUtils.download.DownloadModule

/**
 * Created by 包俊 on 2017/8/15.
 */

class CheckVersionReactPackage(private val iDetailView: IDetailView) : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(CheckVersionModule(reactContext, iDetailView))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
