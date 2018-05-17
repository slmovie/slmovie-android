package cf.movie.slmovie.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.File
import java.io.FilenameFilter

/**
 * Created by 包俊 on 2018/5/17.
 */
class DownloadManagerUtil(private var context: Context) {

    //下载apk
    fun download(url: String, path: Uri): Long {
        var request = DownloadManager.Request(Uri.parse(url))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("双龙影视");
        request.setDescription("正在更新中......")
        request.setDestinationUri(path)
        var manager: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return manager.enqueue(request)
    }

    //查询下载状态
    fun query(downloadId: Long) {
        var manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        var query = DownloadManager.Query()
        query.setFilterById(downloadId)
        var cursor = manager.query(query)
        if (!cursor.moveToFirst()) {
            cursor.close()
            return
        }
        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
        cursor.close()
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            var fileUri: Uri? = manager.getUriForDownloadedFile(downloadId)
            if (fileUri != null) {
                installAPK(context, fileUri)
            }
        } else {
            var intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    companion object {
        fun installAPK(context: Context, uri: Uri) {
            if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                var file = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/slys.apk")
                var apkUri = FileProvider.getUriForFile(context, "cf.movie.slmovie.fileprovider", file)
                var install = Intent(Intent.ACTION_VIEW)
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(apkUri, "application/vnd.android.package-archive")
                context.startActivity(install);
            } else {
                var install = Intent(Intent.ACTION_VIEW)
                install.setDataAndType(uri, "application/vnd.android.package-archive")
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(install)
            }
        }
    }

}