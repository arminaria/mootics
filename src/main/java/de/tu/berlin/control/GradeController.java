package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Grades;
import de.tu.berlin.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 08.01.14.
 */
public class GradeController implements Initializable {
    public TableView dataTable;
    public TableColumn userId;
    public TableColumn name;
    public TableColumn category;
    public TableColumn lecture;
    public TableColumn grade;
    public Button reloadButton;

    public void readData(ActionEvent actionEvent) {
        reloadButton.setDisable(true);
        Task worker = createWorker();
        new Thread(worker).start();
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                DBController dbController = DBController.getInstance();
                List<Grades> allData = dbController.getAllGrades();
                final ObservableList<Grades> grades = FXCollections.observableArrayList();
                grades.addAll(allData);
                dataTable.setItems(grades);

                Platform.runLater(
                        new Runnable() {
                            public void run() {
                                reloadButton.setDisable(false);
                            }
                        }
                );

                return true;
            }

        };

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userId.setCellValueFactory(new PropertyValueFactory<Grades, User>("user"));
        userId.setCellFactory(DataController.getUserFormat());
        name.setCellValueFactory(new PropertyValueFactory<Grades, String>("name"));
        category.setCellValueFactory(new PropertyValueFactory<Grades, String>("category"));
        lecture.setCellValueFactory(new PropertyValueFactory<Grades, String>("lecture"));
        grade.setCellValueFactory(new PropertyValueFactory<Grades, Long>("grade"));

        lecture.setCellFactory(TextFieldTableCell.forTableColumn());
        lecture.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Grades, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Grades, String> event) {
                Grades g = event.getRowValue();
                g.setLecture(event.getNewValue());
                DBController dbController = DBController.getInstance();
                dbController.start();
                dbController.updateGradeLecture(g);
                dbController.commit();
                readData(null);
                dataTable.requestLayout();
            }
        });

        category.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Grades, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Grades, String> event) {
                Grades g = event.getRowValue();
                g.setCategory(event.getNewValue());
                DBController dbController = DBController.getInstance();
                dbController.start();
                dbController.updateGradeCategory(g);
                dbController.commit();
                readData(null);
            }
        });

        readData(null);

    }
}
