package com.HealthMgmtSys;
/**
 *
 * @author kushalbhattarai
 * student ID:12198946
 */
import javafx.application.Application;
import com.HealthMgmtSys.Database.Databases;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX HealthCareManagementSystem
 */
public class HealthCareManagementSystem extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Databases.createDatabaseAndTables();
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
      
           FXMLLoader fxmlLoader = new FXMLLoader(HealthCareManagementSystem.class.getResource("/com/HealthMgmtSys/View/" + fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
