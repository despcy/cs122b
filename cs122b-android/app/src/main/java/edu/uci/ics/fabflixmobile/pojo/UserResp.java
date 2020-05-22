package edu.uci.ics.fabflixmobile.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResp {
    @JsonProperty("message")
    private Integer message;

    @JsonProperty("data")
    private User data;

    public UserResp(){
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
