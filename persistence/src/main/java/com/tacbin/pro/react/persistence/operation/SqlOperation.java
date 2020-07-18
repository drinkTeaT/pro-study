package com.tacbin.pro.react.persistence.operation;

import com.tacbin.pro.react.persistence.config.DataBaseConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description sql操作
 * Author liuweibin
 **/
public abstract class SqlOperation {
    protected Connection conn;
    protected String tableName;
    protected String outputPath;

    public SqlOperation(String tableName, String outputPath) {
        conn = DataBaseConnection.getConnection();
        this.tableName = tableName;
        this.outputPath = outputPath;
    }


    public abstract void doOperate();

    protected List<String> queryTableFields(HashMap<String, String> excludeFieldsMap) {
        // 获取表中字段
        List<String> fields = new ArrayList<>();
        try {
            String fieldSql = "select COLUMN_NAME from information_schema.COLUMNS  where TABLE_NAME= '" + tableName + "'";
            PreparedStatement fieldsStatement = conn.prepareStatement(fieldSql);
            ResultSet result = fieldsStatement.executeQuery();
            int i = 1;
            while (result.next()) {
                String fieldName = result.getString(i);
                if (excludeFieldsMap.containsKey(fieldName)) {
                    continue;
                }
                fields.add(fieldName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fields;
    }

    protected final void writeIntoFile(List<String> sqls) {
        try (FileWriter writer = new FileWriter(outputPath, true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (int i = 0; i < sqls.size(); i++) {
                bw.append(sqls.get(i) + "\n");
            }
            writer.flush();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
