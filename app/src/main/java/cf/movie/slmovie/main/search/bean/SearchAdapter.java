package cf.movie.slmovie.main.search.bean;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cf.movie.slmovie.R;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<SearchResult.Movies> movies;

    public SearchAdapter(ArrayList<SearchResult.Movies> movies) {
        this.movies = movies;
    }

    public void refresh(ArrayList<SearchResult.Movies> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotmovie, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchResult.Movies movie = movies.get(position);
        Uri uri = Uri.parse(movie.getPost());
        holder.simpleDraweeView.setImageURI(uri);
        holder.tv_name.setText(movie.getName());
        if (movie.getDetails() != null) {
            holder.tv_location.setText("地区：" + movie.getDetails().getLocation());
            holder.tv_actor.setText("主演：" + movie.getDetails().getActor());
            holder.tv_year.setText("上映年代：" + movie.getDetails().getYear());
            if (movie.getDetails().isTV()) {
                holder.tv_status.setText(movie.getDetails().getStatus());
                holder.tv_type.setVisibility(View.GONE);
                holder.tv_status.setVisibility(View.VISIBLE);
            } else {
                holder.tv_type.setText("类型：" + movie.getDetails().getType());
                holder.tv_type.setVisibility(View.VISIBLE);
                holder.tv_status.setVisibility(View.GONE);
            }
            holder.tv_location.setVisibility(View.VISIBLE);
            holder.tv_actor.setVisibility(View.VISIBLE);
            holder.tv_year.setVisibility(View.VISIBLE);
        } else {
            holder.tv_location.setVisibility(View.GONE);
            holder.tv_actor.setVisibility(View.GONE);
            holder.tv_type.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.GONE);
            holder.tv_year.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public SearchResult.Movies getMovies(int position) {
        return movies.get(position);
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView simpleDraweeView;
        private TextView tv_name, tv_year, tv_location, tv_type, tv_actor, tv_status, tv_douban;


        public SearchViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_actor = (TextView) itemView.findViewById(R.id.tv_actor);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_douban = (TextView) itemView.findViewById(R.id.tv_douban);
            tv_douban.setVisibility(View.GONE);
        }

    }
}
