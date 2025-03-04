package com.HealthMgmtSys.model;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.io.Serializable;
import java.sql.Date;

public class Patient implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2661887463812196465L;
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String phone;
    private String gender;
    private Date dateOfBirth;
    private Date startDate;
    private Date endDate;
    private String bankName;
    private String bsb;
    private String accountName;
    private String accountNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", address1=" + address1 + ", address2=" + address2 + ", phone=" + phone
                + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", startDate=" + startDate + ", endDate="
                + endDate + ", bankName=" + bankName + ", bsb=" + bsb + ", accountName=" + accountName
                + ", accountNumber=" + accountNumber + "]";
    }

    public Patient(int id, String username, String firstName, String lastName, String address1,
            String address2, String phone, String gender, Date dateOfBirth, Date startDate, Date endDate,
            String bankName, String bsb, String accountName, String accountNumber) {
        super();
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bankName = bankName;
        this.bsb = bsb;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

}
