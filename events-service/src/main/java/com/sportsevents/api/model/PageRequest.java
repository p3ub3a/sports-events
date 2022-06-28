package com.sportsevents.api.model;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection 
public class PageRequest {

    @QueryParam("type") 
    @DefaultValue("home")
    private String type;

    @QueryParam("pageNum")
    @DefaultValue("0")
    private int pageNum;

    @QueryParam("pageSize")
    @DefaultValue("10")
    private int pageSize;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", pageNum='" + getPageNum() + "'" +
            ", pageSize='" + getPageSize() + "'" +
            "}";
    }

}
