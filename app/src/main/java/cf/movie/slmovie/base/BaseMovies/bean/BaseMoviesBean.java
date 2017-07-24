package cf.movie.slmovie.base.BaseMovies.bean;

import java.util.ArrayList;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseMoviesBean {

    private status status;
    private ArrayList<movies> movies;

    public BaseMoviesBean.status getStatus() {
        return status;
    }

    public void setStatus(BaseMoviesBean.status status) {
        this.status = status;
    }

    public ArrayList<BaseMoviesBean.movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<BaseMoviesBean.movies> movies) {
        this.movies = movies;
    }

    public class status {
        private String code;
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public class movies {
        private String name, address, post, douban, year;
        private details details;

        public BaseMoviesBean.details getDetails() {
            return details;
        }

        public void setDetails(BaseMoviesBean.details details) {
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

    public class details {
        private String name, year, location, type, actor, director, othername, IMDB, status;
        private boolean TV;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getOthername() {
            return othername;
        }

        public void setOthername(String othername) {
            this.othername = othername;
        }

        public String getIMDB() {
            return IMDB;
        }

        public void setIMDB(String IMDB) {
            this.IMDB = IMDB;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isTV() {
            return TV;
        }

        public void setTV(boolean TV) {
            this.TV = TV;
        }
    }
}
