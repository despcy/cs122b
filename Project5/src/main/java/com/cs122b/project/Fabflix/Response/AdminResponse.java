package com.cs122b.project.Fabflix.Response;

public class AdminResponse {
    private Integer message;
    private DashData data;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public DashData getData() {
        return data;
    }

    public void setData(DashData data) {
        this.data = data;
    }
}
