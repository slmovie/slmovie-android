package cf.movie.slmovie.main.detail.rn

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

import java.util.ArrayList
import java.util.Collections

import cf.movie.slmovie.main.detail.ui.IDetailView

/**
 * Created by BJ on 2016/9/28.
 */
class DetailReactPackage(private val address: String, private val iDetailView: IDetailView) : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(GetDetailModule(reactContext, address, iDetailView))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
