package com.mziuri.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLController {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCConnector.getConnection();

            String query = "SELECT * FROM candyshop";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Process each row
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCController.close(resultSet);
            JDBCController.close(preparedStatement);
            JDBCController.close(connection);
        }
    }
}
