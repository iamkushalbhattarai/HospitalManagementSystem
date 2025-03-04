package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.Database.Databases;
import com.HealthMgmtSys.model.AppConstants;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.sql.PreparedStatement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MedDosesInfoController {

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button payrollButton;

    @FXML
    private Button clockInButton;

    @FXML
    private Button logoutButton;

    @FXML
    public TextField monday;

    @FXML
    public TextField tuesday;

    @FXML
    public TextField wednesday;

    @FXML
    public TextField thursday;

    @FXML
    public TextField friday;

    @FXML
    public TextField saturday;

    @FXML
    public TextField sunday;

    @FXML
    private void homeButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("Patient_portal");
    }

    @FXML
    private void profileButtonClicked(ActionEvent event) {
        showAlert("Profile Button Clicked - Functionality coming soon");
    }

    @FXML
    private void payrollButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("ViewInvoice");
    }

    @FXML
    private void clockInButtonClicked(ActionEvent event) throws IOException {
        HealthCareManagementSystem.setRoot("enter_hours");
    }

    @FXML
    private void logoutButtonClicked(ActionEvent event) throws IOException {
        AppConstants.appConstMap = new HashMap<>();
        HealthCareManagementSystem.setRoot("Primary");
    }

    @FXML
    public void submitButtonClicked(ActionEvent event) throws IOException {
        int employeeId = Integer.parseInt(AppConstants.appConstMap.get("employeeId"));

        LocalDate currentDate = LocalDate.now();
        LocalDate startdt = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Date weekStartDate = Date.valueOf(startdt);

        BigDecimal mondayHours = new BigDecimal(monday.getText());
        BigDecimal tuesdayHours = new BigDecimal(tuesday.getText());
        BigDecimal wednesdayHours = new BigDecimal(wednesday.getText());
        BigDecimal thursdayHours = new BigDecimal(thursday.getText());
        BigDecimal fridayHours = new BigDecimal(friday.getText());
        BigDecimal saturdayHours = new BigDecimal(saturday.getText());
        BigDecimal sundayHours = new BigDecimal(sunday.getText());

        try (Connection connection = Databases.getConnection()) {
            String sql = "INSERT INTO medical_frequency (patient_id, week_start_date, "
                    + "monday_hours, tuesday_hours, wednesday_hours, thursday_hours, "
                    + "friday_hours, saturday_hours, sunday_hours) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, employeeId);
                statement.setDate(2, weekStartDate);
                statement.setBigDecimal(3, mondayHours);
                statement.setBigDecimal(4, tuesdayHours);
                statement.setBigDecimal(5, wednesdayHours);
                statement.setBigDecimal(6, thursdayHours);
                statement.setBigDecimal(7, fridayHours);
                statement.setBigDecimal(8, saturdayHours);
                statement.setBigDecimal(9, sundayHours);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showAlert("Your working hours have been inserted successfully.");
        HealthCareManagementSystem.setRoot("Patient_portal");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Patient Portal");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
