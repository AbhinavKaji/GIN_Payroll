/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utility;

import com.mycompany.gin_payroll.EmployeeListController;
import com.mycompany.model.Bank;
import com.mycompany.model.Employee;
import com.mycompany.model.Payroll;
import com.mycompany.model.Superannuation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aavin
 * @param <T>
 */
public class SQLHelper<T> {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "payroll_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static int executeUpdate(String query, Object... parameters) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setParameters(statement, parameters);
                return statement.executeUpdate();
            }
        }
    }

    public static <T> T getQuery(String query, ResultSetMapper<T> mapper, Object... parameters) throws SQLException {
        T result = null; // Initialize result to a default value
        try (Connection connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setParameters(statement, parameters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result = mapper.map(resultSet);
                    }
                }
            }
        }
        return result;
    }
    
    public static <T> List<T> executeQuery(String query, ResultSetMapper<T> mapper, Object... parameters) throws SQLException {
        List<T> resultList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setParameters(statement, parameters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        resultList.add(mapper.map(resultSet));
                    }
                }
            }
        }
        return resultList;
    }

    public static int executeInsert(String query, Object... parameters) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                setParameters(statement, parameters);
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1; // Indicating failure
    }

    private static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    public interface ResultSetMapper<T> {

        T map(ResultSet resultSet) throws SQLException;
    }

    public static void CreateDb() throws SQLException {

        // SQL statement to create a new database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL statement to create a new database
            String createDatabaseSQL = "CREATE DATABASE  IF NOT EXISTS " + DBNAME;

            try (PreparedStatement preparedStatement = connection.prepareStatement(createDatabaseSQL)) {
                preparedStatement.executeUpdate();

                System.out.println("Database created successfully.");
            }
        }

    }

    public static boolean SQLConnection() throws SQLException {
        CreateDb();
        boolean status = false;
        try (Connection connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD)) {

            System.out.println("Database Connected Successfully.");
            String createTableUser = "CREATE TABLE IF NOT EXISTS user ("
                    + "userId INT AUTO_INCREMENT PRIMARY KEY,"
                    + "userName VARCHAR(255) NOT NULL,"
                    + "firstName VARCHAR(255),"
                    + "lastName VARCHAR(255),"
                    + "password VARCHAR(255),"
                    + "role VARCHAR(20)"
                    + ")";
            String createTableEmployee = "CREATE TABLE IF NOT EXISTS employee ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "userId INT UNIQUE NOT NULL,"
                    + "employeeId VARCHAR(255) NOT NULL,"
                    + "address VARCHAR(255),"
                    + "phone VARCHAR(255),"
                    + "hourlyRate DOUBLE,"
                    + "email VARCHAR(255),"
                    +"FOREIGN KEY (userId) REFERENCES user(userId)"
                    + ")";
            String createTablePayroll = "CREATE TABLE IF NOT EXISTS payroll ("
                    + "payrollId INT AUTO_INCREMENT PRIMARY KEY,"
                    + "employeeId INT NOT NULL,"
                    + "hoursWorked DOUBLE NOT NULL,"
                    + "CurrentHourlyRate DOUBLE NOT NULL,"
                    + "totalSalary DOUBLE NOT NULL,"
                    + "payDate DATE NOT NULL,"
                    + "payWeekNum INT NOT NULL,"
                    + "year INT NOT NULL,"
                    + "tax DOUBLE NOT NULL,"
                    + "superAnnuation DOUBLE NOT NULL,"
                    + "netPay DOUBLE NOT NULL,"
                    + "ytdTotalSalary DOUBLE NOT NULL,"
                    + "ytdTax DOUBLE NOT NULL,"
                    + "ytdSuperAnnuation DOUBLE NOT NULL,"
                    + "ytdNetPay DOUBLE NOT NULL,"
                    +"FOREIGN KEY (employeeId) REFERENCES employee(id)"
                    + ")";
            String createTableBank = "CREATE TABLE IF NOT EXISTS bank ("
                    + "bankId INT AUTO_INCREMENT PRIMARY KEY,"
                    + "bankName VARCHAR(255) NOT NULL,"
                    + "bsbNumber INT NOT NULL,"
                    + "accNumber INT NOT NULL,"
                    + "payId INT NOT NULL,"
                    + "tfnNumber INT NOT NULL,"
                    + "employeeId INT UNIQUE NOT NULL,"
                    +"FOREIGN KEY (employeeId) REFERENCES employee(id)"
                    + ")";
            String createTableSuper = "CREATE TABLE IF NOT EXISTS superannuation ("
                    + "superId INT AUTO_INCREMENT PRIMARY KEY,"
                    + "superName VARCHAR(255) NOT NULL,"
                    + "memberNumber INT NOT NULL,"
                    + "usiNumber INT NOT NULL,"
                    + "employeeId INT UNIQUE NOT NULL,"
                    +"FOREIGN KEY (employeeId) REFERENCES employee(id)"
                    + ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableUser);
                statement.executeUpdate(createTableEmployee);
                statement.executeUpdate(createTablePayroll);
                statement.executeUpdate(createTableBank);
                statement.executeUpdate(createTableSuper);

                System.out.println("Table created successfully.");
                status = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error:" + e.getMessage());
        }
        seedData();
        return status;
    }

    private static void seedData() throws SQLException {
        String insertAdmin = "INSERT IGNORE INTO user VALUES (?,?,?,?,?,?)";
        var insertedId = executeInsert(insertAdmin, 1, "admin", "admin", "admin", CredentialManager.getHashedPassword("admin"), Role.ADMIN.getDbValue());
        System.out.println("id:" + insertedId);
        insertAdmin = "INSERT IGNORE INTO user VALUES (?,?,?,?,?,?)";
        insertedId = executeInsert(insertAdmin, 0, "testUser", "testUser", "testUser", CredentialManager.getHashedPassword("testPassword"), Role.ADMIN.getDbValue());
        System.out.println("id:" + insertedId);
    }

    public static Bank getBank(int employeeId) {
        try {
            String selectQuery = "Select * from bank where employeeId = ?";
            List<Bank> banks = SQLHelper.executeQuery(selectQuery, resultSet -> {
                Bank bank = new Bank();
                bank.setAccNumber(resultSet.getInt("accNumber"));
                bank.setBankName(resultSet.getString("bankName"));
                bank.setBsbNumber(resultSet.getInt("bsbNumber"));
                bank.setPayId(resultSet.getInt("payId"));
                bank.setEmployeeId(employeeId);
                bank.setTfnNumber(resultSet.getInt("tfnNumber"));
                bank.setId(resultSet.getInt("bankId"));
                return bank;
            }, employeeId);
            return banks.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<Payroll> getPayrollById(int empId) throws SQLException {

        String selectQuery = "SELECT * FROM payroll WHERE employeeId = ?";
        List<Payroll> payrolls = SQLHelper.executeQuery(selectQuery, resultSet -> {
            Payroll payroll = new Payroll();
            payroll.setPayrollId(resultSet.getInt("payrollId"));
            payroll.setEmployeeId(resultSet.getInt("employeeId"));
            payroll.setHoursWorked(resultSet.getDouble("hoursWorked"));
            payroll.setCurrentHourlyRate(resultSet.getDouble("CurrentHourlyRate"));
            payroll.setTotalSalary(resultSet.getInt("totalSalary"));
            payroll.setPayDate(resultSet.getDate("payDate").toLocalDate());
            payroll.setPayWeekNum(resultSet.getInt("payWeekNum"));
            payroll.setYear(resultSet.getInt("year"));
            payroll.setTax(resultSet.getDouble("tax"));
            payroll.setSuperAnnuation(resultSet.getDouble("superAnnuation"));
            payroll.setNetPay(resultSet.getDouble("netPay"));
            payroll.setYtdTotalSalary(resultSet.getDouble("ytdTotalSalary"));
            payroll.setYtdTax(resultSet.getDouble("ytdTax"));
            payroll.setYtdSuperAnnuation(resultSet.getDouble("ytdSuperAnnuation"));
            payroll.setYtdNetPay(resultSet.getDouble("ytdNetPay"));
            return payroll;
        }, empId);
        return payrolls;
    }

    public static List<Payroll> getPayroll(int weekNum, int year) throws SQLException {

        String selectQuery = "SELECT * FROM payroll WHERE payWeekNum = ? AND year = ?";
        List<Payroll> payrolls = SQLHelper.executeQuery(selectQuery, resultSet -> {
            Payroll payroll = new Payroll();
            payroll.setPayrollId(resultSet.getInt("payrollId"));
            payroll.setEmployeeId(resultSet.getInt("employeeId"));
            payroll.setHoursWorked(resultSet.getDouble("hoursWorked"));
            payroll.setCurrentHourlyRate(resultSet.getDouble("CurrentHourlyRate"));
            payroll.setTotalSalary(resultSet.getInt("totalSalary"));
            payroll.setPayDate(resultSet.getDate("payDate").toLocalDate());
            payroll.setPayWeekNum(resultSet.getInt("payWeekNum"));
            payroll.setYear(resultSet.getInt("year"));
            payroll.setTax(resultSet.getDouble("tax"));
            payroll.setSuperAnnuation(resultSet.getDouble("superAnnuation"));
            payroll.setNetPay(resultSet.getDouble("netPay"));
            payroll.setYtdTotalSalary(resultSet.getDouble("ytdTotalSalary"));
            payroll.setYtdTax(resultSet.getDouble("ytdTax"));
            payroll.setYtdSuperAnnuation(resultSet.getDouble("ytdSuperAnnuation"));
            payroll.setYtdNetPay(resultSet.getDouble("ytdNetPay"));
            return payroll;
        }, weekNum, year);
        return payrolls;

    }

    public static Payroll getEachPayroll(int empId, int weekNumber, int year) throws SQLException {

        String selectQuery = "SELECT * FROM payroll WHERE employeeId = ? AND payWeekNum = ? AND year = ?";
        Payroll payrolls = SQLHelper.getQuery(selectQuery, resultSet -> {
            Payroll payroll = new Payroll();
            payroll.setPayrollId(resultSet.getInt("payrollId"));
            payroll.setEmployeeId(resultSet.getInt("employeeId"));
            payroll.setHoursWorked(resultSet.getDouble("hoursWorked"));
            payroll.setCurrentHourlyRate(resultSet.getDouble("CurrentHourlyRate"));
            payroll.setTotalSalary(resultSet.getInt("totalSalary"));
            payroll.setPayDate(resultSet.getDate("payDate").toLocalDate());
            payroll.setPayWeekNum(resultSet.getInt("payWeekNum"));
            payroll.setYear(resultSet.getInt("year"));
            payroll.setTax(resultSet.getDouble("tax"));
            payroll.setSuperAnnuation(resultSet.getDouble("superAnnuation"));
            payroll.setNetPay(resultSet.getDouble("netPay"));
            payroll.setYtdTotalSalary(resultSet.getDouble("ytdTotalSalary"));
            payroll.setYtdTax(resultSet.getDouble("ytdTax"));
            payroll.setYtdSuperAnnuation(resultSet.getDouble("ytdSuperAnnuation"));
            payroll.setYtdNetPay(resultSet.getDouble("ytdNetPay"));
            return payroll;
        }, empId, weekNumber, year);
        return payrolls;

    }

    public static Employee getUser(int id) throws SQLException {

        String selectQuery = "Select * from user as u inner join employee as e on u.userId = e.userId where u.userId = ?";
        Employee emps = SQLHelper.getQuery(selectQuery, resultSet -> {
            Employee emp = new Employee();
            emp.setUserId(resultSet.getInt("userId"));
            emp.setUserName(resultSet.getString("userName"));
            emp.setFirstName(resultSet.getString("firstName"));
            emp.setLastName(resultSet.getString("lastName"));
            emp.setEmployeeId(resultSet.getString("employeeId"));
            emp.setId(resultSet.getInt("id"));
            emp.setAddress(resultSet.getString("address"));
            emp.setPhone(resultSet.getString("phone"));
            emp.setHourlyRate(resultSet.getDouble("hourlyRate"));
            emp.setEmail(resultSet.getString("email"));
            return emp;
        }, id);
        return emps;
    }

    public static Superannuation getSuper(int employeeId) throws SQLException {

        String selectQuery = "Select * from superannuation where employeeId = ?";
        List<Superannuation> supers = SQLHelper.executeQuery(selectQuery, resultSet -> {
            Superannuation supper = new Superannuation();
            supper.setId(resultSet.getInt("superId"));
            supper.setSuperName(resultSet.getString("superName"));
            supper.setUsiNumber(resultSet.getInt("usiNumber"));
            supper.setEmployeeId(employeeId);
            supper.setMemberNumber(resultSet.getInt("memberNumber"));
            return supper;
        }, employeeId);
        return supers.get(0);

    }
}
