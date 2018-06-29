package cf.movie.slmovie.utils.rnUtils.baseRN.model.snackbar

import android.support.design.widget.CoordinatorLayout

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

import java.util.ArrayList

/**
 * Created by 包俊 on 2017/8/7.
 */

class SnackbarReactPackage(private val coordinator: CoordinatorLayout) : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(SnackbarModule(reactContext, coordinator))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
