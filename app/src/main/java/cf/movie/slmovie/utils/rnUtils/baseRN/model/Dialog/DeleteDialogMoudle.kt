package cf.movie.slmovie.utils.rnUtils.baseRN.model.Dialog

import android.app.Activity
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod


/**
 * Created by 包俊 on 2018/6/26.
 */
class DeleteDialogMoudle(reactContext: ReactApplicationContext, private val context: Activity) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "DeleteDialogNative"
    }

    @ReactMethod
    fun show(ok: Callback) {
        var deleteDialog = DeleteDialog(context, object : DeleteDialog.onButtonListener {
            override fun click(dialog: DeleteDialog, chosen: Boolean) {
                ok.invoke(chosen)
                dialog.dismiss()
            }
        })
        deleteDialog.show()
    }
}