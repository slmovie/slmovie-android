package cf.movie.slmovie.commonModels.reactUpdate

import android.content.Context
import android.content.SharedPreferences
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.SharePerfConstent

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
        val sharedPreferences = context.getSharedPreferences("slmovie", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val HisRNVersion = sharedPreferences.getInt(SharePerfConstent.RNVersionShare, 0)
        if (!file.exists() || HisRNVersion != Constant.RNVersion) {
            val fileRn = File(context.externalCacheDir.toString() + File.separator + "rn")
            if (!fileRn.exists())
                fileRn.mkdir()
            AssetsUtils.copy(context, zipName, context.externalCacheDir!!.absolutePath)
            val over = ZipUtils.upZip(context.externalCacheDir!!.absolutePath + File.separator + zipName, context.externalCacheDir!!.absolutePath + File.separator + jsPath)
            if (over) {
                val fileD = File(context.externalCacheDir!!.absolutePath, zipName)
                editor.putInt(SharePerfConstent.RNVersionShare, Constant.RNVersion)
                editor.commit()
                fileD.delete()
            }
            return over
        } else {
            return true
        }
    }

}
