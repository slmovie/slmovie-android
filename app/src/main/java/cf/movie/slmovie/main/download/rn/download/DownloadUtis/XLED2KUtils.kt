package cf.movie.slmovie.main.download.rn.download.DownloadUtis

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.dialog.XLDownloadDialog.XLDownloadDialog
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadModule
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.OutsideDownloadUtils
import com.google.gson.Gson
import com.xunlei.downloadlib.DownloadException
import com.xunlei.downloadlib.XLTaskHelper

/**
 * Created by 包俊 on 2018/6/29.
 */
class XLED2KUtils(var activity: Activity) {

    private var handler: Handler? = null
    private var listener: XLListener? = null

    constructor(activity: Activity, handler: Handler, listener: XLListener) : this(activity) {
        this.handler = handler
        this.listener = listener
    }

    companion object {
        fun instance(activity: Activity, handler: Handler, listener: XLListener): XLED2KUtils {
            return XLED2KUtils(activity, handler, listener)
        }

        fun instance(activity: Activity): XLED2KUtils {
            return XLED2KUtils(activity)
        }
    }

    fun ed2kDownloadDialog(fileBean: FilesBean) {
        var bean = XLDownloadDBBean()
        bean.Name = XLDownloadUtils.getEd2kName(fileBean.download!!)
        bean.TotalSize = XLDownloadUtils.getEd2kSize(fileBean.download!!)
        bean.SavePath = Constant.DownloadPath
        bean.IsTorrent = 0
        bean.DownloadPath = fileBean!!.download!!
        bean.DownloadStatus = 1
        val dialog = XLDownloadDialog(activity, bean)
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
                sysDownload(fileBean)
            }
        })
    }

    fun ed2kDownload(str: String) {
        var gson = Gson()
        var bean = gson.fromJson(str, XLDownloadDBBean::class.java)
        try {
            var taskId = XLTaskHelper.instance().addThunderTask(bean.DownloadPath, bean.SavePath, bean.Name)
            bean.TaskId = taskId
            listener!!.emitTaskId(bean)
            var message = Message()
            message.what = XLDownloadModule.ED2K
            message.obj = bean
            handler!!.sendMessage(message)
        } catch (e: DownloadException) {
            Toast.makeText(activity, "下载失败，请重试", Toast.LENGTH_LONG).show()
            bean.DownloadStatus = 3
        }
    }

    private fun sysDownload(fileBean: FilesBean) {
        if (!OutsideDownloadUtils.start(activity, fileBean!!.download!!))
            OutsideDownloadUtils.copy(activity, fileBean!!.download!!)
        Toast.makeText(activity, "无可下载应用，下载地址已复制到剪切板！", Toast.LENGTH_LONG).show()
    }

}