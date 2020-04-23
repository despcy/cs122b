package com.cs122b.project.Fabflix.Response;

public class CheckoutResponse {
    private Integer message;
    private String data;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
