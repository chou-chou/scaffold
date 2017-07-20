package com.hrp.entity.vo;

import java.util.List;

/**
 * DataTableView
 * 应对DataTables格式
 * @author KVLT
 * @date 2017-07-19.
 */
public class DataTableView<T> {

    private int draw;  // 请求次数
    private int recordsTotal;  // 总行数
    private int recordsFiltered;  // 过滤后记录数
    private List<T> data;  //data 与datatales 加载的“dataSrc"对应
    private String error;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
