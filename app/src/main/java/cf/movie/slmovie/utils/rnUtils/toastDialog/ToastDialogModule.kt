package cf.movie.slmovie.utils.rnUtils.toastDialog

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray

import java.util.ArrayList

import cf.movie.slmovie.R

/**
 * Created by 包俊 on 2017/8/10.
 */

class ToastDialogModule(reactContext: ReactApplicationContext, private val context: Activity) : ReactContextBaseJavaModule(reactContext) {
    private var dialog: AlertDialog? = null

    override fun getName(): String {
        return "ToastDialogNative"
    }

    /**
     * @param title 标题
     * @param msg   内容
     */
    @ReactMethod
    fun show(title: String, msg: String, buttons: ReadableArray, ok: Callback, cancel: Callback) {
        val myButtons = buttons.toArrayList()
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(myButtons[0].toString()) { dialog, which -> ok.invoke() }
        if (myButtons.size > 1) {
            builder.setNegativeButton(myButtons[1].toString()) { dialog, which -> cancel.invoke() }
        }
        dialog = builder.create()
        dialog!!.show()
    }

    @ReactMethod
    fun dismiss() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

}