package cf.movie.slmovie.main.search.bean

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cf.movie.slmovie.R
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

/**
 * Created by 包俊 on 2017/8/12.
 */

class SearchAdapter(private var movies: ArrayList<SearchResult.Movies>?) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    fun refresh(movies: ArrayList<SearchResult.Movies>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_hotmovie, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies!![position]
        val uri = Uri.parse(movie.post)
        holder.simpleDraweeView.setImageURI(uri)
        holder.tv_name.text = movie.name
        if (movie.details != null) {
            holder.tv_location.text = "地区：" + movie.details!!.location!!
            holder.tv_actor.text = "主演：" + movie.details!!.actor!!
            holder.tv_year.text = "上映年代：" + movie.details!!.year!!
            if (movie.details!!.isTV) {
                holder.tv_status.text = movie.details!!.status
                holder.tv_type.visibility = View.GONE
                holder.tv_status.visibility = View.VISIBLE
            } else {
                holder.tv_type.text = "类型：" + movie.details!!.type!!
                holder.tv_type.visibility = View.VISIBLE
                holder.tv_status.visibility = View.GONE
            }
            holder.tv_location.visibility = View.VISIBLE
            holder.tv_actor.visibility = View.VISIBLE
            holder.tv_year.visibility = View.VISIBLE
        } else {
            holder.tv_location.visibility = View.GONE
            holder.tv_actor.visibility = View.GONE
            holder.tv_type.visibility = View.GONE
            holder.tv_status.visibility = View.GONE
            holder.tv_year.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return movies!!.size
    }

    fun getMovies(position: Int): SearchResult.Movies {
        return movies!![position]
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView) as SimpleDraweeView
        val tv_name = itemView.findViewById(R.id.tv_name) as TextView
        val tv_year = itemView.findViewById(R.id.tv_year) as TextView
        val tv_location = itemView.findViewById(R.id.tv_location) as TextView
        val tv_type = itemView.findViewById(R.id.tv_type) as TextView
        val tv_actor = itemView.findViewById(R.id.tv_actor) as TextView
        val tv_status = itemView.findViewById(R.id.tv_status) as TextView
        val tv_douban = itemView.findViewById(R.id.tv_douban) as TextView

        init {
            tv_douban.visibility = View.GONE
        }
    }
}
