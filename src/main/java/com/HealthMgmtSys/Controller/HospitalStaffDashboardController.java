package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.HealthCareManagementSystem;
import com.HealthMgmtSys.model.AppConstants;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HospitalStaffDashboardController {

    @FXML
    private void registerEmployee(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("HospitalStaffRegisterPatient");
    }

    @FXML
    private void searchAndViewEmployee(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("SearchAndViewPatient");
    }

    @FXML
    private void editEmployeeInformation(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("HospitalStaffViewEditPatientDetails");

    }

    @FXML
    private void createReports(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("Invoice_report");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        AppConstants.appConstMap = new HashMap<>();
        HealthCareManagementSystem.setRoot("secondary");
    }
}
