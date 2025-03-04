package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import com.HealthMgmtSys.HealthCareManagementSystem;
import javafx.scene.control.Label;

public class MedicalInvoiceReportController {

    @FXML
    private ChoiceBox<String> dateRangeChoiceBox;

    @FXML
    private DatePicker startDateTextField;

    @FXML
    private DatePicker endDateTextField;

    @FXML
    private Label finalReport;

    @FXML
    private void initialize() throws SQLException {
        dateRangeChoiceBox.getItems().addAll("Week", "Month", "Year");
    }

    @FXML
    private void generateReport() {
        String selectedDateRange = dateRangeChoiceBox.getValue();
        String startDate = startDateTextField.getValue().toString();
        String endDate = endDateTextField.getValue().toString();

        String report = generatePayrollReport(selectedDateRange, startDate, endDate);

        System.out.println(report);
        finalReport.setText(report);

    }

    private String generatePayrollReport(String dateRange, String startDate, String endDate) {
        StringBuilder report = new StringBuilder();
        report.append("Patient Invoice Report\n");
        report.append("Patient Service Time:W/M/Y ").append(dateRange).append("\n");
        report.append("Service Date: ").append(startDate).append("\n");
        report.append("Appointment Date: ").append(endDate).append("\n");
        report.append("Total Medical Payment: $80000\n");
        report.append("Tax Deduction from medicine: $200\n");
        report.append("Insurance Coverage: $50\n");

        return report.toString();
    }

    @FXML
    private void backToHomeButtonClicked() throws IOException {
        HealthCareManagementSystem.setRoot("HospitalStaffPOrtal");
    }
}
