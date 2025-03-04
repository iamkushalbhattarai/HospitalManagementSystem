package com.HealthMgmtSys.Controller;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import com.HealthMgmtSys.HealthCareManagementSystem;
import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        HealthCareManagementSystem.setRoot("secondary");
    }

}
