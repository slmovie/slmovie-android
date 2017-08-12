package cf.movie.slmovie.main.detail.bean;

import java.util.ArrayList;

/**
 * Created by 包俊 on 2017/8/6.
 */

public class MovieDetail {
    private status status;
    private movies movies;

    public MovieDetail.status getStatus() {
        return status;
    }

    public void setStatus(MovieDetail.status status) {
        this.status = status;
    }

    public MovieDetail.movies getMovies() {
        return movies;
    }

    public void setMovies(MovieDetail.movies movies) {
        this.movies = movies;
    }

    class status {
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
        private String name, post, describe;
        private details details;
        private ArrayList<files> files;
        private ArrayList<String> detail;

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

        public details getDetails() {
            return details;
        }

        public void setDetails(details details) {
            this.details = details;
        }

        public ArrayList<files> getFiles() {
            return files;
        }

        public void setFiles(ArrayList<files> files) {
            this.files = files;
        }

        public ArrayList<String> getDetail() {
            return detail;
        }

        public void setDetail(ArrayList<String> detail) {
            this.detail = detail;
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

    public class files {
        private String fileSize, download, name;

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
