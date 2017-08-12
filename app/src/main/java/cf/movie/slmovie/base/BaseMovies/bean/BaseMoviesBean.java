package cf.movie.slmovie.base.BaseMovies.bean;

import java.util.ArrayList;

import cf.movie.slmovie.bean.DetailsBean;
import cf.movie.slmovie.bean.StatusBean;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesBean {

    private StatusBean status;
    private ArrayList<movies> movies;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ArrayList<BaseMoviesBean.movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<BaseMoviesBean.movies> movies) {
        this.movies = movies;
    }


    public class movies {
        private String name, address, post, douban, year;
        private DetailsBean details;

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getDouban() {
            return douban;
        }

        public void setDouban(String douban) {
            this.douban = douban;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }

}
