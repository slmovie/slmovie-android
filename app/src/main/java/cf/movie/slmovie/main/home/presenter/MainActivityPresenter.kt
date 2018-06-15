package cf.movie.slmovie.main.home.presenter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.home.model.CheckUpdateBean
import cf.movie.slmovie.main.home.model.CheckUpdateModel
import cf.movie.slmovie.main.home.ui.MainActivity
import cf.movie.slmovie.server.Constant
import cf.movie.slmovie.server.HtmlCode
import cf.movie.slmovie.utils.DownloadManagerUtil
import cf.movie.slmovie.utils.PermissionUtils
import java.io.File

/**
 * Created by 包俊 on 2018/5/16.
 */
class MainActivityPresenter(private val context: Activity) {
    private var checkUpdateModel: CheckUpdateModel? = null
    private var downloadManager: DownloadManagerUtil? = null

    fun checkUpdate() {
        checkUpdateModel = CheckUpdateModel(context, object : BaseReqListener<CheckUpdateBean> {
            override fun success(result: CheckUpdateBean) {
                if (result.movies!!.version) {
                    var builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.AlertDialog);
                    builder.setTitle("升级提示")
                    builder.setMessage(result.movies!!.info)
                    builder.setPositiveButton("确定") { dialog, p1 ->
                        if (PermissionUtils.checkPermissionAllGranted(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                            downloadAPK()
                            dialog?.dismiss()
                        } else {
                            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MainActivity.XLDownload)
                        }
                    }
                    builder.setNegativeButton("取消") { dialog, p1 -> dialog?.dismiss() }
                    builder.show()
                }
            }

            override fun failed(errorCode: String?, errorMsg: String?) {

            }
        })
        checkUpdateModel!!.start()
    }

    fun downloadAPK() {
        downloadManager = DownloadManagerUtil(context)
        var file = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/slys.apk")
        if (file.exists())
            file.delete()
        downloadManager!!.download(Constant.WEBROOT + HtmlCode.APP, Uri.fromFile(File(Environment.getExternalStorageDirectory().absolutePath + "/Download/slys.apk")))
    }

}