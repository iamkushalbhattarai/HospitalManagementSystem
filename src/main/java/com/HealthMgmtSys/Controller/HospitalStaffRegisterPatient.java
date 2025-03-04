package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.Database.Databases;
import com.HealthMgmtSys.Database.PatientDAO;
import com.HealthMgmtSys.model.AppConstants;
import com.HealthMgmtSys.model.Patient;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class HospitalStaffRegisterPatient {

    @FXML
    private Button logoutButton;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField address1;
    @FXML
    private TextField address2;
    @FXML
    private TextField phone;
    @FXML
    private TextField empid;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField jobtitle;
    @FXML
    private TextField bankName;
    @FXML
    private TextField bsb;
    @FXML
    private TextField accountName;
    @FXML
    private TextField accountNumber;

    @FXML
    private void initialize() {
        gender.getItems().addAll("Male", "Female");
    }

    @FXML
    private void handleLogout() throws Exception {
        AppConstants.appConstMap = new HashMap<>();
        try {
            HealthCareManagementSystem.setRoot("secondary");
        } catch (IOException ex) {
            Logger.getLogger(HospitalStaffRegisterPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registerNewEmployee() throws ParseException, SQLException, IOException {
        String firstNameValue = firstName.getText();
        String lastNameValue = lastName.getText();
        String dateOfBirthValue = dateOfBirth.getValue().toString();
        String genderValue = gender.getValue();
        String address1Value = address1.getText();
        String address2Value = address2.getText();
        String phoneValue = phone.getText();
        String startDateValue = startDate.getValue().toString();
        String bankNameValue = bankName.getText();
        String bsbValue = bsb.getText();
        String accountNameValue = accountName.getText();
        String accountNumberValue = accountNumber.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Patient newEmployee = new Patient();
        newEmployee.setFirstName(firstNameValue);
        newEmployee.setLastName(lastNameValue);
        try {
            newEmployee.setDateOfBirth(new Date(dateFormat.parse(dateOfBirthValue).getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(HospitalStaffRegisterPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
        newEmployee.setGender(genderValue);
        newEmployee.setAddress1(address1Value);
        newEmployee.setAddress2(address2Value);
        newEmployee.setPhone(phoneValue);
        try {
            newEmployee.setStartDate(new Date(dateFormat.parse(startDateValue).getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(HospitalStaffRegisterPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
        newEmployee.setBankName(bankNameValue);
        newEmployee.setBsb(bsbValue);
        newEmployee.setAccountName(accountNameValue);
        newEmployee.setAccountNumber(accountNumberValue);

        PatientDAO employeeDAO = new PatientDAO();
        Connection connection = Databases.getConnection();
        boolean success = employeeDAO.insertEmployee(newEmployee, connection);
        if (success) {
            showAlert("Successfully Added a new Patient to our Hospital.");
            HealthCareManagementSystem.setRoot("HospitalStaffPortal");
        } else {
            showAlert("Something went wrong while fetching Patient details.");
        }

    }

    @FXML
    private void backToHomeButtonClicked() throws IOException {
        HealthCareManagementSystem.setRoot("HospitalStaffPortal");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Patient Details");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
