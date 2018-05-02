package cf.movie.slmovie.utils

import android.os.Environment

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Created by 包俊 on 2017/8/15.
 */

object DownloadUtil {
    private val okHttpClient: OkHttpClient

    init {
        okHttpClient = OkHttpClient()
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun download(url: String, saveDir: String, listener: OnDownloadListener) {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener.onDownloadFailed()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var `is`: InputStream? = null
                val buf = ByteArray(2048)
                var fos: FileOutputStream? = null
                // 储存下载文件的目录
                val savePath = isExistDir(saveDir)
                try {
                    `is` = response.body().byteStream()
                    val total = response.body().contentLength()
                    val file = File(savePath, getNameFromUrl(url))
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    var len: Int
                    while (true) {
                        len = `is`.read(buf)
                        if (len == -1)
                            break
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onDownloading(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener.onDownloadSuccess()
                } catch (e: Exception) {
                    listener.onDownloadFailed()
                } finally {
                    try {
                        if (`is` != null)
                            `is`.close()
                    } catch (e: IOException) {
                    }

                    try {
                        if (fos != null)
                            fos.close()
                    } catch (e: IOException) {
                    }

                }
            }
        })
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    @Throws(IOException::class)
    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess()

        /**
         * @param progress 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }

}