package com.HealthMgmtSys.model;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class MedicalInvoiceSlips implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 361491545926152480L;
    private int id;
    private int employeeId;
    private Date date;
    private BigDecimal netPay;

    public MedicalInvoiceSlips() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getNetPay() {
        return netPay;
    }

    public void setNetPay(BigDecimal netPay) {
        this.netPay = netPay;
    }

    public MedicalInvoiceSlips(int id, int employeeId, Date date, BigDecimal netPay) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.netPay = netPay;
    }

    @Override
    public String toString() {
        return "Payslips [id=" + id + ", employeeId=" + employeeId + ", date=" + date + ", netPay=" + netPay + "]";
    }

}
