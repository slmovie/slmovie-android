package cf.movie.slmovie.application

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log

/**
 * Created by 包俊 on 2017/7/19.
 */

class MyApplication : Application() {
    var instance: MyApplication? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun getPackageName(): String {
        return if (Log.getStackTraceString(Throwable()).contains("com.xunlei.downloadlib")) {
            "com.xunlei.downloadprovider"
        } else super.getPackageName()
    }

    override fun getPackageManager(): PackageManager {
        return if (Log.getStackTraceString(Throwable()).contains("com.xunlei.downloadlib")) {
            DelegateApplicationPackageManager(super.getPackageManager())
        } else super.getPackageManager()
    }
}
