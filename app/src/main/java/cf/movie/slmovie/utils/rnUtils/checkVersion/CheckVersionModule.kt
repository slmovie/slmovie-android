package cf.movie.slmovie.utils.rnUtils.checkVersion

import android.widget.Toast
import cf.movie.slmovie.main.detail.ui.IDetailView
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode
import cf.movie.slmovie.utils.DownloadUtil
import cf.movie.slmovie.utils.ZipUtils
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.io.File

/**
 * Created by 包俊 on 2017/8/15.
 */

class CheckVersionModule(private val context: ReactApplicationContext, private val iDetailView: IDetailView) : ReactContextBaseJavaModule(context) {

    override fun getName(): String {
        return "CheckVersionNative"
    }

    @ReactMethod
    fun Download(module: String) {
        DownloadUtil.download(Constant.WEBROOT + HtmlCode.RNUpdate + module + ".zip", context.externalCacheDir!!.absolutePath + File.separator + "rn", object : DownloadUtil.OnDownloadListener {
            override fun onDownloadSuccess() {
                val ok = ZipUtils.upZip(context.externalCacheDir!!.absolutePath + File.separator + "rn" + File.separator + module + ".zip", context.externalCacheDir!!.absolutePath + File.separator + "rn" + File.separator + module)
                if (ok) {
                    val file = File(context.externalCacheDir!!.absolutePath + File.separator + "rn" + File.separator + module + ".zip")
                    file.delete()
                }
                iDetailView.reCreateReactNative()
            }

            override fun onDownloading(progress: Int) {

            }

            override fun onDownloadFailed() {
                Toast.makeText(context, "失败", Toast.LENGTH_LONG).show()
            }
        })
    }

}
