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
import java.sql.SQLException;
import java.util.List;
import com.HealthMgmtSys.HealthCareManagementSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchAndViewPatientController {

    @FXML
    private TextField searchText;

    @FXML
    private TableView<Patient> employeeTable;

    @FXML
    private TableColumn<Patient, Integer> employeeIdColumn;

    @FXML
    private TableColumn<Patient, String> firstNameColumn;

    @FXML
    private TableColumn<Patient, String> lastNameColumn;

    @FXML
    private TableColumn<Patient, String> dateOfBirthColumn;

    @FXML
    private TableColumn<Patient, String> genderColumn;

    @FXML
    private TableColumn<Patient, String> address1Column;

    @FXML
    private TableColumn<Patient, String> address2Column;

    @FXML
    private TableColumn<Patient, String> phoneColumn;

    @FXML
    private TableColumn<Patient, String> startDateColumn;

    @FXML
    private TableColumn<Patient, String> endDateColumn;

    @FXML
    private TableColumn<Patient, String> bankNameColumn;

    @FXML
    private TableColumn<Patient, String> bsbColumn;

    @FXML
    private TableColumn<Patient, String> accountNameColumn;

    @FXML
    private TableColumn<Patient, String> accountNumberColumn;

    private ObservableList<Patient> employeeData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address1Column.setCellValueFactory(new PropertyValueFactory<>("address1"));
        address2Column.setCellValueFactory(new PropertyValueFactory<>("address2"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        bankNameColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        bsbColumn.setCellValueFactory(new PropertyValueFactory<>("bsb"));
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        employeeTable.setItems(employeeData);

        populateEmployeeData();
    }

    private void populateEmployeeData() {
        employeeData.clear();

        PatientDAO empDao = new PatientDAO();
        Connection connection = Databases.getConnection();

        try {
            List<Patient> employeeList = empDao.getAllEmployees(connection);
            employeeData.addAll(employeeList);
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
}
