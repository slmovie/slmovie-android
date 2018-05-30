package cf.movie.slmovie.utils.rnUtils.download

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.facebook.react.bridge.*

/**
 * Created by 包俊 on 2017/8/9.
 */

class DownloadModule(private val context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {

    override fun getName(): String {
        return "DownloadNative"
    }

    @ReactMethod
    fun pushDownload(url: String, promise: Promise) {
        var isFind = true
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory("android.intent.category.DEFAULT")
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, url, Toast.LENGTH_LONG).show()
            val myClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("text", url)
            myClipboard.primaryClip = myClip
            isFind = false
            e.printStackTrace()
        }

        val maps = Arguments.createMap()
        maps.putBoolean("result", isFind)
        promise.resolve(maps)
    }
}
