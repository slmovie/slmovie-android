package cf.movie.slmovie.commonModels.reactUpdate

import android.content.Context

import java.io.File

import cf.movie.slmovie.utils.AssetsUtils
import cf.movie.slmovie.utils.ZipUtils

/**
 * Created by 包俊 on 2017/8/15.
 */

class UpdateModel(private val context: Context) {

    /**
     * 检查本地是否已经解压了bundle
     *
     * @param zipName 压缩包名称
     * @param jsPath  rn根目录地址
     * @param bundle  bundle名
     * @return
     */
    fun checkZip(zipName: String, jsPath: String, bundle: String): Boolean {
        val file = File(context.externalCacheDir, jsPath + File.separator + bundle)
        if (!file.exists()) {
            val fileRn = File(context.externalCacheDir.toString() + File.separator + "rn")
            if (!fileRn.exists())
                fileRn.mkdir()
            AssetsUtils.copy(context, zipName, context.externalCacheDir!!.absolutePath)
            val over = ZipUtils.upZip(context.externalCacheDir!!.absolutePath + File.separator + zipName, context.externalCacheDir!!.absolutePath + File.separator + jsPath)
            if (over) {
                val fileD = File(context.externalCacheDir!!.absolutePath, zipName)
                fileD.delete()
            }
            return over
        } else {
            return true
        }
    }

}
