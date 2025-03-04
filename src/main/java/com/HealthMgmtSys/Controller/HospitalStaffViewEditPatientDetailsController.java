package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.Database.Databases;
import com.HealthMgmtSys.Database.PatientDAO;
import com.HealthMgmtSys.model.Patient;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import com.HealthMgmtSys.HealthCareManagementSystem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class HospitalStaffViewEditPatientDetailsController {

    @FXML
    private TextField searchText;

    @FXML
    private TableView<Patient> employeeTable;

    private ObservableList<Patient> employeeData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        populateEmployeeData();
    }

    private void populateEmployeeData() {
        employeeData.clear();

        PatientDAO empDao = new PatientDAO();
        Connection connection = Databases.getConnection();

        try {
            List<Patient> employeeList = empDao.getAllEmployees(connection);
            employeeData.addAll(employeeList);

            List<String> empId = new ArrayList<>();
            for (Patient e : employeeList) {
                empId.add(String.valueOf(e.getId()));
            }
            listOfEmployeesId.setItems(FXCollections.observableArrayList(empId));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        employeeData.clear();
        String searchTextQuery = searchText.getText();
        PatientDAO empDao = new PatientDAO();
        Connection connection = Databases.getConnection();
        List<Patient> employeeList = null;
        try {
            employeeList = empDao.searchEmployees(searchTextQuery);
            employeeData.addAll(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void backToHomeButtonClicked() throws IOException {
        HealthCareManagementSystem.setRoot("HospitalStaffPortal");
    }

    @FXML
    private void saveChanges() {
        for (Patient employee : employeeTable.getItems()) {
            try (Connection connection = Databases.getConnection()) {
                PatientDAO employeeDAO = new PatientDAO();
                employeeDAO.updateEmployee(employee, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private ComboBox<String> listOfEmployeesId;

    @FXML
    private void handleComboBoxSelect() throws SQLException {
        int employeeId = Integer.parseInt(listOfEmployeesId.getValue());
        genderComboBox.getItems().addAll("Male", "Female");

        PatientDAO employeeDAO = new PatientDAO();

        Patient employee = employeeDAO.getEmployeeById(employeeId, Databases.getConnection());

        if (employee != null) {
            firstNameTextField.setText(employee.getFirstName());

            lastNameTextField.setText(employee.getLastName());

            dateOfBirthPicker.setValue(employee.getDateOfBirth().toLocalDate());

            genderComboBox.setValue(employee.getGender());

            address1TextField.setText(employee.getAddress1());

            address2TextField.setText(employee.getAddress2());
            phoneTextField.setText(employee.getPhone());

            employeeIdTextField.setText(employee.getId() + "");

            startDatePicker.setValue(employee.getStartDate().toLocalDate());

            jobTitleTextField.setText("Diabetes");

            bankNameTextField.setText(employee.getBankName());

            bsbTextField.setText(employee.getBsb());

            accountNameTextField.setText(employee.getAccountName());

            accountNumberTextField.setText(employee.getAccountNumber());

        } else {
            showAlert("Something went wrong while fetching Patients details.");
        }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Patient Details");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField address1TextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField employeeIdTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField jobTitleTextField;

    @FXML
    private TextField bankNameTextField;

    @FXML
    private TextField bsbTextField;

    @FXML
    private TextField accountNameTextField;

    @FXML
    private TextField accountNumberTextField;

    @FXML
    private void updateButtonClicked() throws SQLException, IOException {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String dateOfBirth = dateOfBirthPicker.getValue().toString();
        String gender = genderComboBox.getValue();
        String address1 = address1TextField.getText();
        String address2 = address2TextField.getText();
        String phone = phoneTextField.getText();
        String employeeId = employeeIdTextField.getText();
        String startDate = startDatePicker.getValue().toString();
        String jobTitle = jobTitleTextField.getText();
        String bankName = bankNameTextField.getText();
        String bsb = bsbTextField.getText();
        String accountName = accountNameTextField.getText();
        String accountNumber = accountNumberTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty()
                || gender == null || address1.isEmpty() || phone.isEmpty()
                || startDate.isEmpty() || bankName.isEmpty() || bsb.isEmpty()
                || accountName.isEmpty() || accountNumber.isEmpty()) {
            showAlert("Please fill in all required fields.");
            return;
        }

        Connection connection = Databases.getConnection();

        String updateSql = "UPDATE patients SET first_name=?, last_name=?, date_of_birth=?, gender=?,"
                + " address1=?, address2=?, phone=?,  "
                + "appointmentDate=?, InsuranceName=?, patient_ref_no=?, currentMedications=?, health_pol_no=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSql);

        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, dateOfBirth);
        preparedStatement.setString(4, gender);
        preparedStatement.setString(5, address1);
        preparedStatement.setString(6, address2);
        preparedStatement.setString(7, phone);
        preparedStatement.setString(8, startDate);
        preparedStatement.setString(9, bankName);
        preparedStatement.setString(10, bsb);
        preparedStatement.setString(11, accountName);
        preparedStatement.setString(12, accountNumber);
        preparedStatement.setInt(13, Integer.parseInt(employeeId));

        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0) {
            showAlert("Appointment Scheduled and updated info successfully!");
        } else {
            showAlert("No changes were made to Patient details.");
        }

        HealthCareManagementSystem.setRoot("HospitalStaffPortal");

    }
}
