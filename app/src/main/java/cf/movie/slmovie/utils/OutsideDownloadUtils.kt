package cf.movie.slmovie.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by 包俊 on 2018/6/7.
 */
class OutsideDownloadUtils {
    companion object {
        //唤醒系统下载器
        fun start(context: Context, url: String): Boolean {
            var isFind = true
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addCategory("android.intent.category.DEFAULT")
                context.startActivity(intent)
            } catch (e: Exception) {
                isFind = false
                e.printStackTrace()
            }
            return isFind
        }

        //复制到剪切板
        fun copy(context: Context, url: String) {
            val myClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("text", url)
            myClipboard.primaryClip = myClip
        }
    }
}