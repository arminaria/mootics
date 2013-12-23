package de.tu.berlin.control;

import de.tu.berlin.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * User: ara
 * Date: 11.12.13
 * Time: 10:23
 */
public class Navigation {
    private static final Logger log = LoggerFactory.getLogger(Navigation.class);
    private static Stage stage;



    public void replaceSceneContent(String fxml)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml"));
            Parent page = loader.load(MainApp.class.getResource(fxml), null, new JavaFXBuilderFactory());
            MainController.content.getChildren().clear();
            MainController.content.getChildren().add(page);
        } catch (IOException e) {
            log.error("Could not load {}", fxml, e);
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Navigation.stage = stage;
    }

    public void close(ActionEvent actionEvent) {
        log.debug("Closed....");
        System.exit(0);
    }

    public void gotoShowData(ActionEvent actionEvent) {
        gotoMain();
    }

    public void gotoContent(ActionEvent actionEvent) {
        String fxmlFile = "/fxml/content.fxml";
        replaceSceneContent(fxmlFile);
    }
    public void gotoMain(){
        String fxmlFile = "/fxml/data.fxml";
        replaceSceneContent(fxmlFile);
    }

    public void gotoImportData(){
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            ImportController.setFile(file);
            String fxmlFile = "/fxml/importWindow.fxml";
            replaceSceneContent(fxmlFile);
        }
    }

    public void gotoShowDayOfUsage(ActionEvent actionEvent) {
        String fxmlFile = "/fxml/dayOfUsage.fxml";
        replaceSceneContent(fxmlFile);
    }
}
