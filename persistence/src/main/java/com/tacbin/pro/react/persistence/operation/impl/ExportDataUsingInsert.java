package com.tacbin.pro.react.persistence.operation.impl;


import com.tacbin.pro.react.persistence.operation.SqlOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 生成insert的sql脚本，导出表中数据
 * Author liuweibin
 **/
public class ExportDataUsingInsert extends SqlOperation {
    private String conditionSql;
    private HashMap<String, String> excludeFieldsMap;

    public ExportDataUsingInsert(String tableName, String outputPath, String conditionSql, List<String> excludeFieldsList) {
        super(tableName, outputPath);
        this.conditionSql = " " + conditionSql + " ";
        this.excludeFieldsMap = new HashMap<>();
        excludeFieldsList.forEach(x -> excludeFieldsMap.put(x, ""));
    }

    @Override
    public void doOperate() {
        // 获取表的字段
        List<String> fields = queryTableFields(excludeFieldsMap);
        // 分页查询
        page(fields);
    }

    private void page(List<String> fields) {
        int writeAmount = 0;
        List<String> insertSqls = new ArrayList<>();
        try {
            // 总数
            String countSql = "select count(*) from " + tableName + ";";
            PreparedStatement fieldsStatement = null;
            fieldsStatement = conn.prepareStatement(countSql);
            ResultSet result = fieldsStatement.executeQuery();
            int count = result.next() ? result.getInt(1) : 0;
            // 分页查询
            int initial = 50;
            int times = count / initial + (count % initial == 0 ? 0 : 1);
            String pageSql = "";
            for (int i = 0; i < times; i++) {
                insertSqls.clear();
                String field = fields.stream().collect(Collectors.joining(","));
                pageSql = "SELECT " + field + " FROM " + tableName + conditionSql + " order by " + fields.get(0) + " LIMIT " + (i * initial) + "," + initial + ";";
                fieldsStatement = conn.prepareStatement(pageSql);
                result = fieldsStatement.executeQuery();
                while (result.next()) {
                    String sql = "insert into " + tableName + " (" + field + ") values ";
                    String value = "";
                    for (int j = 0; j < fields.size(); j++) {
                        String column = fields.get(j);
                        if (j == 0) {
                            value += "'" + result.getString(column) + "'";
                        } else {
                            value += ",'" + result.getString(column) + "'";
                        }
                    }
                    sql = sql + "(" + value + ");";
                    insertSqls.add(sql.trim());
                    writeAmount++;
                }
                writeIntoFile(insertSqls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(tableName + " 写入总条数有" + writeAmount + "条");
    }
}
