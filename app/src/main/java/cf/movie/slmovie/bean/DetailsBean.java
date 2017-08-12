package cf.movie.slmovie.bean;

/**
 * Created by 包俊 on 2017/8/12.
 */

public class DetailsBean {
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
