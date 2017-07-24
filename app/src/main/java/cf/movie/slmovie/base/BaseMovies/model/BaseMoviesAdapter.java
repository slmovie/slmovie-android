package cf.movie.slmovie.base.BaseMovies.model;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseMovies.bean.BaseMoviesBean;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesAdapter extends RecyclerView.Adapter<BaseMoviesAdapter.HotMovieViewHolder> {

    private ArrayList<BaseMoviesBean.movies> movies;

    public BaseMoviesAdapter(ArrayList<BaseMoviesBean.movies> movies) {
        this.movies = movies;
    }

    public void refresh(ArrayList<BaseMoviesBean.movies> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public HotMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotmovie, parent, false);
        return new HotMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotMovieViewHolder holder, int position) {
        BaseMoviesBean.movies movie = movies.get(position);
        Uri uri = Uri.parse(movie.getPost());
        holder.simpleDraweeView.setImageURI(uri);
        holder.tv_name.setText(movie.getName());
        holder.tv_year.setText("上映年代：" + movie.getYear());
        holder.tv_location.setText("地区：" + movie.getDetails().getLocation());
        holder.tv_douban.setText("评分：" + movie.getDouban());
        holder.tv_actor.setText("主演：" + movie.getDetails().getActor());
        if (movie.getDetails().isTV()) {
            holder.tv_status.setText(movie.getDetails().getStatus());
            holder.tv_type.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
        } else {
            holder.tv_type.setText("类型：" + movie.getDetails().getType());
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class HotMovieViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView simpleDraweeView;
        private TextView tv_name, tv_year, tv_location, tv_type, tv_douban, tv_actor, tv_status;

        public HotMovieViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_douban = (TextView) itemView.findViewById(R.id.tv_douban);
            tv_actor = (TextView) itemView.findViewById(R.id.tv_actor);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }
}
