package cf.movie.slmovie.main.download.rn.download

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.db.XLDownloadDao
import cf.movie.slmovie.dialog.XLDownloadDialog.XLDownloadDialog
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kName
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils.Companion.getEd2kSize
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.main.home.ui.MainActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.OutsideDownloadUtils
import cf.movie.slmovie.utils.PermissionUtils
import cf.movie.slmovie.utils.ReactNativeJson
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.xunlei.downloadlib.DownloadException
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
    private var XLTorrentUtils: XLTorrentUtils? = null

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
                        analyzeTorrent(json.optString("savePath"), json.optString("magent"))
                    }
                    3 -> {
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
    fun ed2kDownload(str: String) {
        var gson = Gson()
        var bean = gson.fromJson(str, XLDownloadDBBean::class.java)
        try {
            var taskId = XLTaskHelper.instance().addThunderTask(bean.DownloadPath, bean.SavePath, bean.Name)
            bean.TaskId = taskId
            emitTaskId(bean)
            var message = Message()
            message.what = ED2K
            message.obj = bean
            handler.sendMessage(message)
        } catch (e: DownloadException) {
            Toast.makeText(activity, "下载失败，请重试", Toast.LENGTH_LONG).show()
            bean.DownloadStatus = 3
        }
    }

    @ReactMethod
    fun sysDownload(fileStr: String) {
        var gson = Gson()
        var fileBean = gson.fromJson(fileStr, FilesBean::class.java)
        if (!OutsideDownloadUtils.start(reactContext, fileBean!!.download!!))
            OutsideDownloadUtils.copy(reactContext, fileBean!!.download!!)
        Toast.makeText(reactContext, "无可下载应用，下载地址已复制到剪切板！", Toast.LENGTH_LONG).show()
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
    fun scanTorrent(fileStr: String) {
        XLTorrentUtils = XLTorrentUtils(activity, handler)
        XLTorrentUtils!!.scanTorrent(fileStr)
    }

    //分析种子
    @ReactMethod
    fun analyzeTorrent(path: String, magent: String) {
        XLTorrentUtils = XLTorrentUtils(activity, handler)
        XLTorrentUtils!!.analyzeTorrent(path, magent)
    }

    @ReactMethod
    fun torrentDownload(fileStr: String) {
        var gson = Gson()
        var bean = gson.fromJson(fileStr, XLDownloadDBBean::class.java)
        XLTorrentUtils = XLTorrentUtils(activity, handler)
        var taskId = XLTorrentUtils!!.TorrentDownload(bean)
        bean.TaskId = taskId
        emitTaskId(bean)
        var message = Message()
        message.what = Torrent
        message.obj = bean
        handler.sendMessage(message)
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

    fun emitTaskId(bean: XLDownloadDBBean) {
        val gson = Gson()
        val map = ReactNativeJson.convertStringToMap(gson.toJson(bean))
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("EmitTaskId", map)
    }
}


