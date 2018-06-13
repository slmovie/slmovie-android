package cf.movie.slmovie.utils.rnUtils.baseRN.model.LoadModule

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.ViewManager
import java.util.*

/**
 * Created by 包俊 on 2018/6/12.
 */
class LoadReactPackage(val listener: LoadModule.LoadListener) : ReactPackage {

    private var reactContext: ReactApplicationContext? = null

    override fun createNativeModules(reactContext: ReactApplicationContext?): MutableList<NativeModule> {
        this.reactContext = reactContext
        val modules = ArrayList<NativeModule>()
        modules.add(LoadModule(reactContext!!, listener))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }

    fun emit(name: String, param: WritableMap) {
        reactContext!!.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(name, param)
    }
}