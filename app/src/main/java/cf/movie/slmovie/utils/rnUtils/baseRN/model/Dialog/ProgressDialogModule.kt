package cf.movie.slmovie.utils.rnUtils.baseRN.model.Dialog

import android.app.Activity
import cf.movie.slmovie.dialog.ProgressDialog.ProgressDialog
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

/**
 * Created by 包俊 on 2017/8/10.
 */

class ProgressDialogModule(reactContext: ReactApplicationContext, private val context: Activity) : ReactContextBaseJavaModule(reactContext) {
    private var dialog: ProgressDialog? = null

    override fun getName(): String {
        return "ProgressDialogNative"
    }

    /**
     * @param title 标题
     * @param msg   内容
     */
    @ReactMethod
    fun show(msg: String) {
        dialog = ProgressDialog.show(context, msg)
    }

    @ReactMethod
    fun dismiss() {
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss()
        }
    }

}