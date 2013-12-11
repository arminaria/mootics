package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Created by Armin on 27.11.13.
 */
public class ImportController implements Initializable {

    @FXML
    public static Text filePath;
    @FXML
    public TextField separator;
    @FXML
    public ProgressBar progressBar;

    private static File file;

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        ImportController.file = file;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        filePath.setText(file.getAbsolutePath());
    }

    public void importData(ActionEvent actionEvent) throws Exception {
        Parser p = new ParserImpl();
        p.setSeparator(separator.getText().charAt(0));
        List<Data> dataList;
        try {
            progressBar.setVisible(true);
            progressBar.progressProperty().unbind();
            Stage scene = (Stage) progressBar.getScene().getWindow();
            dataList = p.parseAll(new File(file.getAbsolutePath()));
            Task worker = createWorker(dataList, scene);
            progressBar.progressProperty().bind(worker.progressProperty());
            new Thread(worker).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Task createWorker(final List<Data> dataList, final Stage stage) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int count = dataList.size();
                DBController dbController = new DBController();
                for (int i = 0; i < count; i++) {
                    dbController.insert(dataList.get(i));
                    updateProgress(i + 1, count);
                }

                Platform.runLater(
                        new Runnable() {
                            public void run() {
                                stage.close();
                            }
                        }
                );

                return true;
            }
        };
    }


    public void cancel(ActionEvent actionEvent) {
        new Navigation().gotoMain();
    }
}
