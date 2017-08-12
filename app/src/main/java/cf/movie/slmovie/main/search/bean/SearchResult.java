package cf.movie.slmovie.main.search.bean;

import java.util.ArrayList;

import cf.movie.slmovie.bean.DetailsBean;
import cf.movie.slmovie.bean.FilesBean;
import cf.movie.slmovie.bean.StatusBean;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class SearchResult {
    private StatusBean status;
    private ArrayList<Movies> movies;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }

    public class Movies {
        private String name, post, describe, id;
        private DetailsBean details;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

    }

}
