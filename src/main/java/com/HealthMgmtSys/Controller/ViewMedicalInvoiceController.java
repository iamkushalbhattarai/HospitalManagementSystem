package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.model.MedicalInvoiceSlips;
import com.HealthMgmtSys.Database.Databases;
import com.HealthMgmtSys.Database.PatientDAO;
import com.HealthMgmtSys.Database.InvoiceDAO;
import com.HealthMgmtSys.model.AppConstants;
import com.HealthMgmtSys.model.Patient;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewMedicalInvoiceController {

    @FXML
    private Button logoutButton;
    @FXML
    private Button viewPayslipButton;
    @FXML
    private Button backToHomeButton;

    @FXML
    private ComboBox<String> payDateComboBox;

    @FXML
    private ListView<String> payslipListView;

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label payPeriodLabel;

    @FXML
    private Label payDateLabel;

    @FXML
    private Label grossIncomeLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label unpaidAbsenceLabel;

    @FXML
    private Label netPayLabel;

    @FXML
    private void initialize() throws SQLException {
        int employeeId = Integer.parseInt(AppConstants.appConstMap.get("employeeId"));

        InvoiceDAO payrollDAO = new InvoiceDAO();
        List<String> payslipDatesByEmployeeId = payrollDAO.getPayslipDatesByEmployeeId(employeeId, Databases.getConnection());

        ObservableList<String> payDates = FXCollections.observableArrayList(payslipDatesByEmployeeId);
        payDateComboBox.setItems(payDates);
    }

    @FXML
    private void viewPayslipButtonClicked() throws SQLException {
        int employeeId = Integer.parseInt(AppConstants.appConstMap.get("employeeId"));
        PatientDAO employeeDAO = new PatientDAO();
        Patient employee = employeeDAO.getEmployeeById(employeeId, Databases.getConnection());

        if (employee != null) {
            employeeNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());
            employeeIdLabel.setText(employee.getId() + "");
        }
        String dateParam = payDateComboBox.getValue();
        Date payslipDate = Date.valueOf(dateParam);
        InvoiceDAO pDao = new InvoiceDAO();
        MedicalInvoiceSlips payslip = pDao.getPayslip(employeeId, payslipDate);

        if (payslip != null) {
            java.util.Date currentDate = payslip.getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            java.util.Date firstDayOfMonth = calendar.getTime();

            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            java.util.Date lastDayOfMonth = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String firstDayOfMonthStr = sdf.format(firstDayOfMonth);
            String lastDayOfMonthStr = sdf.format(lastDayOfMonth);
            payPeriodLabel.setText(firstDayOfMonthStr + " to " + lastDayOfMonthStr);

            payDateLabel.setText(payslip.getDate().toLocalDate() + "");
            grossIncomeLabel.setText("$" + payslip.getNetPay());
            taxLabel.setText("$50.00");
            BigDecimal netPay = payslip.getNetPay();
            BigDecimal subtractedAmount = netPay.subtract(new BigDecimal("50"));
            unpaidAbsenceLabel.setText("$0.00");
            netPayLabel.setText("$" + subtractedAmount);

        }
    }

    @FXML
    private void backToHomeButtonClicked() throws IOException {
        HealthCareManagementSystem.setRoot("Patient_portal");
    }

    @FXML
    private void handleLogout() throws IOException {
        AppConstants.appConstMap = new HashMap<>();
        HealthCareManagementSystem.setRoot("Primary");
    }

}
