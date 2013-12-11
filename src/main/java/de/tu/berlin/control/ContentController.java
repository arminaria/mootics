package de.tu.berlin.control;

import de.tu.berlin.model.Content;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * User: ara
 * Date: 11.12.13
 * Time: 14:32
 */
public class ContentController implements Initializable{
    public TableColumn<Content, Long> id;
    public TableView<Content> dataTable;
    public TableColumn<Content, String> name;

    private static Logger log = LoggerFactory.getLogger(ContentController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Content, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Content, String>("name"));
        dataTable.setEditable(true);
        name.setCellFactory(TextFieldTableCell.<Content>forTableColumn());
        name.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Content , String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Content, String> t) {
                        Content content = (Content) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        log.debug("Conent {}" , content);
                        content.setName(t.getNewValue());
                        log.debug("new Value {}" , t.getNewValue());
                        new DBController().updateNameOfContent(content,t.getNewValue());
                    }
                }
        );
    }

    public void readData(ActionEvent actionEvent) {
        List<Content> allData = new DBController().getAllContent();
        final ObservableList<Content> data = FXCollections.observableArrayList();
        data.addAll(allData);
        dataTable.setItems(data);
        dataTable.setEditable(true);
    }

}
