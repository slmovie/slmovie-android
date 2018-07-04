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
class XLDownloadDialog(context: Context?, var bean: XLDownloadDBBean) : AlertDialog(context) {

    private var listener: onClickDownloadListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_xldownloadui)
        initData()
        initAction()
    }

    private fun initData() {
        tv_path.text = bean.SavePath
        tv_name.text = bean.Name
        tv_size.text = XLDownloadUtils.convertFileSize(bean.TotalSize)
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

    fun setData(bean: XLDownloadDBBean) {
        this.bean = bean
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