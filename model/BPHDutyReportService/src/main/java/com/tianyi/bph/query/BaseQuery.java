package com.tianyi.bph.query;

public class BaseQuery {

    public enum SortType {
        DESC, ASC
    };

    private String   sort;
    private SortType sortType = SortType.ASC;
    private int      pageSize;
    //当前页码
    private int      pageNo;

    public BaseQuery() {
    	super();
    }

    public BaseQuery(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getBegin() {
        return pageSize * (pageNo > 0 ? (pageNo - 1) : 0);
    }

    public Integer getEnd() {
        Integer rst = pageSize + getBegin();
        return (rst == null || rst < 1) ? 100000 : rst;
    }

}
