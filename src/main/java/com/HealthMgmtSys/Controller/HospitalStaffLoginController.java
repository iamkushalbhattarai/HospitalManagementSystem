package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.Database.MedicalStaffDAO;
import com.HealthMgmtSys.model.AppConstants;
import java.io.IOException;
import javafx.fxml.FXML;
import com.HealthMgmtSys.HealthCareManagementSystem;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HospitalStaffLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isValid = validateStaffLogin(username, password);

        if (isValid) {
            AppConstants.appConstMap.put("employeeId", username);
            HealthCareManagementSystem.setRoot("HospitalStaffPortal");
        } else {
            loginErrorLabel.setText("Invalid username or password");
        }
    }

    private boolean validateStaffLogin(String username, String password) {
        try {
            MedicalStaffDAO staffDAO = new MedicalStaffDAO();
            return staffDAO.authenticateStaff(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
