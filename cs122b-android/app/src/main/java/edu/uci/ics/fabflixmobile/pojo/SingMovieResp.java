package edu.uci.ics.fabflixmobile.pojo;

public class SingMovieResp {

    private Integer message;
    private Movie data;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Movie getData() {
        return data;
    }

    public void setData(Movie data) {
        this.data = data;
    }
}
