package me.aki.estore.domain;

import java.util.List;

/**
 * Created by Aki on 2017/2/21.
 */
public class Page<T> {
    private final static int pageSize = Constant.DEFAULT_PAGE_SIZE;
    private int totalRecord;
    private int totalPage;
    private int currentPage;
    private List<T> dataList;

    public Page() {}


    public Page(int totalRecord, int totalPage, int currentPage, List<T> dataList) {
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.dataList = dataList;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
