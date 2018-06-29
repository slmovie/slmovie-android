package cf.movie.slmovie.main.detailOS.view.Fragment.UrlFragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.dialog.ProgressDialog.ProgressDialog
import cf.movie.slmovie.main.detailOS.model.event.DetailOsEvent
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLED2KUtils
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLListener
import cf.movie.slmovie.main.download.rn.download.DownloadUtis.XLTorrentUtils
import cf.movie.slmovie.main.download.rn.download.XLDownloadModule
import cf.movie.slmovie.main.home.ui.MainActivity
import cf.movie.slmovie.main.search.ui.SearchActivity
import cf.movie.slmovie.utils.OutsideDownloadUtils
import cf.movie.slmovie.utils.PermissionUtils
import cf.movie.slmovie.utils.SpanTextClick
import com.xunlei.downloadlib.XLTaskHelper
import kotlinx.android.synthetic.main.fragment_detailos_download.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by 包俊 on 2018/5/25.
 */
class UrlFragment : BaseFragment() {
    private var dialog: ProgressDialog? = null
    override val contentLayout: Int
        get() = R.layout.fragment_detailos_download

    override fun initGui() {
    }

    override fun initData() {

    }

    override fun initAction() {
    }

    internal var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == XLDownloadModule.ScanTorrent) {
                val json = msg.obj as JSONObject
                val taskInfo = XLTaskHelper.instance().getTaskInfo(json.getLong("taskId"))
                when (taskInfo.mTaskStatus) {
                    0, 1 -> {
                        var json1 = JSONObject()
                        json1.put("taskId", json.getLong("taskId"))
                        json1.put("savePath", json.optString("savePath"))
                        json1.put("magent", json.optString("magent"))
                        var message = Message()
                        message.what = XLDownloadModule.ScanTorrent
                        message.obj = json1
                        this.sendMessageDelayed(message, 1000)
                    }
                    2 -> {
                        if (dialog != null)
                            dialog!!.dismiss()
                        analyzeTorrent(json.optString("savePath"), json.optString("magent"))
                    }
                    3 -> {
                        if (dialog != null)
                            dialog!!.dismiss()
                        Toast.makeText(activity, "下载失败", Toast.LENGTH_LONG).show()
                        if (!OutsideDownloadUtils.start(activity!!, json.optString("magent")))
                            OutsideDownloadUtils.copy(activity!!, json.optString("magent"))
                        Toast.makeText(activity, "无可下载应用，下载地址已复制到剪切板！", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(result: DetailOsEvent) {
        if (result.isStatus) {
            val linearLayoutManager = LinearLayoutManager(activity!!)
            linearLayoutManager.orientation = OrientationHelper.VERTICAL
            recycler.layoutManager = linearLayoutManager
            var adapter = UrlsAdapter(activity!!, result.movie?.movies?.files!!)
            adapter.setOnItemClickListener(object : UrlsAdapter.onItemClickListener {
                override fun itemClick(file: FilesBean) {
                    pushDownload(file)
                }

            })
            recycler.adapter = adapter
            recycler.visibility = View.VISIBLE
        } else {
            SpanTextClick.setSpan(tv_search, "点我去搜索") {
                var intent = Intent(activity, SearchActivity::class.java)
                activity!!.startActivity(intent)
            }
            tv_search.visibility = View.VISIBLE
        }
    }

    private fun pushDownload(file: FilesBean) {
        when {
            file.download!!.startsWith("ed2k://") -> {
                val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (PermissionUtils.checkPermissionAllGranted(activity!!, perms)) {
                    XLED2KUtils.instance(activity as Activity).ed2kDownloadDialog(file)
                } else {
                    ActivityCompat.requestPermissions(activity!!, perms, MainActivity.XLDownload)
                }
            }
            file.download!!.startsWith("magnet:?") -> {
                val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (PermissionUtils.checkPermissionAllGranted(activity!!, perms)) {
                    if (dialog == null)
                        dialog = ProgressDialog(activity!!, "分析种子中......")
                    dialog!!.show()
                    XLTorrentUtils.instance(activity!!, handler, XLListener).scanTorrent(file)
                } else {
                    ActivityCompat.requestPermissions(activity!!, perms, MainActivity.XLDownload)
                }
            }
            else -> try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(file.download!!))
                intent.addCategory("android.intent.category.DEFAULT")
                activity!!.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(activity, file.download!!, Toast.LENGTH_LONG).show()
                val myClipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip = ClipData.newPlainText("text", file.download!!)
                myClipboard.primaryClip = myClip
                var builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialog);
                builder.setTitle("提示")
                builder.setMessage("启动下载器失败，下载地址已复制到剪切板，请自行粘贴下载")
                builder.setPositiveButton("确定") { dialog, p1 ->
                    dialog.dismiss()
                }
                builder.show()
                e.printStackTrace()
            }
        }
    }

    fun analyzeTorrent(path: String, magent: String) {
        XLTorrentUtils.instance(activity!!, handler, XLListener).analyzeTorrent(path, magent)
    }

    val XLListener: XLListener = object : XLListener {
        override fun hasTorrent() {
            if (dialog != null)
                dialog!!.dismiss()
        }

        override fun emitTaskId(bean: XLDownloadDBBean) {
        }
    }
}
