package com.cs122b.project.Fabflix.Response;

import com.cs122b.project.Fabflix.model.Star;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StarResponse {
    private Integer message;
    private Star star;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    @JsonProperty("data")
    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }
}
