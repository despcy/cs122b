package com.cs122b.project.Fabflix.Response;

import com.cs122b.project.Fabflix.model.Movie;
import com.cs122b.project.Fabflix.model.Star;

public class StarResponse {
    private Integer message;
    private Star star;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }
}
