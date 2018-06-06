package cf.movie.slmovie.utils.rnUtils.checkVersion

import cf.movie.slmovie.utils.rnUtils.baseRN.view.IBaseRNView
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

/**
 * Created by 包俊 on 2017/8/15.
 */

class CheckVersionReactPackage(private val impl: IBaseRNView) : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(CheckVersionModule(reactContext, impl))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
