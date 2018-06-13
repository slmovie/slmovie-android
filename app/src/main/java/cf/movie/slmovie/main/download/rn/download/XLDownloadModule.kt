package cf.movie.slmovie.main.download.rn.download

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.dialog.XLDownloadDialog.XLDownloadDialog
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kName
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kSize
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.OutsideDownloadUtils
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.gson.Gson
import com.xunlei.downloadlib.XLTaskHelper
import java.io.File


/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadModule(var activity: Activity, val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        XLTaskHelper.init(activity.applicationContext)
        if (!File(Constant.DownloadPath).exists())
            File(Constant.DownloadPath).mkdirs()
        return "XLDownloadNative"
    }

    //下载弹出框
    @ReactMethod
    fun ed2kDownload(fileStr: String) {
        var gson = Gson()
        var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
        var bean = XLDownloadDBBean()
        bean.Name = getEd2kName(fileBean.download!!)
        bean.TotalSize = getEd2kSize(fileBean.download!!)
        bean.SavePath = Constant.DownloadPath + fileBean.name
        bean.IsTorrent = 0
        bean.DownloadPath = fileBean!!.download!!
        bean.DownloadStatus = 1
        val list = ArrayList<XLDownloadDBBean>()
        list.add(bean)
        val dialog = XLDownloadDialog(activity, list)
        dialog.show()
        dialog.setOnClickDownloadListner(object : XLDownloadDialog.onClickDownloadListener {
            override fun myDownload(dialog: XLDownloadDialog) {
                dialog.dismiss()
                var intent = Intent(activity, DownloadRNActivity::class.java)
                intent.putExtra("download", bean)
                activity.startActivity(intent)
            }

            override fun sysDownload(dialog: XLDownloadDialog) {
                dialog.dismiss()
                sysDownload(fileStr)
            }
        })
    }

    @ReactMethod
    fun sysDownload(fileStr: String) {
        var gson = Gson()
        var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
        if (!OutsideDownloadUtils.start(reactContext, fileBean!!.download!!))
            OutsideDownloadUtils.copy(reactContext, fileBean!!.download!!)
        Toast.makeText(reactContext, "无可下载应用，下载地址已复制到剪切板！", Toast.LENGTH_LONG).show()
    }

    //下载种子文件
    @ReactMethod
    fun scanTorrent(fileStr: String) {
        XLTorrentUtils.get(activity).scanTorrent(fileStr)
    }

    //分析种子
    @ReactMethod
    private fun analyzeTorrent(path: String, magent: String) {
        XLTorrentUtils.get(activity).analyzeTorrent(path, magent)
    }

}


