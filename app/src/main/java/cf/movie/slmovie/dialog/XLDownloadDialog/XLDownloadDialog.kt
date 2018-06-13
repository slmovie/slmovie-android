package cf.movie.slmovie.dialog.XLDownloadDialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import cf.movie.slmovie.R
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.main.download.rn.download.XLDownloadUtils
import kotlinx.android.synthetic.main.dialog_xldownloadui.*

/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadDialog(context: Context?, var list: ArrayList<XLDownloadDBBean>) : AlertDialog(context) {

    private var listener: onClickDownloadListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_xldownloadui)
        initData()
        initAction()
    }

    private fun initData() {
        if (list.size != 0) {
            tv_path.text = list[0].SavePath
            tv_name.text = list[0].Name
            tv_size.text = XLDownloadUtils.convertFileSize(list[0].TotalSize)
        }
    }

    private fun initAction() {
        tv_sys_download.setOnClickListener {
            if (listener != null)
                listener!!.sysDownload(this)
        }
        tv_my_download.setOnClickListener {
            if (listener != null)
                listener!!.myDownload(this)
        }
    }

    fun setData(list: ArrayList<XLDownloadDBBean>) {
        this.list = list
        initData()
    }

    fun setOnClickDownloadListner(listener: onClickDownloadListener) {
        this.listener = listener
    }

    interface onClickDownloadListener {
        fun sysDownload(dialog: XLDownloadDialog)
        fun myDownload(dialog: XLDownloadDialog)
    }

}