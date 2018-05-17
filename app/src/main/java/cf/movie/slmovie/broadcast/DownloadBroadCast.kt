package cf.movie.slmovie.broadcast

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cf.movie.slmovie.utils.DownloadManagerUtil

/**
 * Created by 包俊 on 2018/5/17.
 */
class DownloadBroadCast() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var downloadManager = DownloadManagerUtil(context!!)
        var action = intent?.action
        var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        when (action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                downloadManager.query(id!!)
            }
            DownloadManager.ACTION_NOTIFICATION_CLICKED -> {
                downloadManager.query(id!!)
            }
        }
    }

}