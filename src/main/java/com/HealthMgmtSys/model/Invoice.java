package com.HealthMgmtSys.model;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.io.Serializable;
import java.sql.Date;

public class Invoice implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6271229137754541047L;
    private int id;
    private int employeeId;
    private Date weekEndingDate;
    private double hoursWorked;
    private double payAmount;

    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public Invoice(int id, int employeeId, Date weekEndingDate, double hoursWorked, double payAmount) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.weekEndingDate = weekEndingDate;
        this.hoursWorked = hoursWorked;
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "Payroll [id=" + id + ", employeeId=" + employeeId + ", hoursWorked=" + hoursWorked + ", payAmount="
                + payAmount + "]";
    }

}
