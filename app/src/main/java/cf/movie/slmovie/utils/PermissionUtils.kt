package cf.movie.slmovie.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.main.home.ui.MainActivity


/**
 * Created by 包俊 on 2018/6/15.
 */
class PermissionUtils {
    companion object {
        /**
         * 检查是否拥有指定的所有权限
         */
        fun checkPermissionAllGranted(activity: Activity, permissions: Array<String>): Boolean {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 只要有一个权限没有被授予, 则直接返回 false
                    return false
                }
            }
            return true
        }

        fun requestResult(activity: Activity, grantResults: IntArray, permissions: Array<out String>, grant: () -> Unit, denied: () -> Unit) {
            var isAllGranted = true
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }
            if (isAllGranted) {
                Toast.makeText(activity, "用户授权成功！", Toast.LENGTH_SHORT).show()
                grant
            } else {
                denied
                Toast.makeText(activity, "用户授权失败！", Toast.LENGTH_SHORT).show()
                PermissionUtils.tips(activity, MainActivity.XLDownload, permissions)
            }
        }

        fun tips(activity: Activity, requestCode: Int, permissions: Array<out String>) {
            var builder = AlertDialog.Builder(activity, R.style.AlertDialog)
            builder.setTitle("提示")
            builder.setMessage("请打开读取手机状态与读写存储权限")
            builder.setPositiveButton("确定") { dialog, p1 ->
                var per = true
                permissions.forEach {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) per = false
                }
                if (per) {
                    ActivityCompat.requestPermissions(activity, permissions, requestCode)
                } else {
                    dialog.dismiss()
                    gotoSetting(activity)
                }
            }
            builder.setNegativeButton("取消") { dialog, p1 ->
                dialog.dismiss()
            }
            builder.show()
        }

        fun gotoSetting(activity: Activity) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + activity.getPackageName())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            activity.startActivity(intent)
        }
    }
}