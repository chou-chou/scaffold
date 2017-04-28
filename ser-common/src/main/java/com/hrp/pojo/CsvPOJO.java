package com.hrp.pojo;

import java.util.List;

/**
 * CsvPOJO
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class CsvPOJO {

    private String[] headers;
    private List<CsvRow> rows;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<CsvRow> getRows() {
        return rows;
    }

    public void setRows(List<CsvRow> rows) {
        this.rows = rows;
    }

}
