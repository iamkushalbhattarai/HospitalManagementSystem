package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.model.AppConstants;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.HealthMgmtSys.HealthCareManagementSystem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PatientPortalController {

    @FXML
    public void clockInButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("enter_hours");
    }

    @FXML
    public void employeeDetailsButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("ViewPatientDetails");
    }

    @FXML
    public void payrollButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("ViewInvoice");
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event) throws IOException {
        AppConstants.appConstMap = new HashMap<>();
        HealthCareManagementSystem.setRoot("Primary");
    }

    public void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Patient Portal");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
