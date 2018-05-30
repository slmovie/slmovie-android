package cf.movie.slmovie.main.detailOS.view.Fragment.UrlFragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.utils.SpanTextClick


/**
 * Created by 包俊 on 2018/5/28.
 */
class UrlsAdapter(var activity: Activity, var files: ArrayList<FilesBean>) : RecyclerView.Adapter<UrlsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_fragment_url, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var file = files.get(position)
        var name = ""
        if (!TextUtils.isEmpty(file.fileSize)) {
            name = "[${file.fileSize}]"
        }
        name += file.name

        SpanTextClick.setSpan(holder!!.tv, name, {
            pushDownload(file.download!!)
        })
    }

    fun pushDownload(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory("android.intent.category.DEFAULT")
            activity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(activity, url, Toast.LENGTH_LONG).show()
            val myClipboard = activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("text", url)
            myClipboard.primaryClip = myClip
            var builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialog);
            builder.setTitle("提示")
            builder.setMessage("启动下载器失败，下载地址已复制到剪切板，请自行粘贴下载")
            builder.setPositiveButton("确定") { dialog, p1 ->
                dialog.dismiss()
            }
            builder.show()
            e.printStackTrace()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv = itemView.findViewById<TextView>(R.id.tv)
    }
}