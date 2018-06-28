package cf.movie.slmovie.utils.rnUtils.baseRN.model.Dialog

import android.app.Activity
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

/**
 * Created by 包俊 on 2017/8/7.
 */

class DialogReactPackage(private val activity: Activity) : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(ToastDialogModule(reactContext, activity))
        modules.add(ProgressDialogModule(reactContext, activity))
        modules.add(DeleteDialogMoudle(reactContext, activity))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
