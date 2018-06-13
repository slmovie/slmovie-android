package cf.movie.slmovie.main.download.rn.download

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.dialog.ProgressDialog.ProgressDialog
import cf.movie.slmovie.dialog.XLDownloadDialog.XLDownloadDialog
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kName
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kSize
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.isMediaFile
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.main.home.ui.MainActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.LogUtils
import cf.movie.slmovie.utils.OutsideDownloadUtils
import com.facebook.react.bridge.*
import com.google.gson.Gson
import com.xunlei.downloadlib.XLTaskHelper
import com.xunlei.downloadlib.parameter.TorrentInfo
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadModule(val activity: Activity, val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var scanTorrentPromise: Promise? = null
    private val ScanTorrent: Int = 0
    private var progressDialog: ProgressDialog? = null

    override fun getName(): String {
        XLTaskHelper.init(activity.applicationContext)
        if (!File(Constant.DownloadPath).exists())
            File(Constant.DownloadPath).mkdirs()
        return "XLDownloadNative"
    }

    internal var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == ScanTorrent) {
                val json = msg.obj as JSONObject
                val taskInfo = XLTaskHelper.instance().getTaskInfo(json.getLong("taskId"))
                when (taskInfo.mTaskStatus) {
                    0, 1 -> {
                        LogUtils.e("scanTorrent", "taskId=" + taskInfo.mTaskId + ">>taskStatus=" + taskInfo.mTaskStatus)
                        var json1 = JSONObject()
                        json1.put("taskId", json.getLong("taskId"))
                        json1.put("savePath", json.optString("savePath"))
                        json1.put("magent", json.optString("magent"))
                        var message = Message()
                        message.what = ScanTorrent
                        message.obj = json1
                        this.sendMessageDelayed(message, 1000)
                    }
                    2 -> {
                        analyzeTorrent(json.optString("savePath"), json.optString("magent"))
                    }
                    3 -> {
                        val maps = Arguments.createMap()
                        maps.putBoolean("status", false)
                        scanTorrentPromise!!.resolve(maps)
                    }
                }

            }
        }
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
    fun scanTorrent(fileStr: String, scanTorrentPromise: Promise) {
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(activity, *perms)) {
            if (progressDialog == null)
                progressDialog = ProgressDialog(activity, "分析种子中")
            progressDialog!!.show()
            progressDialog!!.data = "分析种子中"
            this.scanTorrentPromise = scanTorrentPromise
            var gson = Gson()
            var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
            val savePath = Constant.DownloadPath + fileBean!!.name
            val file = File(savePath)
            if (file.exists()) {
                analyzeTorrent(savePath, fileBean!!.download!!)
            } else {
                var taskId = XLTaskHelper.instance().addMagentTask(fileBean!!.download!!, Constant.DownloadPath, fileBean!!.name)
                var json = JSONObject()
                json.put("taskId", taskId)
                json.put("savePath", savePath)
                json.put("magent", fileBean!!.download)
                var message = Message()
                message.what = ScanTorrent
                message.obj = json
                handler.sendMessage(message)
            }
        } else {
            EasyPermissions.requestPermissions(activity, "下载电影", MainActivity.READ_PHONE_STATE, *perms)
        }
    }

    private var torrentMediaIndexs: IntArray? = null
    private var torrentUnmediaIndexs: IntArray? = null

    //分析种子
    @ReactMethod
    private fun analyzeTorrent(path: String, magent: String) {
        var torrentInfo = XLTaskHelper.instance().getTorrentInfo(path)
        var currentPlayMediaIndex = initTorrentIndex(torrentInfo)
        if (progressDialog != null && progressDialog!!.isShowing)
            progressDialog!!.dismiss()
        when (currentPlayMediaIndex.size) {
        //没有视频文件
            0 -> {
                Toast.makeText(reactContext, "种子没有可播放文件,下载地址已复制至剪切板", Toast.LENGTH_LONG).show()
                OutsideDownloadUtils.copy(reactContext, magent)
            }
        //有视频文件
            else -> {
                var list = ArrayList<XLDownloadDBBean>()
                currentPlayMediaIndex.forEach {
                    var torrentFileInfo = torrentInfo.mSubFileInfo[it]
                    var bean = XLDownloadDBBean()
                    bean.Name = torrentFileInfo.mFileName
                    bean.TotalSize = torrentFileInfo.mFileSize
                    bean.SavePath = Constant.DownloadPath + bean.Name
                    bean.IsTorrent = 1
                    bean.DownloadPath = magent
                    bean.TorrentPath = path
                    bean.DownloadStatus = 1
                    list.add(bean)
                }
                var dialog = XLDownloadDialog(activity, list)
                dialog.show()
                dialog.setOnClickDownloadListner(object : XLDownloadDialog.onClickDownloadListener {
                    override fun myDownload(dialog: XLDownloadDialog) {
                        dialog.dismiss()
                        var intent = Intent(activity, DownloadRNActivity::class.java)
                        intent.putExtra("download", list.get(0))
                        activity.startActivity(intent)
                    }

                    override fun sysDownload(dialog: XLDownloadDialog) {
                        dialog.dismiss()
                        Toast.makeText(activity, "种子以保存" + path, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    //查找种子中的视频文件
    private fun initTorrentIndex(torrentInfo: TorrentInfo): ArrayList<Int> {
        var currentPlayMediaIndexs = ArrayList<Int>()
        if (torrentInfo != null && torrentInfo!!.mSubFileInfo != null) {
            val mediaIndexs = ArrayList<Int>()
            val unmediaIndexs = ArrayList<Int>()
            for (i in torrentInfo.mSubFileInfo.indices) {
                val torrentFileInfo = torrentInfo!!.mSubFileInfo[i]
                if (isMediaFile(torrentFileInfo.mFileName)) {
                    mediaIndexs.add(torrentFileInfo.mFileIndex)
                } else {
                    unmediaIndexs.add(torrentFileInfo.mFileIndex)
                }
            }
            this.torrentMediaIndexs = IntArray(mediaIndexs.size)
            this.torrentUnmediaIndexs = IntArray(unmediaIndexs.size)
            for (i in mediaIndexs.indices)
                torrentMediaIndexs!![i] = mediaIndexs[i]
            for (i in unmediaIndexs.indices)
                torrentUnmediaIndexs!![i] = unmediaIndexs[i]
            if (this.torrentMediaIndexs!!.size > 0) {
                currentPlayMediaIndexs.add(this.torrentMediaIndexs!![0])
            }
        }
        return currentPlayMediaIndexs
    }

    private fun getTorrentDeselectedIndexs(currentPlayMediaIndex: Int): IntArray {
        val indexs = IntArray(this.torrentUnmediaIndexs!!.size + this.torrentMediaIndexs!!.size - 1)
        var offset = 0
        for (idx in this.torrentMediaIndexs!!) {
            if (idx != currentPlayMediaIndex) {
                indexs[offset++] = idx
            }
        }
        for (idx in this.torrentUnmediaIndexs!!) {
            indexs[offset++] = idx
        }
        return indexs
    }
}


