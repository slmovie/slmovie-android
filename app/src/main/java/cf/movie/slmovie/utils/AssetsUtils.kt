package cf.movie.slmovie.utils

import android.content.Context
import android.os.Environment

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by 包俊 on 2017/8/15.
 */

object AssetsUtils {

    /**
     * assets目录下文件复制到sd卡
     *
     * @param context
     * @param assets  assets文件名
     * @param path    复制地址
     */
    fun copy(context: Context, assets: String, path: String) {
        try {
            val outFile = File(path + File.separator + assets)
            val `is` = context.assets.open(assets)
            val fos = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var byteCount: Int
            while (true) {
                byteCount = `is`.read(buffer)
                if (byteCount == -1)
                    break
                fos.write(buffer, 0, byteCount)
            }
            fos.flush()
            `is`.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
