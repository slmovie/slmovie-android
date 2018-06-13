package cf.movie.slmovie.utils.rnUtils.baseRN.model.checkVersion

import android.widget.Toast
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode
import cf.movie.slmovie.utils.DownloadUtil
import cf.movie.slmovie.utils.ZipUtils
import cf.movie.slmovie.utils.rnUtils.baseRN.view.IBaseRNView
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.io.File

/**
 * Created by 包俊 on 2017/8/15.
 */

class CheckVersionModule(private val context: ReactApplicationContext, private val impl: IBaseRNView) : ReactContextBaseJavaModule(context) {

    override fun getName(): String {
        return "CheckVersionNative"
    }

    @ReactMethod
    fun Download(module: String) {
        DownloadUtil.download(Constant.WEBROOT + HtmlCode.RNUpdate + module + ".zip", context.externalCacheDir!!.absolutePath + File.separator + "rn", object : DownloadUtil.OnDownloadListener {
            override fun onDownloadSuccess() {
                var filePath = context.externalCacheDir!!.absolutePath + File.separator + "rn";
                val ok = ZipUtils.upZip(context.externalCacheDir!!.absolutePath + File.separator + "rn" + File.separator + module + ".zip", filePath)
                if (ok) {
                    val file = File(context.externalCacheDir!!.absolutePath + File.separator + "rn" + File.separator + module + ".zip")
                    file.delete()
                }
                impl.reCreateReactNative()
            }

            override fun onDownloading(progress: Int) {

            }

            override fun onDownloadFailed() {
                Toast.makeText(context, "失败", Toast.LENGTH_LONG).show()
            }
        })
    }
}
