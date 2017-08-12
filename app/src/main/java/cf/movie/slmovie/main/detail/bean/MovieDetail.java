package cf.movie.slmovie.main.detail.bean;

import java.util.ArrayList;

import cf.movie.slmovie.bean.DetailsBean;
import cf.movie.slmovie.bean.FilesBean;
import cf.movie.slmovie.bean.StatusBean;

/**
 * Created by 包俊 on 2017/8/6.
 */

public class MovieDetail {
    private StatusBean status;
    private movies movies;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public MovieDetail.movies getMovies() {
        return movies;
    }

    public void setMovies(MovieDetail.movies movies) {
        this.movies = movies;
    }

    public class movies {
        private String name, post, describe;
        private DetailsBean details;
        private ArrayList<FilesBean> files;
        private ArrayList<String> detail;

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public ArrayList<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(ArrayList<FilesBean> files) {
            this.files = files;
        }

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

        public ArrayList<String> getDetail() {
            return detail;
        }

        public void setDetail(ArrayList<String> detail) {
            this.detail = detail;
        }
    }

}
