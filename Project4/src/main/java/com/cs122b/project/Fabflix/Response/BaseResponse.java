package com.cs122b.project.Fabflix.Response;

public class BaseResponse {

    private Integer message;
    private Object data;

    public BaseResponse(int msg){
        this.message = msg;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
