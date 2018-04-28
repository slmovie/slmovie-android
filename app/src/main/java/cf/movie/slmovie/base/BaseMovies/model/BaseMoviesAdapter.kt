package cf.movie.slmovie.base.BaseMovies.model

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

/**
 * Created by 包俊 on 2017/7/21.
 */

class BaseMoviesAdapter(var movies: ArrayList<BaseMoviesBean.movie>) : RecyclerView.Adapter<BaseMoviesAdapter.HotMovieViewHolder>() {

    val serialVersionUID: Long = -2579386368620865598L

    fun refresh(movies: ArrayList<BaseMoviesBean.movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotMovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_hotmovie, parent, false)
        return HotMovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HotMovieViewHolder, position: Int) {
        val movie = movies!![position]
        val uri = Uri.parse(movie.post)
        holder.simpleDraweeView.setImageURI(uri)
        holder.tv_name.text = movie.name
        holder.tv_year.text = "上映年代：" + movie.year!!
        holder.tv_douban.text = "评分：" + movie.douban!!
        if (movie.details != null) {
            holder.tv_location.text = "地区：" + movie.details!!.location!!
            holder.tv_actor.text = "主演：" + movie.details!!.actor!!
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
        } else {
            holder.tv_location.visibility = View.GONE
            holder.tv_actor.visibility = View.GONE
            holder.tv_type.visibility = View.GONE
            holder.tv_status.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return movies!!.size
    }

    fun getMovies(position: Int): BaseMoviesBean.movie {
        return movies!![position]
    }

    class HotMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView) as SimpleDraweeView
        var tv_name = itemView.findViewById(R.id.tv_name) as TextView
        var tv_year = itemView.findViewById(R.id.tv_year) as TextView
        var tv_location = itemView.findViewById(R.id.tv_location) as TextView
        var tv_type = itemView.findViewById(R.id.tv_type) as TextView
        var tv_douban = itemView.findViewById(R.id.tv_douban) as TextView
        var tv_actor = itemView.findViewById(R.id.tv_actor) as TextView
        var tv_status = itemView.findViewById(R.id.tv_status) as TextView
    }
}
