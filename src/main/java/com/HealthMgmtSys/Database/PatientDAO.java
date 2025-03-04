package com.HealthMgmtSys.Database;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.HealthMgmtSys.model.Patient;

public class PatientDAO {

    public List<Patient> getAllEmployees(Connection connection) throws SQLException {
        List<Patient> employees = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Patient employee = createEmployeeFromResultSet(resultSet);
                employees.add(employee);
            }
        }

        return employees;
    }

    public Patient getEmployeeById(String employeeId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient employee = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employee = createEmployeeFromResultSet(resultSet);
                }
            }
        }

        return employee;
    }

    public Patient getEmployeeById(int employeeId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient employee = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employee = createEmployeeFromResultSet(resultSet);
                }
            }
        }

        return employee;
    }

    public boolean updateEmployeeDetails(Patient employee, Connection connection) throws SQLException {
        String sql = "UPDATE patients SET first_name=?, last_name=?, address1=?, address2=?, phone=?, gender=?, date_of_birth=?, appointmentDate=?, enddate=?, InsuranceName=?, patient_ref_no=?, currentMedications=?, health_pol_no=? WHERE id=?";
        int rowsAffected;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getAddress1());
            statement.setString(4, employee.getAddress2());
            statement.setString(5, employee.getPhone());
            statement.setString(6, employee.getGender());

            statement.setDate(7,
                    employee.getDateOfBirth() != null ? new java.sql.Date(employee.getDateOfBirth().getTime()) : null);
            statement.setDate(8,
                    employee.getStartDate() != null ? new java.sql.Date(employee.getStartDate().getTime()) : null);
            if (employee.getEndDate() != null) {
                statement.setDate(9, new java.sql.Date(employee.getEndDate().getTime()));
            } else {
                statement.setDate(9, null);
            }
            statement.setString(10, employee.getBankName());
            statement.setString(11, employee.getBsb());
            statement.setString(12, employee.getAccountName());
            statement.setString(13, employee.getAccountNumber());
            statement.setInt(14, employee.getId());

            rowsAffected = statement.executeUpdate();
        }

        return rowsAffected > 0;
    }

    private Patient createEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        Patient employee = new Patient();
        employee.setId(resultSet.getInt("id"));
        employee.setUsername(resultSet.getString("username"));
        employee.setPassword(resultSet.getString("password"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setAddress1(resultSet.getString("address1"));
        employee.setAddress2(resultSet.getString("address2"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setGender(resultSet.getString("gender"));
        employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
        employee.setStartDate(resultSet.getDate("appointmentDate"));
        employee.setEndDate(resultSet.getDate("enddate"));
        employee.setBankName(resultSet.getString("InsuranceName"));
        employee.setBsb(resultSet.getString("patient_ref_no"));
        employee.setAccountName(resultSet.getString("currentMedications"));
        employee.setAccountNumber(resultSet.getString("health_pol_no"));

        return employee;
    }

    public boolean authenticateEmployee(int employeeId, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Databases.getConnection();

            String sql = "SELECT id FROM patients WHERE id = ? AND password = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean insertEmployee(Patient employee, Connection connection) throws SQLException {

        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, address1, address2, phone, appointmentDate, InsuranceName, patient_ref_no, currentMedications, health_pol_no, username, password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setDate(3, new java.sql.Date(employee.getDateOfBirth().getTime()));
            statement.setString(4, employee.getGender());
            statement.setString(5, employee.getAddress1());
            statement.setString(6, employee.getAddress2());
            statement.setString(7, employee.getPhone());
            statement.setDate(8, new java.sql.Date(employee.getStartDate().getTime()));
            statement.setString(9, employee.getBankName());
            statement.setString(10, employee.getBsb());
            statement.setString(11, employee.getAccountName());
            statement.setString(12, employee.getAccountNumber());
            statement.setInt(13, getNextUsernameNumber(connection));
            statement.setInt(14, getNextUsernameNumber(connection));

            rowsAffected = statement.executeUpdate();
        }

        return rowsAffected > 0;
    }

    public int getNextUsernameNumber(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(1) + 1 AS username FROM patients";
        int nextUsernameNumber = 1;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nextUsernameNumber = resultSet.getInt("username");
                }
            }
        }

        return nextUsernameNumber;
    }

    public List<Patient> searchEmployees(String searchText) {
        List<Patient> employeeList = new ArrayList<>();

        Connection connection = null;

        try {
            connection = Databases.getConnection();

            List<Patient> employees = new ArrayList<>();
            String sql = "SELECT * FROM patients WHERE " + "first_name LIKE ? OR " + "last_name LIKE ? OR "
                    + "id LIKE ? OR phone LIKE ? ";

            PreparedStatement statement = connection.prepareStatement(sql);

            searchText = "%" + searchText + "%";
            statement.setString(1, searchText);
            statement.setString(2, searchText);
            statement.setString(3, searchText);
            statement.setString(4, searchText);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Patient employee = createEmployeeFromResultSet(resultSet);
                employees.add(employee);
            }

            return employees;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    public boolean updateEmployee(Patient employee, Connection connection) throws SQLException {
        String updateQuery = "UPDATE patients SET first_name=?, last_name=?, date_of_birth=?, gender=?, "
                + "address1=?, address2=?, phone=?, appointmentDate=?, enddate=?, InsuranceName=?, patient_ref_no=?, "
                + "currentMedications=?, health_pol_no=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(employee.getDateOfBirth().getTime()));
            preparedStatement.setString(4, employee.getGender());
            preparedStatement.setString(5, employee.getAddress1());
            preparedStatement.setString(6, employee.getAddress2());
            preparedStatement.setString(7, employee.getPhone());
            preparedStatement.setDate(8, new java.sql.Date(employee.getStartDate().getTime()));
            preparedStatement.setDate(9, null);
            preparedStatement.setString(10, employee.getBankName());
            preparedStatement.setString(11, employee.getBsb());
            preparedStatement.setString(12, employee.getAccountName());
            preparedStatement.setString(13, employee.getAccountNumber());
            preparedStatement.setInt(14, employee.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

}
