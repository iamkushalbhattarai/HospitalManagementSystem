package com.HealthMgmtSys.model;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
public class InvoiceRecord {

    private String employeeName;
    private double totalNetPay;
    private double tax;
    private double supper;

    public InvoiceRecord(String employeeName, double totalNetPay, double tax, double supper) {
        this.employeeName = employeeName;
        this.totalNetPay = totalNetPay;
        this.tax = tax;
        this.supper = supper;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getTotalNetPay() {
        return totalNetPay;
    }

    public void setTotalNetPay(double totalNetPay) {
        this.totalNetPay = totalNetPay;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSupper() {
        return supper;
    }

    public void setSupper(double supper) {
        this.supper = supper;
    }

}
