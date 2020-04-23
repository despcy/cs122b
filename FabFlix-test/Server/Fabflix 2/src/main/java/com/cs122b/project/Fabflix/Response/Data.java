
package com.cs122b.project.Fabflix.Response;

import com.cs122b.project.Fabflix.model.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    private long totalItem;
    private Integer curPage;
    private Integer pagesize;
    private List<Movie> movies = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalPage) {
        this.totalItem = totalPage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
