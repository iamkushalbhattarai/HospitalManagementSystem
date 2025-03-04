package com.HealthMgmtSys.Database;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalStaffDAO {

    public boolean authenticateStaff(String username, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = Databases.getConnection();
        try {

            String sql = "SELECT * FROM hospitalStaff WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
