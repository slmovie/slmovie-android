package cf.movie.slmovie.dialog.XLDownloadDialog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cf.movie.slmovie.R

/**
 * Created by 包俊 on 2018/6/7.
 */
class FileInfoAdapter(val list: ArrayList<XLDownloadBean>) : RecyclerView.Adapter<FileInfoAdapter.FileInfoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileInfoHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_xldownload_dialog, parent, false)
        return FileInfoHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FileInfoHolder, position: Int) {
        var bean = list[position]
        holder.tv_name.text = bean.name.trim()
        holder.tv_size.text = bean.size
    }


    class FileInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        var tv_size = itemView.findViewById<TextView>(R.id.tv_size)
    }

}