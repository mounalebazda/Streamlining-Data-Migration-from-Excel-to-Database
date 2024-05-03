package com.company.classes;

import java.util.HashMap;
import java.util.Map;

public class TableRow {
    
    private Map<String, String> columnData = new HashMap<>();

    public void addColumnData(String columnName, String columnValue) {
        columnData.put(columnName, columnValue);
    }

    public String getColumnName(String columnName) {
        return columnData.get(columnName);
    }

    public String getColumnValue(String columnName) {
        return columnData.get(columnName);
    }

    public Iterable<String> getColumnName() {
        return columnData.keySet();
    }
     public Map<String, String> getColumnData() {
        return columnData;
    }
}
