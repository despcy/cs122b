package com.cs122b.project.Fabflix.Response;

import java.util.List;

public class ListGenResponse {

    private Integer message;
    private List<String> data;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
