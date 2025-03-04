module com.HealthMgmtSys{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.HealthMgmtSys to javafx.fxml;
    opens com.HealthMgmtSys.Controller to javafx.fxml;
    opens com.HealthMgmtSys.View to javafx.fxml;
    exports com.HealthMgmtSys;
    exports com.HealthMgmtSys.Controller;
    exports com.HealthMgmtSys.Database;
    exports com.HealthMgmtSys.model;
   
    
}
