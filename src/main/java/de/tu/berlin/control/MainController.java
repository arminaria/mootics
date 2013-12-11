package de.tu.berlin.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Armin on 27.11.13.
 */
public class MainController {
    public static String importFilePath;
    private static Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    public static Pane content;

    private Navigation navigation = new Navigation();

    public void showImportWindow(ActionEvent actionEvent) throws IOException {


    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void test(ActionEvent actionEvent) {
        log.debug("test");
    }
}
