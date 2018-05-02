package cf.movie.slmovie.utils

import android.widget.Toast

import net.lingala.zip4j.core.ZipFile

import java.io.File

/**
 * Created by 包俊 on 2017/8/15.
 */

object ZipUtils {

    fun upZip(from: String, to: String): Boolean {
        var over = true
        try {
            val zip = File(from)
            val zipFile = ZipFile(zip)
            if (zipFile.isValidZipFile)
                zipFile.extractAll(to)
        } catch (e: Exception) {
            over = false
            e.printStackTrace()
        }

        return over
    }

}
