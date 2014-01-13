package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * User: ara
 * Date: 11.12.13
 * Time: 13:10
 */
public class DataController implements Initializable {
    public TableColumn<Data, User> userId;
    public TableColumn<Data, Calendar> time;
    public TableColumn<Data, String> action;
    public TableColumn<Data, String> url;
    public TableColumn<Data, String> material;
    public TableColumn category;
    public TableColumn lecture;

    public TableView dataTable;
    public Button reloadButton;

    public void readData(ActionEvent actionEvent) {
        reloadButton.setDisable(true);
        Task worker = createWorker();
        new Thread(worker).start();
    }

    @Override
    public void initialize(URL u, ResourceBundle resourceBundle) {
        userId.setCellValueFactory(new PropertyValueFactory<Data, User>("user"));
        userId.setCellFactory(getUserFormat());

        time.setCellValueFactory(new PropertyValueFactory<Data, Calendar>("time"));
        time.setCellFactory(getCalendarFormat());

        action.setCellValueFactory(new PropertyValueFactory<Data, String>("action"));

        url.setCellValueFactory(new PropertyValueFactory<Data, String>("url"));
        material.setCellValueFactory(new PropertyValueFactory<Data, String>("material"));

        lecture.setCellValueFactory(new PropertyValueFactory<Data, String>("lecture"));
        lecture.setCellFactory(TextFieldTableCell.forTableColumn());
        lecture.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data, String> event) {
                Data d = event.getRowValue();
                d.setLecture(event.getNewValue());
                DBController dbController = DBController.getInstance();
                dbController.start();
                dbController.updateLecture(d);
                dbController.commit();
                readData(null);
                dataTable.requestLayout();
            }
        });

        category.setCellValueFactory(new PropertyValueFactory<Data, String>("category"));
        category.setCellFactory(TextFieldTableCell.forTableColumn());
        category.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data, String> event) {
                Data d = event.getRowValue();
                d.setCategory(event.getNewValue());
                DBController dbController = DBController.getInstance();
                dbController.start();
                dbController.updateCategory(d);
                dbController.commit();
                readData(null);
            }
        });
        readData(null);
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                DBController dbController = DBController.getInstance();
                List<Data> allData = dbController.getAllData();
                final ObservableList<Data> data = FXCollections.observableArrayList();
                data.addAll(allData);
                dataTable.setItems(data);

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

    Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>> getCalendarFormat() {
        Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>> callback =
                new Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>>() {
                    @Override
                    public TableCell<Data, Calendar> call(TableColumn<Data, Calendar> param) {
                        return new TableCell<Data, Calendar>() {

                            @Override
                            protected void updateItem(Calendar item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    int m = item.get(Calendar.MINUTE);
                                    String min = (m / 10) == 0 ? "0" + m : String.valueOf(m);
                                    String calText = String.format("%d.%d.%d %d:%s",
                                            item.get(Calendar.DAY_OF_MONTH),
                                            item.get(Calendar.MONTH) + 1,
                                            item.get(Calendar.YEAR),
                                            item.get(Calendar.HOUR) + 12 * item.get(Calendar.AM_PM),
                                            min);
                                    setText(calText);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                };
        return callback;
    }

    public static Callback<TableColumn<Data, User>, TableCell<Data, User>> getUserFormat() {
        return
                new Callback<TableColumn<Data, User>, TableCell<Data, User>>() {
                    @Override
                    public TableCell<Data, User> call(TableColumn<Data, User> param) {
                        return new TableCell<Data, User>() {

                            @Override
                            protected void updateItem(User item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(String.valueOf(item.getId()));
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                };
    }
}
