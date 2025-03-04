package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        HealthCareManagementSystem.setRoot("primary");
    }
}
