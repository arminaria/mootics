package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: ara
 * Date: 11.12.13
 * Time: 13:10
 */
public class DataController implements Initializable {
    public TableColumn<Data, Long> id;
    public TableColumn<Data, Calendar> time;
    public TableColumn course;
    public TableColumn module;
    public TableColumn cmid;
    public TableColumn action;
    public TableColumn info;
    public TableColumn created;
    public TableView dataTable;
    public TableColumn urlColumn;
    DBController dbController = new DBController();


    public void readData(ActionEvent actionEvent) {
        List<Data> allData = dbController.getAllData();
        final ObservableList<Data> data = FXCollections.observableArrayList();
        data.addAll(allData);
        dataTable.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Data, Long>("id"));
        time.setCellValueFactory(new PropertyValueFactory<Data, Calendar>("time"));
        time.setCellFactory(getCalendarFormat());
        course.setCellValueFactory(new PropertyValueFactory<Data, Long>("course"));
        module.setCellValueFactory(new PropertyValueFactory<Data, String>("module"));
        cmid.setCellValueFactory(new PropertyValueFactory<Data, Content>("cmid"));
        cmid.setCellFactory(getContentFormat());
        action.setCellValueFactory(new PropertyValueFactory<Data, String>("action"));
        urlColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("url"));
        info.setCellValueFactory(new PropertyValueFactory<Data, String>("info"));
        created.setCellValueFactory(new PropertyValueFactory<Data, String>("created"));
        created.setCellFactory(getCalendarFormat());
    }

    Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>> getCalendarFormat(){
        Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>> callback =
                new Callback<TableColumn<Data, Calendar>, TableCell<Data, Calendar>>() {
            @Override
            public TableCell<Data, Calendar> call(TableColumn<Data, Calendar> param) {
                return new TableCell<Data, Calendar>() {

                    @Override
                    protected void updateItem(Calendar item, boolean empty) {
                        super.updateItem(item, empty);
                        DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);
                        DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.GERMAN);
                        if (!empty) {
                            setText(dateInstance.format(item.getTime()) + " " + timeInstance.format(item.getTime()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        };
        return callback;
    }

    private Callback<TableColumn<Data, Content>, TableCell<Data, Content>> getContentFormat(){
        Callback<TableColumn<Data, Content>, TableCell<Data, Content>> callback =
                new Callback<TableColumn<Data, Content>, TableCell<Data, Content>>() {
                    @Override
                    public TableCell<Data, Content> call(TableColumn<Data, Content> param) {
                        return new TableCell<Data, Content>() {

                            @Override
                            protected void updateItem(Content item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.getId()+" : "+item.getName());
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                };
        return callback;
    }
}
