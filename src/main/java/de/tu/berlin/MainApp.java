package de.tu.berlin;

import de.tu.berlin.control.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    private static Stage currentStage;

    public static void main(String[] args) throws Exception {
       launch(args);
    }


    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml"));
        String fxmlFile = "/fxml/main.fxml";
        Parent page = loader.load(this.getClass().getResource(fxmlFile), null, new JavaFXBuilderFactory());
        Navigation.setStage(stage);
        Scene scene = new Scene(page);
        scene.getStylesheets().add(this.getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Mootics");
        stage.show();
    }



    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static void setCurrentStage(Stage currentStage) {
        MainApp.currentStage = currentStage;
    }
}
