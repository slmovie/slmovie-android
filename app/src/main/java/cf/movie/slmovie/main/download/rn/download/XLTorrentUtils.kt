package cf.movie.slmovie.main.download.rn.download

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.Toast
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.dialog.XLDownloadDialog.XLDownloadDialog
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadModule.Companion.ScanTorrent
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.utils.OutsideDownloadUtils
import com.google.gson.Gson
import com.xunlei.downloadlib.XLTaskHelper
import com.xunlei.downloadlib.parameter.TorrentInfo
import org.json.JSONObject
import java.io.File

/**
 * Created by 包俊 on 2018/6/13.
 */
class XLTorrentUtils(var activity: Activity, var handler: Handler) {

    //下载种子文件
    fun scanTorrent(fileStr: String) {
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
            handler!!.sendMessage(message)
        }
    }

    private var torrentMediaIndexs: IntArray? = null
    private var torrentUnmediaIndexs: IntArray? = null

    //分析种子
    fun analyzeTorrent(path: String, magent: String) {
        var torrentInfo = XLTaskHelper.instance().getTorrentInfo(path)
        var currentPlayMediaIndex = initTorrentIndex(torrentInfo)
        when (currentPlayMediaIndex.size) {
        //没有视频文件
            0 -> {
                Toast.makeText(activity, "种子没有可播放文件,下载地址已复制至剪切板", Toast.LENGTH_LONG).show()
                OutsideDownloadUtils.copy(activity, magent)
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

    fun TorrentDownload(bean: XLDownloadDBBean): Long {
        var torrentInfo = XLTaskHelper.instance().getTorrentInfo(bean.TorrentPath)
        var currentPlayMediaIndex = initTorrentIndex(torrentInfo)
        return XLTaskHelper.instance().addTorrentTask(bean.TorrentPath, bean.SavePath, getTorrentDeselectedIndexs(currentPlayMediaIndex[0]))
    }

    private fun initTorrentIndex(torrentInfo: TorrentInfo): ArrayList<Int> {
        var currentPlayMediaIndexs = ArrayList<Int>()
        if (torrentInfo != null && torrentInfo!!.mSubFileInfo != null) {
            val mediaIndexs = ArrayList<Int>()
            val unmediaIndexs = ArrayList<Int>()
            for (i in torrentInfo.mSubFileInfo.indices) {
                val torrentFileInfo = torrentInfo!!.mSubFileInfo[i]
                if (XLDownloadUtils.isMediaFile(torrentFileInfo.mFileName)) {
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