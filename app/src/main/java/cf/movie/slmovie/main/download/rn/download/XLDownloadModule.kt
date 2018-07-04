package cf.movie.slmovie.main.download.rn.download

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.db.XLDownloadDao
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLED2KUtils
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLListener
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLTorrentUtils
import cf.movie.slmovie.main.home.ui.MainActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.OutsideDownloadUtils
import cf.movie.slmovie.utils.PermissionUtils
import cf.movie.slmovie.utils.ReactNativeJson
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.xunlei.downloadlib.XLTaskHelper
import org.json.JSONObject
import java.io.File


/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadModule(var activity: Activity, val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    companion object {
        val ScanTorrent = 0
        val ED2K = 1
        val Torrent = 2
        var perCallback: Callback? = null
    }

    private var dao = XLDownloadDao(activity)
    private var scanTorrentCallback: Callback? = null

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
                        if (scanTorrentCallback != null)
                            scanTorrentCallback!!.invoke()
                        analyzeTorrent(json.optString("savePath"), json.optString("magent"))
                    }
                    3 -> {
                        if (scanTorrentCallback != null)
                            scanTorrentCallback!!.invoke()
                        Toast.makeText(activity, "下载失败", Toast.LENGTH_LONG).show()
                        if (!OutsideDownloadUtils.start(activity, json.optString("magent")))
                            OutsideDownloadUtils.copy(activity, json.optString("magent"))
                        Toast.makeText(activity, "无可下载应用，下载地址已复制到剪切板！", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (msg.what == ED2K) {
                var bean = msg.obj as XLDownloadDBBean
                val taskInfo = XLTaskHelper.instance().getTaskInfo(bean.TaskId)
                bean.DownloadStatus = taskInfo.mTaskStatus
                bean.DownloadSize = taskInfo.mDownloadSize
                bean.Speed = taskInfo.mDownloadSpeed
                if (!TextUtils.isEmpty(taskInfo.mCid))
                    bean.mCid = taskInfo.mCid
                when (taskInfo.mTaskStatus) {
                    0 -> {

                    }
                    1 -> {
                        var message = Message()
                        message.what = ED2K
                        message.obj = bean
                        this.sendMessageDelayed(message, 1000)
                    }
                }
                when (taskInfo.mTaskStatus) {
                    1, 2, 3 -> {
                        //保存到数据库
                        if (TextUtils.isEmpty(dao.find(bean).Name)) {
                            dao.insert(bean)
                        } else {
                            dao.update(bean)
                        }
                        //推送给RN
//                        var gson = Gson()
//                        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
//                                .emit("Querytask", ReactNativeJson.convertStringToMap(gson.toJson(bean)))
                    }
                }
            } else if (msg.what == Torrent) {
                var bean = msg.obj as XLDownloadDBBean
                val taskInfo = XLTaskHelper.instance().getTaskInfo(bean.TaskId)
                bean.DownloadStatus = taskInfo.mTaskStatus
                bean.DownloadSize = taskInfo.mDownloadSize
                bean.Speed = taskInfo.mDownloadSpeed
                bean.mCid = taskInfo.mFileSize.toString()
                when (taskInfo.mTaskStatus) {
                    0 -> {

                    }
                    1 -> {
                        var message = Message()
                        message.what = Torrent
                        message.obj = bean
                        this.sendMessageDelayed(message, 1000)
                    }
                }
                when (taskInfo.mTaskStatus) {
                    1, 2, 3 -> {
                        //保存到数据库
                        if (TextUtils.isEmpty(dao.find(bean).Name)) {
                            dao.insert(bean)
                        } else {
                            dao.update(bean)
                        }
                        //推送给RN
//                        var gson = Gson()
//                        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
//                                .emit("Querytask", ReactNativeJson.convertStringToMap(gson.toJson(bean)))
                    }
                }
            }
        }
    }

    @ReactMethod
    fun queryTask(str: String, promise: Promise) {
        var gson = Gson()
        var bean = gson.fromJson(str, XLDownloadDBBean::class.java)
        val taskInfo = XLTaskHelper.instance().getTaskInfo(bean.TaskId)
        var json = gson.toJson(taskInfo)
        var map = ReactNativeJson.convertStringToMap(json)
        promise.resolve(map)
    }

    //下载弹出框
    @ReactMethod
    fun ed2kDownloadDialog(fileStr: String) {
        var gson = Gson()
        var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
        XLED2KUtils.instance(activity, handler, XLListener).ed2kDownloadDialog(fileBean)
    }

    @ReactMethod
    fun ed2kDownload(str: String) {
        XLED2KUtils.instance(activity, handler, XLListener).ed2kDownload(str)
    }

    @ReactMethod
    fun getTaskInfo(str: String, promise: Promise) {
        var gson = Gson()
        var bean = gson.fromJson(str, XLDownloadDBBean::class.java)
        val taskInfo = XLTaskHelper.instance().getTaskInfo(bean.TaskId)
        bean.DownloadStatus = taskInfo.mTaskStatus
        bean.DownloadSize = taskInfo.mDownloadSize
        bean.Speed = taskInfo.mDownloadSpeed
        if (!TextUtils.isEmpty(taskInfo.mCid))
            bean.mCid = taskInfo.mCid
        var map = ReactNativeJson.convertStringToMap(gson.toJson(bean))
        promise.resolve(map)
    }

    //下载种子文件
    @ReactMethod
    fun scanTorrent(fileStr: String, scanTorrentCallback: Callback) {
        this.scanTorrentCallback = scanTorrentCallback
        var gson = Gson()
        var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
        XLTorrentUtils.instance(activity, handler, XLListener).scanTorrent(fileBean)
    }

    //分析种子
    @ReactMethod
    fun analyzeTorrent(path: String, magent: String) {
        XLTorrentUtils.instance(activity, handler, XLListener).analyzeTorrent(path, magent)
    }

    @ReactMethod
    fun torrentDownload(fileStr: String) {
        XLTorrentUtils.instance(activity, handler, XLListener).TorrentDownload(fileStr)
    }

    @ReactMethod
    fun stopTask(taskId: Double) {
        XLTaskHelper.instance().stopTask(taskId.toLong())
    }

    @ReactMethod
    fun deleteTask(taskId: Long, savePath: String) {
        XLTaskHelper.instance().deleteTask(taskId, savePath)
    }

    @ReactMethod
    fun requestPermission(perCallback: Callback) {
        XLDownloadModule.perCallback = perCallback
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (PermissionUtils.checkPermissionAllGranted(activity, perms)) {
            XLDownloadModule.perCallback!!.invoke(true)
        } else {
            ActivityCompat.requestPermissions(activity, perms, MainActivity.XLDownload)
        }
    }

    @ReactMethod
    fun paly(savePath: String, title: String) {
        var filePath = if (savePath.endsWith("/")) {
            savePath + title
        } else {
            "$savePath/$title"
        }
        var url = XLTaskHelper.instance().getLoclUrl(filePath)
        var uri = Uri.parse(url)
        // 让系统选择播放器来播放流媒体视频
        var intent = Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/*");
        activity.startActivity(intent)
//        var intent = Intent(activity, VideoActivity::class.java)
//        intent.putExtra("url", url)
//        activity.startActivity(intent)
        activity.finish()
    }

    @ReactMethod
    fun playLocal(savePath: String, title: String) {
        var filePath = if (savePath.endsWith("/")) {
            savePath + title
        } else {
            "$savePath/$title"
        }
        var file = File(filePath)
        if (file.exists()) {
            var uri = Uri.parse(filePath)
            var intent = Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
            activity.startActivity(intent)
            activity.finish()
        }
    }

    fun EmitTaskId(bean: XLDownloadDBBean) {
        val gson = Gson()
        val map = ReactNativeJson.convertStringToMap(gson.toJson(bean))
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("EmitTaskId", map)
    }

    val XLListener: XLListener = object : XLListener {
        override fun hasTorrent() {
            if (scanTorrentCallback != null)
                scanTorrentCallback!!.invoke()
        }

        override fun emitTaskId(bean: XLDownloadDBBean) {
            EmitTaskId(bean)
        }
    }
}


