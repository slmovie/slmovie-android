package cf.movie.slmovie.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object Version {
    fun getVersion(context: Context): String {
        val packageManager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return info!!.versionName
    }
}
