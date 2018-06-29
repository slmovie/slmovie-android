package cf.movie.slmovie.main.detailOS.view.Fragment.UrlFragment

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cf.movie.slmovie.R
import cf.movie.slmovie.bean.FilesBean
import cf.movie.slmovie.utils.SpanTextClick


/**
 * Created by 包俊 on 2018/5/28.
 */
class UrlsAdapter(var activity: Activity, var files: ArrayList<FilesBean>) : RecyclerView.Adapter<UrlsAdapter.ViewHolder>() {

    private var listener: onItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_fragment_url, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var file = files.get(position)
        var name = ""
        if (!TextUtils.isEmpty(file.fileSize)) {
            name = "[${file.fileSize}]"
        }
        name += file.name

        SpanTextClick.setSpan(holder!!.tv, name) {
            if(listener!=null)
                listener!!.itemClick(file)
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv = itemView.findViewById<TextView>(R.id.tv)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    interface onItemClickListener {
        fun itemClick(file: FilesBean)
    }
}