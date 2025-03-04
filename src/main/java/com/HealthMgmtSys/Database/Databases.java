
package com.HealthMgmtSys.Database;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.ResultSet;



public class Databases {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "healthmgmt_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "kushal@062";

    static {
        // Register the JDBC driver during class initialization
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME + "?useSSL=false", DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createDatabaseAndTables() {
        Connection connection = null;
        try {
            // Establish a connection to the MySQL server without specifying a database
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Creating the database if it doesn't exist
            createDatabaseIfNotExists(connection, DB_NAME);
            
             System.out.println("Database '" + DB_NAME + "' created.");

            // Now Switch to the database
            connection = DriverManager.getConnection(JDBC_URL + DB_NAME + "?useSSL=false", DB_USER, DB_PASSWORD);

            // Create the tables if they don't exist
            createTablesIfNotExists(connection);
            
              System.out.println("Tables created.");

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

    private static void createDatabaseIfNotExists(Connection connection, String dbName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + dbName);
        statement.executeUpdate();
    }

    private static void createTablesIfNotExists(Connection connection) throws SQLException {
        createEmployeesTable(connection);
        createPayrollTable(connection);
        createPayslipsTable(connection);
        createStaffTable(connection);
        createHoursWorkedTable(connection);
    }

private static void createEmployeesTable(Connection connection) throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS patients (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(255) NOT NULL," +
            "password VARCHAR(255) NOT NULL," +
            "first_name VARCHAR(255)," +
            "last_name VARCHAR(255)," +
            "address1 VARCHAR(255)," +
            "address2 VARCHAR(255)," +
            "phone VARCHAR(255)," +
            "gender VARCHAR(255)," +
            "date_of_birth DATE," +
            "appointmentDate DATE," +
            "enddate DATE," +
            "InsuranceName VARCHAR(255)," +
            "patient_ref_no VARCHAR(255)," +
            "currentMedications VARCHAR(255)," +
            "health_pol_no VARCHAR(255))";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.executeUpdate();
    insertDefaultEmployee(connection);
}

    
private static void insertDefaultEmployee(Connection connection) throws SQLException {
    // Check if an employee with the specified username already exists
    String checkEmployeeSql = "SELECT id FROM patients WHERE username = ?";
    PreparedStatement checkEmployeeStatement = connection.prepareStatement(checkEmployeeSql);
    checkEmployeeStatement.setString(1, "1");
    ResultSet employeeResultSet = checkEmployeeStatement.executeQuery();

    if (!employeeResultSet.next()) {
        // Insert default employee
        String employeeUsername = "1";
        String employeePassword = "1"; 

        String insertEmployeeSql = "INSERT INTO patients (username, password, first_name, last_name, address1, address2, phone, gender, date_of_birth, appointmentDate, enddate, InsuranceName, patient_ref_no, currentMedications, health_pol_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertEmployeeStatement = connection.prepareStatement(insertEmployeeSql);
        insertEmployeeStatement.setString(1, employeeUsername);
        insertEmployeeStatement.setString(2, employeePassword);
        insertEmployeeStatement.setString(3, "Kushal");
        insertEmployeeStatement.setString(4, "Bhattarai");
        insertEmployeeStatement.setString(5, "123 Main St");
        insertEmployeeStatement.setString(6, "Apt 456");
        insertEmployeeStatement.setString(7, "123-456-7890");
        insertEmployeeStatement.setString(8, "Male");
        insertEmployeeStatement.setDate(9, Date.valueOf("1990-01-01")); 
        insertEmployeeStatement.setDate(10, Date.valueOf("2023-08-01")); 
         insertEmployeeStatement.setNull(11, java.sql.Types.DATE);
        insertEmployeeStatement.setString(12, "Bupa Australia");
        insertEmployeeStatement.setString(13, "123456789");
        insertEmployeeStatement.setString(14, "Ibuprofen");
        insertEmployeeStatement.setString(15, "987654321");

        insertEmployeeStatement.executeUpdate();
    }
}


private static void createPayrollTable(Connection connection) throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS Invoice (" +
            "Invoice_id INT AUTO_INCREMENT PRIMARY KEY," +
            "patient_id INT NOT NULL," +
            "billing_date DATE NOT NULL," +
            "medical_frequency DECIMAL(5, 2) NOT NULL," +  
            "pay_amount DECIMAL(10, 2) NOT NULL)";
           

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.executeUpdate();
     insertDefaultPayroll(connection);
   
}
private static void insertDefaultPayroll(Connection connection) throws SQLException {
    // Check if the default Invoice record for the specified employee already exists
    if (payrollRecordExists(connection, 1, Date.valueOf("2023-08-01"))) {
        System.out.println("Default Invoice record already exists, skipping insertion.");
        return;
    }

    // Example: Inserting a default Invoice record for employee with ID 1
    int employeeId = 1; 
    Date weekEndingDate = Date.valueOf("2023-08-01"); 
    BigDecimal hoursWorked = new BigDecimal("40.00"); 
    BigDecimal payAmount = new BigDecimal("1000.00"); 
    String insertSql = "INSERT INTO Invoice (patient_id, billing_date, medical_frequency, pay_amount) VALUES (?, ?, ?, ?)";
    PreparedStatement insertStatement = connection.prepareStatement(insertSql);

    insertStatement.setInt(1, employeeId);
    insertStatement.setDate(2, weekEndingDate);
    insertStatement.setBigDecimal(3, hoursWorked);
    insertStatement.setBigDecimal(4, payAmount);

    insertStatement.executeUpdate();
}

private static boolean payrollRecordExists(Connection connection, int employeeId, Date weekEndingDate) throws SQLException {
    String querySql = "SELECT COUNT(*) FROM Invoice WHERE patient_id = ? AND billing_date = ?";
    PreparedStatement queryStatement = connection.prepareStatement(querySql);

    queryStatement.setInt(1, employeeId);
    queryStatement.setDate(2, weekEndingDate);

    ResultSet resultSet = queryStatement.executeQuery();
    resultSet.next();
    int count = resultSet.getInt(1);

    return count > 0;
}


private static void createPayslipsTable(Connection connection) throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS Med_Invoice_Slip (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "patient_id INT NOT NULL," +
            "pay_date DATE NOT NULL," +
            "paid_tot_income DECIMAL(10, 2) NOT NULL," +
            "FOREIGN KEY (patient_id) REFERENCES patients(id))";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.executeUpdate();

    // Insert default payment dates for existing patients
    insertDefaultPaymentDates(connection);
}

private static void insertDefaultPaymentDates(Connection connection) throws SQLException {
    // Checking if the default payment date for the specified employee already exists
    if (paymentDateExists(connection, 1, Date.valueOf("2023-08-01"))) {
        System.out.println("Default payment date already exists, skipping insertion.");
        return;
    }


    int employeeId = 1; 
    Date defaultPaymentDate = Date.valueOf("2023-08-01"); 
    BigDecimal defaultNetPay = new BigDecimal("3400.00"); 

    String insertSql = "INSERT INTO Med_Invoice_Slip (patient_id, pay_date, paid_tot_income) VALUES (?, ?, ?)";
    PreparedStatement insertStatement = connection.prepareStatement(insertSql);

    insertStatement.setInt(1, employeeId);
    insertStatement.setDate(2, defaultPaymentDate);
    insertStatement.setBigDecimal(3, defaultNetPay);

    insertStatement.executeUpdate();
}

private static boolean paymentDateExists(Connection connection, int employeeId, Date paymentDate) throws SQLException {
    String query = "SELECT COUNT(*) FROM Med_Invoice_Slip WHERE patient_id = ? AND pay_date = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, employeeId);
    statement.setDate(2, paymentDate);
    try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }
    return false;
}

   private static void createStaffTable(Connection connection) throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS hospitalStaff (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(255) NOT NULL," +
            "password VARCHAR(255) NOT NULL)";
        
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.executeUpdate();

    // now Inserting default admin values
    insertDefaultStaff(connection);
}

private static void insertDefaultStaff(Connection connection) throws SQLException {
    // now Checking if admin exists
    String checkAdminSql = "SELECT id FROM hospitalStaff WHERE username = ?";
    PreparedStatement checkAdminStatement = connection.prepareStatement(checkAdminSql);
    checkAdminStatement.setString(1, "KushalAdmin");
    ResultSet adminResultSet = checkAdminStatement.executeQuery();

    if (!adminResultSet.next()) {
        //now  Inserting default admin
        String adminUsername = "KushalPhysician";
        String adminPassword = "physician123"; // You can change this to a secure password

        String insertAdminSql = "INSERT INTO hospitalStaff (username, password) VALUES (?, ?)";

        PreparedStatement insertAdminStatement = connection.prepareStatement(insertAdminSql);
        insertAdminStatement.setString(1, adminUsername);
        insertAdminStatement.setString(2, adminPassword);

        insertAdminStatement.executeUpdate();
    }

    // now Checking if accountant exists
    String checkAccountantSql = "SELECT id FROM hospitalStaff WHERE username = ?";
    PreparedStatement checkAccountantStatement = connection.prepareStatement(checkAccountantSql);
    checkAccountantStatement.setString(1, "KushalAccountant");
    ResultSet accountantResultSet = checkAccountantStatement.executeQuery();

    if (!accountantResultSet.next()) {
        //now  Inserting default accountant
        String accountantUsername = "KushalDentist";
        String accountantPassword = "dentist123"; // You can change this to a secure password

        String insertAccountantSql = "INSERT INTO hospitalStaff (username, password) VALUES (?, ?)";

        PreparedStatement insertAccountantStatement = connection.prepareStatement(insertAccountantSql);
        insertAccountantStatement.setString(1, accountantUsername);
        insertAccountantStatement.setString(2, accountantPassword);

        insertAccountantStatement.executeUpdate();
    }

    // Let's Check if manager exists
    String checkManagerSql = "SELECT id FROM hospitalStaff WHERE username = ?";
    PreparedStatement checkManagerStatement = connection.prepareStatement(checkManagerSql);
    checkManagerStatement.setString(1, "KushalManager");
    ResultSet managerResultSet = checkManagerStatement.executeQuery();

    if (!managerResultSet.next()) {
        // Here i am Inserting default manager
        String managerUsername = "SitaNurse";
        String managerPassword = "nurse123"; // You can change this to a secure password

        String insertManagerSql = "INSERT INTO hospitalStaff (username, password) VALUES (?, ?)";

        PreparedStatement insertManagerStatement = connection.prepareStatement(insertManagerSql);
        insertManagerStatement.setString(1, managerUsername);
        insertManagerStatement.setString(2, managerPassword);

        insertManagerStatement.executeUpdate();
    }
}


        private static void createHoursWorkedTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS medical_frequency (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "patient_id INT NOT NULL," +
                "week_start_date DATE NOT NULL," +
                "monday_hours DECIMAL(5, 2) NOT NULL," +
                "tuesday_hours DECIMAL(5, 2) NOT NULL," +
                "wednesday_hours DECIMAL(5, 2) NOT NULL," +
                "thursday_hours DECIMAL(5, 2) NOT NULL," +
                "friday_hours DECIMAL(5, 2) NOT NULL," +
                "saturday_hours DECIMAL(5, 2) NOT NULL," +
                "sunday_hours DECIMAL(5, 2) NOT NULL" +
                ")";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
}
