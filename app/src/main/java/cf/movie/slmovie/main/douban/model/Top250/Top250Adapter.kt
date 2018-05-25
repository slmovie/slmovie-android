package cf.movie.slmovie.main.douban.model.Top250

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cf.movie.slmovie.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by 包俊 on 2018/5/21.
 */
class Top250Adapter(var context: Context, var movies: ArrayList<Top250Bean.subject>) : RecyclerView.Adapter<Top250Adapter.HotMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top250Adapter.HotMovieViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_doubanmovie, parent, false)
        return Top250Adapter.HotMovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Top250Adapter.HotMovieViewHolder, position: Int) {
        val movie = movies!![position]
        val uri = Uri.parse(movie.images?.medium)
        var options = RequestOptions()
        options.placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .fallback(R.drawable.ic_launcher)
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(holder.iv_post)
        holder.tv_name.text = movie.title
        holder.tv_year.text = "上映年代：" + movie.year!!
        holder.tv_douban.text = "评分：" + movie.rating?.average!!
        holder.tv_ranking.text = (position + 1).toString()

        var directors = ""
        if (movie.directors?.size!! > 0) {
            movie.directors?.forEach {
                if (TextUtils.isEmpty(directors)) {
                    directors = it.name!!
                } else {
                    directors = directors + '、' + it.name
                }
            }
        }
        holder.tv_directer.text = "导演：" + directors

        var casts = ""
        if (movie.casts?.size!! > 0) {
            movie.casts?.forEach {
                if (TextUtils.isEmpty(casts)) {
                    casts = it.name!!
                } else {
                    casts = casts + '、' + it.name
                }
            }
        }
        holder.tv_actor.text = "演员：" + casts

        var genres = ""
        if (movie.genres?.size!! > 0) {
            movie.genres?.forEach {
                if (TextUtils.isEmpty(genres)) {
                    genres = it!!
                } else {
                    genres = genres + '、' + it
                }
            }
        }
        holder.tv_type.text = "类型：" + genres

    }

    override fun getItemCount(): Int {
        return movies!!.size
    }

    public fun getMovies(position: Int): Top250Bean.subject {
        return movies!![position]
    }

    class HotMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_post = itemView.findViewById(R.id.iv_post) as ImageView
        var tv_name = itemView.findViewById(R.id.tv_name) as TextView
        var tv_year = itemView.findViewById(R.id.tv_year) as TextView
        var tv_directer = itemView.findViewById(R.id.tv_directer) as TextView
        var tv_type = itemView.findViewById(R.id.tv_type) as TextView
        var tv_douban = itemView.findViewById(R.id.tv_douban) as TextView
        var tv_actor = itemView.findViewById(R.id.tv_actor) as TextView
        var tv_ranking = itemView.findViewById(R.id.tv_ranking) as TextView
    }
}