package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.Database.Databases;
import com.HealthMgmtSys.model.AppConstants;
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PatientLoginController {
    
    @FXML
    private TextField employeeId;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginError;

    @FXML
      private void handleLogin() throws IOException {
 
        int enteredEmployeeId = Integer.parseInt(employeeId.getText());
        String enteredPassword = password.getText();

        boolean isAuthenticated = authenticateEmployee(enteredEmployeeId, enteredPassword);

        if (isAuthenticated) {
            AppConstants.appConstMap.put("employeeId", String.valueOf(enteredEmployeeId));
            HealthCareManagementSystem.setRoot("Patient_portal");
        } else {
            loginError.setText("Invalid credentials. Please try again.");
        }
    }

    public boolean authenticateEmployee(int employeeId, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Databases.getConnection();

            String sql = "SELECT id FROM patients WHERE id = ? AND password = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); 

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
    }

}
