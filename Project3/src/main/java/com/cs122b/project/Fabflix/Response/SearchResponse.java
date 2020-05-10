
package com.cs122b.project.Fabflix.Response;

import java.util.HashMap;
import java.util.Map;

public class SearchResponse {

    private Integer message;
    private Data data;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
