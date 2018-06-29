package cf.movie.slmovie.main.download.rn.download

import android.text.TextUtils

/**
 * Created by 包俊 on 2018/6/7.
 */
class XLDownloadUtils {
    companion object {
        //格式化
        fun convertFileSize(size: Long): String {
            val kb: Long = 1024
            val mb = kb * 1024
            val gb = mb * 1024

            if (size >= gb) {
                return String.format("%.1f GB", size.toFloat() / gb)
            } else if (size >= mb) {
                val f = size.toFloat() / mb
                return String.format(if (f > 100) "%.0f M" else "%.1f M", f)
            } else if (size >= kb) {
                val f = size.toFloat() / kb
                return String.format(if (f > 100) "%.0f K" else "%.1f K", f)
            } else
                return String.format("%d B", size)
        }

        //判断是否是视频
        fun isMediaFile(fileName: String): Boolean {
            when (getFileExt(fileName)) {
                ".avi", ".mp4", ".m4v", ".mkv", ".mov", ".mpeg", ".mpg", ".mpe", ".rm", ".rmvb", ".3gp", ".wmv", ".asf", ".asx", ".dat", ".vob", ".m3u8" -> return true
                else -> return false
            }
        }

        fun getFileExt(fileName: String): String {
            if (TextUtils.isEmpty(fileName)) return ""
            val p = fileName.lastIndexOf('.')
            return if (p != -1) {
                fileName.substring(p).toLowerCase()
            } else ""
        }

        fun getEd2kName(url: String): String {
            var split = url.split("|")
            return split[2]
        }

        fun getEd2kSize(url: String): Long {
            var split = url.split("|")
            var size = split[3].toLong()
            return size
        }
    }
}