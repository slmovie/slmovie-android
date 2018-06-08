package cf.movie.slmovie.dialog.XLDownloadDialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import cf.movie.slmovie.R
import kotlinx.android.synthetic.main.dialog_xldownload.*

/**
 * Created by 包俊 on 2018/6/6.
 */
class XLDownloadDialog(context: Context?, var list: ArrayList<XLDownloadBean>) : AlertDialog(context) {

    private var listener: onClickDownloadListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_xldownload)
        initData()
        initAction()
    }

    private fun initData() {
        if (list != null && list.size != 0) {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = OrientationHelper.VERTICAL
            recycler!!.layoutManager = linearLayoutManager
            var adapter = FileInfoAdapter(list)
            recycler.adapter = adapter
            tv_path.text = list[0]!!.savePath
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

    fun setData(list: ArrayList<XLDownloadBean>) {
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