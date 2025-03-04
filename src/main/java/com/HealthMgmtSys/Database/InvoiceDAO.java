package com.HealthMgmtSys.Database;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.HealthMgmtSys.model.Invoice;
import com.HealthMgmtSys.model.MedicalInvoiceSlips;

public class InvoiceDAO {

    public boolean addPayroll(Invoice payroll, Connection connection) throws SQLException {
        String sql = "INSERT INTO Invoice (patient_id, billing_date, medical_frequency) VALUES (?, ?, ?)";
        int rowsAffected;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payroll.getEmployeeId());
            statement.setDate(2, payroll.getWeekEndingDate());
            statement.setDouble(3, payroll.getHoursWorked());

            rowsAffected = statement.executeUpdate();
        }

        return rowsAffected > 0;
    }

    public List<Invoice> getPayrollByEmployeeId(int employeeId, Connection connection) throws SQLException {
        List<Invoice> payrollList = new ArrayList<>();
        String sql = "SELECT * FROM Invoice WHERE patient_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Invoice payroll = createPayrollFromResultSet(resultSet);
                    payrollList.add(payroll);
                }
            }
        }

        return payrollList;
    }

    private Invoice createPayrollFromResultSet(ResultSet resultSet) throws SQLException {
        Invoice payroll = new Invoice();
        payroll.setId(resultSet.getInt("id"));
        payroll.setEmployeeId(resultSet.getInt("patient_id"));
        payroll.setWeekEndingDate(resultSet.getDate("billing_date"));
        payroll.setHoursWorked(resultSet.getDouble("medical_frequency"));

        return payroll;
    }

    public List<String> getPayslipDatesByEmployeeId(int employeeId, Connection connection) {
        List<String> payslipDates = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            String sql = "SELECT DISTINCT pay_date FROM Med_Invoice_Slip WHERE patient_id = ? order by 1 desc";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String payDate = resultSet.getString("pay_date");
                payslipDates.add(payDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payslipDates;
    }

    public MedicalInvoiceSlips getPayslip(int employeeId, Date payslipDate) {
        Connection connection = Databases.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        MedicalInvoiceSlips payslip = null;

        try {
            String sql = "SELECT * FROM Med_Invoice_Slip WHERE patient_id = ? AND pay_date = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setDate(2, payslipDate);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int payslipId = resultSet.getInt("id");
                BigDecimal netPay = resultSet.getBigDecimal("paid_tot_income");
                payslip = new MedicalInvoiceSlips(payslipId, employeeId, payslipDate, netPay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return payslip;
    }
}
