package com.sportsevents.api.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaginationRequest {

    @NotBlank
    private String type;

    @Min(0)
    private int pageNum;

    @Min(1)
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
