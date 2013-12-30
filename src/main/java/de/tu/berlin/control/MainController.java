package de.tu.berlin.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Armin on 27.11.13.
 */
public class MainController {
    public static String importFilePath;
    private static Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    public static Pane content;

    private Navigation navigation = new Navigation();

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void test(ActionEvent actionEvent) {
        log.debug("test");
    }
}
